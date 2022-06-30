package ru.itmo.lab.request;

import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.repository.request.RequestBuilder;
import ru.itmo.lab.repository.request.RequestType;
import ru.itmo.lab.service.CommandToSend;
import ru.itmo.lab.service.handlers.DragonValidator;

import java.util.List;

public class WithoutArgsCommandRequest implements CommandRequest {
    public WithoutArgsCommandRequest() {
    }

    @Override
    public Request createRequest(CommandToSend commandToSend, List<String> user) {
        DragonValidator.validateNumberOfArgs(commandToSend.getCommandArgs(), 0);

        return new RequestBuilder()
                .withName(commandToSend.getCommandName())
                .withRequestTypeArgument(RequestType.COMMAND)
                .withLocalTimeArgument()
                .withUsernameArgument(user.get(0))
                .withPasswordArgument(user.get(1))
                .build();
    }
}