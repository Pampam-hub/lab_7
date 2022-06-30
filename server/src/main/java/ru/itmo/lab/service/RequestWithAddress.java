package ru.itmo.lab.service;

import ru.itmo.lab.repository.request.Request;

import java.net.SocketAddress;

public class RequestWithAddress {
    private final Request request;
    private final SocketAddress socketAddress;

    public RequestWithAddress(Request request, SocketAddress socketAddress) {
        this.request = request;
        this.socketAddress = socketAddress;
    }

    public Request getRequest() {
        return request;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
