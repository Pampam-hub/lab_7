package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.request.Request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public final class Serializer {
    private Serializer() {
    }

    public static ByteBuffer serializeRequest(Request request) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();
            return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        }
    }

    public static byte[] serializeResult (CommandResult result) throws IOException {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
