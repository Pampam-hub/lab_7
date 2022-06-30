package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.commands.clientcommands.ClientCommand;
import ru.itmo.lab.service.commands.servercommands.ServerCommand;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandExecutor {
    public static Map<String, ClientCommand> CLIENT_AVAILABLE_COMMANDS = new LinkedHashMap<>();
    public static Map<String, ServerCommand> SERVER_AVAILABLE_COMMANDS = new LinkedHashMap<String, ServerCommand>();

    public CommandExecutor(ClientCommand helpCommand, ClientCommand infoCommand,
                           ClientCommand showCommand, ClientCommand insertCommand,
                           ClientCommand updateCommand, ClientCommand removeKeyCommand,
                           ClientCommand clearCommand, ClientCommand exitCommand,
                           ClientCommand historyCommand, ClientCommand removeLowerKeyCommand,
                           ClientCommand minByAgeCommand,
                           ClientCommand filterGreaterThanTypeCommand,
                           ClientCommand printFieldDescendingAgeCommand,
                           ServerCommand helpCommandServer,
                           ServerCommand exitCommandServer) {

        CLIENT_AVAILABLE_COMMANDS.put(helpCommand.getName(), helpCommand);
        CLIENT_AVAILABLE_COMMANDS.put(infoCommand.getName(), infoCommand);
        CLIENT_AVAILABLE_COMMANDS.put(showCommand.getName(), showCommand);
        CLIENT_AVAILABLE_COMMANDS.put(insertCommand.getName(), insertCommand);
        CLIENT_AVAILABLE_COMMANDS.put(updateCommand.getName(), updateCommand);
        CLIENT_AVAILABLE_COMMANDS.put(removeKeyCommand.getName(), removeKeyCommand);
        CLIENT_AVAILABLE_COMMANDS.put(clearCommand.getName(), clearCommand);
        CLIENT_AVAILABLE_COMMANDS.put(exitCommand.getName(), exitCommand);
        CLIENT_AVAILABLE_COMMANDS.put(historyCommand.getName(), historyCommand);
        CLIENT_AVAILABLE_COMMANDS.put(removeLowerKeyCommand.getName(),
                removeLowerKeyCommand);
        CLIENT_AVAILABLE_COMMANDS.put(minByAgeCommand.getName(), minByAgeCommand);
        CLIENT_AVAILABLE_COMMANDS.put(filterGreaterThanTypeCommand.getName(),
                filterGreaterThanTypeCommand);
        CLIENT_AVAILABLE_COMMANDS.put(printFieldDescendingAgeCommand.getName(),
                printFieldDescendingAgeCommand);

        SERVER_AVAILABLE_COMMANDS.put(helpCommandServer.getName(), helpCommandServer);
        SERVER_AVAILABLE_COMMANDS.put(exitCommandServer.getName(), exitCommandServer);
    }

    public CommandResult executeClientCommand(Storage storage, Request request, DBStorage dbStorage) {
        CommandResult commandResult;

        ClientCommand command = CLIENT_AVAILABLE_COMMANDS.get(request.getCommandName());
        storage.fillHistory(command);
        commandResult = command.execute(storage, request, dbStorage);

        return commandResult;
    }

    public CommandResult executeServerCommand(Storage storage, String string) {
        CommandResult commandResult = null;

        string = string.trim();
        String[] splintedString = string.split(" ");
        String commandName = splintedString[0].toLowerCase();
        String[] commandsArgs = Arrays.copyOfRange(splintedString, 1, splintedString.length);

        if(SERVER_AVAILABLE_COMMANDS.containsKey(commandName)) {
            ServerCommand command = SERVER_AVAILABLE_COMMANDS.get(commandName);
            commandResult = command.execute(storage, commandsArgs);
            commandResult.showCommandResult();
        } else {
            OutputMessage.printErrorMessage("\nThere is no such command, for reference, enter command \"help\" ");
        }

        return commandResult;
    }
}
