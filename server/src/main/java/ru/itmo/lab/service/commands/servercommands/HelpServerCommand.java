package ru.itmo.lab.service.commands.servercommands;

import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.service.handlers.CommandExecutor;
import ru.itmo.lab.service.handlers.DragonValidator;

import java.util.List;
import java.util.stream.Collectors;

public class HelpServerCommand extends ServerCommand{
    public HelpServerCommand() {
        super("help", "help on available commands",
                "arguments aren't needed");
    }

    @Override
    public CommandResult execute(Storage storage, String[] args) {
        try {
            DragonValidator.validateNumberOfArgs(args, 0);
            List<String> listOfCommands = CommandExecutor.SERVER_AVAILABLE_COMMANDS.entrySet().stream()
                    .map(entry -> entry.getValue().toString()).collect(Collectors.toList());
            CommandResult commandResult = new CommandResultBuilder()
                    .setMessage("Available commands: ")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .setListOfCommands(listOfCommands).build();

            return commandResult;
        } catch (IllegalArgumentException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL).build();
        }

    }
}
