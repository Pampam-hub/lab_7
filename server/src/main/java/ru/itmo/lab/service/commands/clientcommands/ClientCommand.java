package ru.itmo.lab.service.commands.clientcommands;

import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

import java.util.Arrays;
import java.util.List;

public abstract class ClientCommand {
    private String name;
    private String description;
    private List<String> args;


    // commandEntry(всякая шняга типа "кто дал аргументы")

    public ClientCommand(String name, String description, String... args){
        this.name = name;
        this.description = description;
        this.args = Arrays.asList(args);
    }

    public abstract CommandResult execute(Storage storage, Request request, DBStorage dbStorage);

    public String getName() {
        return name;
    }

    public List<String> getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return name + " - " + description
                + (args.get(0).equals("arguments aren't needed") ? "" : "\n args: " + args.get(0));
    }
}
