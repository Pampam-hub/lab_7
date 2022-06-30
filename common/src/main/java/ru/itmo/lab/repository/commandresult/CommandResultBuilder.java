package ru.itmo.lab.repository.commandresult;

import ru.itmo.lab.entity.Dragon;

import java.util.Date;
import java.util.Deque;
import java.util.List;

public class CommandResultBuilder {
    CommandResult commandResult = new CommandResult();

    public CommandResultBuilder(){
        commandResult.dateOfCommand = new Date();
    }

    public CommandResultBuilder setMessage(String message) {
        commandResult.message = message;
        return this;
    }

    public CommandResultBuilder setStatus(CommandStatus status) {
        commandResult.status = status;
        return this;
    }

    public CommandResultBuilder setListOfDragons(List<Dragon> listOfDragons) {
        commandResult.listOfDragons = listOfDragons;
        return this;
    }

    public CommandResultBuilder setIntegerList(List<Integer> list) {
        commandResult.list = list;
        return this;
    }

    public CommandResultBuilder setListOfCommands(List<String> listOfCommands) {
        commandResult.listOfCommand = listOfCommands;
        return this;
    }

    public CommandResultBuilder setDeque(Deque<String> deque) {
        commandResult.deque = deque;
        return this;
    }

    public CommandResultBuilder setData(String data) {
        commandResult.data = data;
        return this;
    }

    public CommandResultBuilder setDragon(Dragon dragon) {
        commandResult.dragon = dragon;
        return this;
    }

    public CommandResultBuilder setExit() {
        commandResult.isExit = true;
        return this;
    }

    public CommandResult build() {
        return commandResult;
    }
}

