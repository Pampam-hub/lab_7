package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.comparators.AgeDragonComparator;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

public class MinByAgeCommand extends ClientCommand {
    public MinByAgeCommand() {
        super("min_by_age","you can see element with the smallest age",
                "arguments aren't needed");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage) {
        try {
            Dragon minDragon = (Dragon) storage.min(new AgeDragonComparator());

            return new CommandResultBuilder()
                    .setMessage("Here element with the smallest age")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .setDragon(minDragon).build();
        } catch (IllegalArgumentException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }
    }
}
