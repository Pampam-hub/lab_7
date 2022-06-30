package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

import java.sql.SQLException;
import java.util.List;


public class ClearCommand extends ClientCommand {
    public ClearCommand() {
        super("clear","clear all collection",
                "arguments aren't needed");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage) {
        try {
            dbStorage.validateUser(request.getUsernameArgument(), request.getPasswordArgument());
            System.out.println("0");
            List<Integer> listId = dbStorage.removeAll(request.getUsernameArgument());
            System.out.println("1");
            storage.removeAll(listId);
            return new CommandResultBuilder()
                    .setMessage("Collection has been cleared")
                    .setStatus(CommandStatus.SUCCESSFUL).build();
        } catch (IllegalArgumentException | SQLException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }
    }
}
