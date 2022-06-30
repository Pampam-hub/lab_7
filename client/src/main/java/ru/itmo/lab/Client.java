package ru.itmo.lab;

import ru.itmo.lab.repository.ConsoleWorker;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.handlers.SocketWorker;
import ru.itmo.lab.service.handlers.ConsoleReader;
import ru.itmo.lab.service.user.GeneratorUser;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Scanner scanner = new Scanner(System.in)) {
            ConsoleWorker.getConsoleWorker().setScanner(scanner);
            SocketWorker socketWorker = startClientWorker();

            GeneratorUser generatorUser = new GeneratorUser(socketWorker);
            List<String> user = generatorUser.getUser();

            ConsoleReader consoleReader = new ConsoleReader(socketWorker, user);
            consoleReader.readFromConsole();
        } catch (IOException e) {
            OutputMessage.printErrorMessage("failed to open datagram channel");
        }
    }

    private static SocketWorker startClientWorker() throws IOException {
        return new SocketWorker();
    }
}
