package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.ConsoleWorker;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ScriptReader {
    private SocketWorker socketWorker;
    private List<String> user;
    public ScriptReader(SocketWorker socketWorker, List<String> user) {
        this.socketWorker = socketWorker;
        this.user = user;
    }

    public void readFromScript(String file) throws IOException {
        Scanner scanner = new Scanner(new BufferedInputStream(new FileInputStream(file)));

        ConsoleWorker.getConsoleWorker().setScanner(scanner);
        while(scanner.hasNext()) {
            String line = scanner.nextLine();
            CommandRequestWorker.superpupermethod(line, socketWorker, user);
        }

        ConsoleWorker.getConsoleWorker().setExecutedScript(false);
        scanner.close();
    }
}
