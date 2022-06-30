package ru.itmo.lab.service.user;

import ru.itmo.lab.repository.ConsoleWorker;
import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.request.RequestBuilder;
import ru.itmo.lab.repository.request.RequestType;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.handlers.SocketWorker;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class GeneratorUser {
    private List<String> user;

    public GeneratorUser(SocketWorker socketWorker) {
        boolean isRunning = true;
        Scanner scanner = ConsoleWorker.getConsoleWorker().getScanner();
        while (isRunning) {
            String question = "Do you have an account? Enter yes/no";
            boolean answer = UserInitializer.askForRegistration(question, scanner);
            List<String> user = UserInitializer.getUser(scanner);
            if (answer) {
                if (loginUser(user.get(0), user.get(1), socketWorker)) {
                    isRunning = false;
                    this.user = user;
                }
            } else {
                if (registerUser(user.get(0), user.get(1), socketWorker)) {
                    isRunning = false;
                    this.user = user;
                }
            }
        }
    }

    private boolean registerUser(String login, String password, SocketWorker socketWorker) {
        try {
            socketWorker.sendRequest(new RequestBuilder()
                    .withUsernameArgument(login)
                    .withPasswordArgument(password)
                    .withRequestTypeArgument(RequestType.SIGN_UP)
                    .build());
            CommandResult commandResult = null;
            for (int i = 0; i < 50 && commandResult == null; i++) {
                try {
                    OutputMessage.printSuccessfulMessage("Waiting for response from server...");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                commandResult =socketWorker.receiveResult();
            }
            if (commandResult == null) {
                OutputMessage.printErrorMessage("I'm tired!");
                return false;
            }

            commandResult.showCommandResult();
            return commandResult.isSuccessful();
        } catch (IOException | ClassNotFoundException e) {
            OutputMessage.printErrorMessage("Occurred while receiving response from server ");
        }
        return false;
    }

    private boolean loginUser(String login, String password, SocketWorker socketWorker) {
        try {
            socketWorker.sendRequest(new RequestBuilder()
                    .withUsernameArgument(login)
                    .withPasswordArgument(password)
                    .withRequestTypeArgument(RequestType.SIGN_IN)
                    .build());
            CommandResult commandResult = null;
            for (int i = 0; i < 50 && commandResult == null; i++) {
                try {
                    OutputMessage.printSuccessfulMessage("Waiting for response from server...");
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                commandResult = socketWorker.receiveResult();
            }
            if (commandResult == null) {
                OutputMessage.printErrorMessage("I'm tired!");
                return false;
            }
            commandResult.showCommandResult();
            return commandResult.isSuccessful();
        } catch (IOException | ClassNotFoundException e) {
            OutputMessage.printErrorMessage("Occurred while receiving response from server ");
        }
        return false;
    }

    public List<String> getUser() {
        return user;
    }
}
