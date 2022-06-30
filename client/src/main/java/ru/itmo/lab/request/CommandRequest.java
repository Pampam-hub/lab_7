package ru.itmo.lab.request;


import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.CommandToSend;

import java.util.List;

public interface CommandRequest {
    Request createRequest(CommandToSend commandToSend, List<String> user);
}
