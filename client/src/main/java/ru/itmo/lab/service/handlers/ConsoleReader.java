package ru.itmo.lab.service.handlers;
import ru.itmo.lab.repository.ConsoleWorker;
import ru.itmo.lab.service.OutputMessage;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleReader {
    SocketWorker socketWorker;
    List<String> user;

    public ConsoleReader(SocketWorker socketWorker, List<String> user) {
        this.socketWorker = socketWorker;
        this.user = user;
    }

    public void readFromConsole() throws IOException {
        while(true) {
            Scanner scanner = ConsoleWorker.getConsoleWorker().getScanner();;

            while(true) {
                try {
                    System.out.println("\nEnter command, please");
                    String line = scanner.nextLine();
                    CommandRequestWorker.superpupermethod(line, socketWorker, user);
                } catch(NoSuchElementException e) {
                    OutputMessage.printErrorMessage("Invalid character entered");
                    System.exit(0);
                }
            }
        }
    }
}
