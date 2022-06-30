package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.ConsoleWorker;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.CommandToSend;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class ScriptWorker {
    private static HashSet<String> previousFiles = new HashSet<>();

    private ScriptWorker() {

    }

    public static void startWorkWithScript(CommandToSend commandToSend,
                                           SocketWorker socketWorker, List<String> user) {
        try {
            DragonValidator.validateNumberOfArgs(commandToSend.getCommandArgs(), 1);
            String file = commandToSend.getCommandArgs()[0];
            FileChecker.checkFile(file);
            if (previousFiles.contains(file)) {
                throw new IllegalArgumentException("Possible looping");
            }
            previousFiles.add(file);

            ConsoleWorker.getConsoleWorker().setExecutedScript(true);
            ScriptReader scriptReader = new ScriptReader(socketWorker, user);
            scriptReader.readFromScript(file);
            previousFiles.remove(file);
            ConsoleWorker.getConsoleWorker().setExecutedScript(false);

            OutputMessage.printSuccessfulMessage("Script \"" +  file + "\" has been executed");
        } catch (IOException e) {
            OutputMessage.printErrorMessage(e.getMessage());
        }
    }
}
