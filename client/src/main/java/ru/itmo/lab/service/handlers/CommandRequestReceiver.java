package ru.itmo.lab.service.handlers;

public class CommandRequestReceiver {
    public static void receiveCommandRequest(SocketWorker socketWorker) {
        ResultReceiver.receiveResult(socketWorker);
    }
}
