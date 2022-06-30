package ru.itmo.lab.request;

import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.repository.request.RequestBuilder;
import ru.itmo.lab.repository.request.RequestType;
import ru.itmo.lab.service.CommandToSend;
import ru.itmo.lab.service.handlers.DragonValidator;

import java.util.List;

public class WithIdCommandRequest implements CommandRequest {
    public WithIdCommandRequest() {
    }

    @Override
    public Request createRequest(CommandToSend commandToSend, List<String> user) {
        DragonValidator.validateNumberOfArgs(commandToSend.getCommandArgs(), 1);

        DragonValidator<Integer> dragonValidator = new DragonValidator<>(commandToSend.getCommandArgs()[0],null);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(Integer::parseInt, "value of id must be an integer");
        Integer id = dragonValidator.getValue();

        return new RequestBuilder()
                .withName(commandToSend.getCommandName())
                .withIntegerArgument(id)
                .withRequestTypeArgument(RequestType.COMMAND)
                .withLocalTimeArgument()
                .withUsernameArgument(user.get(0))
                .withPasswordArgument(user.get(1))
                .build();
    }
}