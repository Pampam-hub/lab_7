package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

public class HistoryCommand extends ClientCommand {
    public HistoryCommand() {
        super("history", "you can see the last 12 commands " +
                        "you called", "arguments aren't needed");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage) {
        try {
            return new CommandResultBuilder()
                    .setMessage("Your history:")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .setDeque(storage.getHistory()).build();
        } catch (IllegalArgumentException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }
    }
}
