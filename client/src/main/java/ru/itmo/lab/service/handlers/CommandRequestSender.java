package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.request.Request;

import java.io.IOException;

public class CommandRequestSender {
    public static void sendCommandRequest(Request request, SocketWorker socketWorker)
            throws IOException {
        request.setClientInfo(socketWorker.getAddress() + socketWorker.getPort());
        socketWorker.sendRequest(request);
    }
}
