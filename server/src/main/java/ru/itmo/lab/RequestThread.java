package ru.itmo.lab;


import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.repository.request.RequestType;
import ru.itmo.lab.service.DB.DBStorage;
import ru.itmo.lab.service.DB.SSHDBConnector;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.RequestWithAddress;
import ru.itmo.lab.service.handlers.CommandExecutor;
import ru.itmo.lab.service.handlers.SocketWorker;
import ru.itmo.lab.service.handlers.UsersManager;

import java.io.IOException;
import java.util.concurrent.*;

public class RequestThread extends Thread {
    private final SocketWorker socketWorker;
    private final Storage storage;
    private final CommandExecutor commandExecutor;
    private final DBStorage dbStorage;
    private final UsersManager usersManager;

    private final ExecutorService fixedService = Executors.newFixedThreadPool(5);
    private final ExecutorService cachedService = Executors.newCachedThreadPool();

    public RequestThread(SocketWorker socketWorker, CommandExecutor commandExecutor,
                         Storage storage, DBStorage dbStorage, UsersManager usersManager) {
        this.socketWorker = socketWorker;
        this.storage = storage;
        this.commandExecutor = commandExecutor;
        this.dbStorage = dbStorage;
        this.usersManager = usersManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Future<RequestWithAddress> listenFuture = fixedService.submit(socketWorker::receiveRequest);
                RequestWithAddress acceptedRequest = listenFuture.get();
                if (acceptedRequest != null) {
                    CompletableFuture
                            .supplyAsync(acceptedRequest::getRequest)
                            .thenApplyAsync(request -> {
                                System.out.println(request.toString());
                                if (request.getRequestTypeArgument().equals(RequestType.COMMAND)) {
                                    return commandExecutor.executeClientCommand(storage, request, dbStorage);
                                } else if (request.getRequestTypeArgument().equals(RequestType.SIGN_UP)) {
                                    return usersManager.registerNewUser(request);
                                } else {
                                    return usersManager.loginUser(request);
                                }
                            }, cachedService)
                            .thenAcceptAsync(response -> {
                                try {
                                    socketWorker.sendResult(response, acceptedRequest.getSocketAddress());
                                } catch (IOException e) {
                                    OutputMessage.printErrorMessage(e.getMessage());
                                }
                            }, fixedService);
                }
            } catch (ExecutionException e) {
                OutputMessage.printErrorMessage(e.getMessage());
            } catch (InterruptedException e) {
                OutputMessage.printErrorMessage("An error occurred while deserializing the request, try again");
            }
        }
    }
}