package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.request.Request;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SocketWorker {
    private static final int DEFAULT_PORT = 1425;
    private final DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private InetAddress host;
    private int port = DEFAULT_PORT;

    private String address = "localhost";

    public SocketWorker() throws IOException {
        host = InetAddress.getByName(address);
        socketAddress = new InetSocketAddress(host, port);
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);
        datagramChannel.configureBlocking(false);
    }

    public void sendRequest(Request request) throws IOException {
        try {
            ByteBuffer bufferToSend = Serializer.serializeRequest(request);
            datagramChannel.send(bufferToSend, socketAddress);
        } catch (IOException e) {
            throw new IOException("error sending the request");
        }
    }

    public CommandResult receiveResult() throws IOException, ClassNotFoundException {
        int receivedSize = datagramChannel.socket().getReceiveBufferSize();
        ByteBuffer bufferToReceive = ByteBuffer.allocate(receivedSize);
        SocketAddress socketAddress = datagramChannel.receive(bufferToReceive);
        if (socketAddress == null) {
            return null;
        }
        return Deserializer.deSerializeResult(bufferToReceive);
    }

    public int getPort() {
        return port;
    }

    public String getAddress() {
        return address;
    }
}
