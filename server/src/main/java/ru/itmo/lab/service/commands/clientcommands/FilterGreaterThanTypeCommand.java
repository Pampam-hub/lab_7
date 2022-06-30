package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.entity.DragonType;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.comparators.TypeDragonComparator;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

import java.util.List;

public class FilterGreaterThanTypeCommand extends ClientCommand {
    public FilterGreaterThanTypeCommand() {
        super("filter_greater_than_type","you can see elements that " +
                "have greater type than specified", "type");
    }

    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage) {
        try {
            DragonType type = request.getDragonArgument().getType();

            List<Dragon> listDragons = storage.sortElements(new TypeDragonComparator());

            while(type.compareTo(listDragons.get(0).getType()) > 0 ) {
                listDragons.remove(0);
            }

            CommandResult commandResult = new CommandResultBuilder()
                    .setMessage("Here elements with greater type: ")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .setListOfDragons(listDragons).build();

            return commandResult;
        } catch (IllegalArgumentException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }
    }
}
