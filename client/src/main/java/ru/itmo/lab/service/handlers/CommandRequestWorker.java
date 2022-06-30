package ru.itmo.lab.service.handlers;

import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.CommandToSend;

import java.io.IOException;
import java.util.List;

public class CommandRequestWorker {
    private final static CommandRequestCreator requestCreator = new CommandRequestCreator();

    public static void superpupermethod(String line, SocketWorker socketWorker,
                                        List<String> user) throws IOException {
        CommandToSend commandToSend = CommandToSendCreator.createCommandToSend(line);
        try {
            if ("execute_script".equals(commandToSend.getCommandName())) {
                ScriptWorker.startWorkWithScript(commandToSend, socketWorker, user);
            } else {
                Request request = requestCreator.createCommandRequest(line, commandToSend, user);

                if (request != null) {
                    CommandRequestSender.sendCommandRequest(request, socketWorker);

                    CommandRequestReceiver.receiveCommandRequest(socketWorker);
                }
            }
        } catch (IllegalArgumentException e) {
            OutputMessage.printErrorMessage(e.getMessage());
        }
    }
}
