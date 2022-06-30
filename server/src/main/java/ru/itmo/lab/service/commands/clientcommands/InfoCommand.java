package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;


public class InfoCommand extends ClientCommand {
    public InfoCommand() {
        super("info","you can see info about the collection",
                "arguments aren't needed");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage) {
        try {
            return new CommandResultBuilder()
                    .setMessage("Here is your collection: ")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .setData(storage.getInfo()).build();
        } catch (IllegalArgumentException e) {
        return new CommandResultBuilder()
                .setMessage(e.getMessage())
                .setStatus(CommandStatus.UNSUCCESSFUL).build();
    }
    }
}
