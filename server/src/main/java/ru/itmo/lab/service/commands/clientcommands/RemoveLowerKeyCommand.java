package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

import java.sql.SQLException;

public class RemoveLowerKeyCommand extends ClientCommand {
    public RemoveLowerKeyCommand() {
        super("remove_lower_key", "remove elements that have key " +
                "lower than specified from collection", "id");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage)  {
        try {
            dbStorage.validateUser(request.getUsernameArgument(), request.getPasswordArgument());
            dbStorage.removeLowerKey(request.getIntegerArgument(), request.getUsernameArgument());
            storage.removeLowerKey(request.getIntegerArgument());
            return new CommandResultBuilder()
                    .setMessage("The removal has been completed")
                    .setStatus(CommandStatus.SUCCESSFUL).build();
        } catch (IllegalArgumentException | SQLException | EntityNotFoundException | EntityAlreadyExistsException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }

    }
}
