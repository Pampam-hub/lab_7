package ru.itmo.lab.service.commands.servercommands;

import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public abstract class ServerCommand {
    private String name;
    private String description;
    private List<String> args;

    // commandEntry(всякая шняга типа "кто дал аргументы")

    public ServerCommand(String name, String description, String... args){
        this.name = name;
        this.description = description;
        this.args = Arrays.asList(args);
    }

    public abstract CommandResult execute(Storage storage, String[] args);

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
