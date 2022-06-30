package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.service.OutputMessage;

import java.io.IOException;

public final class ResultReceiver {
    private ResultReceiver() {
    }

    public static void receiveResult (SocketWorker socketWorker) {
        try {
            CommandResult commandResult = null;
            for (int i = 0; i < 50 && commandResult == null; i++) {
                OutputMessage.printSuccessfulMessage("Waiting for response from server...");
                Thread.sleep(200);
                commandResult = socketWorker.receiveResult();
            }
            if (commandResult == null) {
                OutputMessage.printErrorMessage("I'm tired!");
                return;
            }
            commandResult.showCommandResult();
        } catch (IOException e) {
            OutputMessage.printErrorMessage("Occurred while receiving response from server ");
        } catch (ClassNotFoundException e) {
            OutputMessage.printErrorMessage("Incorrect response from server");
        } catch (InterruptedException e) {
            OutputMessage.printErrorMessage("Interruption request during tread sleeping");
        }
    }
}
