package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;
import ru.itmo.lab.service.handlers.CommandExecutor;

import java.util.List;
import java.util.stream.Collectors;


public class HelpCommand extends ClientCommand {

    public HelpCommand() {
        super("help", "help on available commands",
                "arguments aren't needed");
    }

    // DIY lib
    @Override
    public CommandResult execute(Storage storage, Request request, DBStorage dbStorage) {
        try {
            List<String> listOfCommands = CommandExecutor.CLIENT_AVAILABLE_COMMANDS.entrySet().stream()
                    .map(entry -> entry.getValue().toString()).collect(Collectors.toList());
            return new CommandResultBuilder()
                    .setMessage("Available commands: ")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .setListOfCommands(listOfCommands).build();
        } catch (IllegalArgumentException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }

    }
}
