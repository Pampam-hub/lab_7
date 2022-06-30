package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

import java.sql.SQLException;

public class UpdateCommand extends ClientCommand {
    public UpdateCommand() {
        super("update","update element from collection",
                "id");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage)  {

        try {
            dbStorage.validateUser(request.getUsernameArgument(), request.getPasswordArgument());
            dbStorage.update(request.getIntegerArgument(), request.getDragonArgument(), request.getUsernameArgument());
            storage.update(request.getIntegerArgument(), request.getDragonArgument());
            return new CommandResultBuilder()
                    .setMessage("Collection has been update")
                    .setStatus(CommandStatus.SUCCESSFUL).build();
        } catch (IllegalArgumentException | EntityNotFoundException | SQLException | EntityAlreadyExistsException e) {
            e.printStackTrace();
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }

    }
}
