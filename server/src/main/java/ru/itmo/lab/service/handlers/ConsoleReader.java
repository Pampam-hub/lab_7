package ru.itmo.lab.service.handlers;


import ru.itmo.lab.repository.ConsoleWorker;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.commands.clientcommands.*;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleReader {
    public static CommandExecutor commandExecutor;

    public ConsoleReader(CommandExecutor commandExecutor) {
        ConsoleReader.commandExecutor = commandExecutor;
    }
    public void readFromConsole(Storage storage) {
        Scanner scanner = new Scanner(System.in);
        ConsoleWorker.getConsoleWorker().setScanner(scanner);
        while(true) {
            try {
                System.out.println("\nEnter command, please");
                String line = scanner.nextLine();
                commandExecutor.executeServerCommand(storage, line);
            } catch(NoSuchElementException e) {
                OutputMessage.printErrorMessage("\nInvalid character entered");
                System.exit(0);
            }
        }
    }

    public static CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }
}
