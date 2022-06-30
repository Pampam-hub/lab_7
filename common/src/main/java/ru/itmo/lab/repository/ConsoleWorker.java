package ru.itmo.lab.repository;

import java.util.Scanner;

public class ConsoleWorker {
    private boolean executedScript = false;
    private Scanner scanner;
    private static ConsoleWorker consoleWorker;

    private ConsoleWorker(){

    }

    public boolean isExecutedScript() {
        return executedScript;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setExecutedScript(boolean executedScript) {
        this.executedScript = executedScript;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public static ConsoleWorker getConsoleWorker() {
        if(consoleWorker == null ) consoleWorker = new ConsoleWorker();
        return consoleWorker;
    }
}
