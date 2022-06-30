package ru.itmo.lab;

import ru.itmo.lab.repository.DragonConcurrentSkipListMapStorage;

import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.service.DB.DBConnectable;
import ru.itmo.lab.service.DB.DBStorage;
import ru.itmo.lab.service.DB.SSHDBConnector;
import ru.itmo.lab.service.commands.servercommands.ExitServerCommand;
import ru.itmo.lab.service.commands.servercommands.HelpServerCommand;
import ru.itmo.lab.service.commands.clientcommands.*;
import ru.itmo.lab.service.handlers.*;

import java.sql.SQLException;

public class Server {
    private final static DBConnectable dbConnector = new SSHDBConnector();

    public static void main(String[] args) {

        Storage storage = new DragonConcurrentSkipListMapStorage();
        DBStorage dbStorage = new DBStorage(dbConnector);

        UsersManager usersManager = new UsersManager(dbStorage);

        SocketWorker socketWorker = startSocketWorker();

        CommandExecutor commandExecutor = new CommandExecutor(new HelpCommand(),
                new InfoCommand(), new ShowCommand(), new InsertCommand(),
                new UpdateCommand(), new RemoveKeyCommand(), new ClearCommand(),
                new ExitCommand(), new HistoryCommand(), new RemoveLowerKeyCommand(),
                new MinByAgeCommand(), new FilterGreaterThanTypeCommand(),
                new PrintFieldDescendingAgeCommand(),
                new HelpServerCommand(), new ExitServerCommand());

        try {
            storage.loadCollection(dbStorage.loadCollection());
        } catch (SQLException e) {
            System.out.println("Ошибка при инициализации коллекции");
            e.printStackTrace();
            System.exit(1);
        }

        RequestThread requestThread =
                new RequestThread(socketWorker, commandExecutor, storage, dbStorage, usersManager);

        ConsoleReader consoleReader = new ConsoleReader(commandExecutor);
        ConsoleThread consoleThread = new ConsoleThread(consoleReader, storage);

        requestThread.start();
        consoleThread.start();

    }
    private static SocketWorker startSocketWorker() {
        return new SocketWorker();
    }
}
