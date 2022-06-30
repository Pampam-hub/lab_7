package ru.itmo.lab.service.factories;

import ru.itmo.lab.repository.ConsoleWorker;
import ru.itmo.lab.repository.Factory;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.entity.*;
import ru.itmo.lab.service.handlers.DragonValidator;

import java.util.Scanner;

public class ScriptDragonFactory implements Factory {
    Scanner scanner;
    private Dragon dragon;
    Coordinates coordinates = new Coordinates();

    public ScriptDragonFactory(Scanner scanner) {
        this.scanner = scanner;
        dragon = new Dragon();
    }

    @Override
    public void generateDragonData() {
        try {
            setName();
            setX();
            setY();
            setAge();
            setWingspan();
            setDragonType();
            setDragonCharacter();
        } catch (IllegalArgumentException e) {
            OutputMessage.printErrorMessage(e.getMessage() + " change script, please");
            ConsoleWorker.getConsoleWorker().setExecutedScript(false);
        }
    }

    void setName() throws IllegalArgumentException {
        DragonValidator<String> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragon.setName(dragonValidator.getValue());
    }

    void setX() throws IllegalArgumentException {
        DragonValidator<Long> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(Long::parseLong,
                "value of x must be an integer");
        coordinates.setX(dragonValidator.getValue());
    }

    void setY() throws IllegalArgumentException {
        DragonValidator<Float> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(Float::parseFloat,
                "value of y must be a number");
        coordinates.setY(dragonValidator.getValue());
        dragon.setCoordinates(coordinates);
    }

    void setAge() throws IllegalArgumentException {
        DragonValidator<Integer> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(Integer::parseInt,
                "value of age must be an integer ");
        dragonValidator.validatePredicate(arg -> (int) arg > 0,
                "value of age must be a positive");
        dragon.setAge(dragonValidator.getValue());
    }

    void setWingspan() throws IllegalArgumentException {
        DragonValidator<Integer> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(Integer::parseInt,
                "value of wingspan must be an integer ");
        dragonValidator.validatePredicate(arg -> (int) arg > 0,
                "value of age must be a positive");
        dragon.setWingspan(dragonValidator.getValue());
    }

    void setDragonType() throws IllegalArgumentException {
        DragonValidator<DragonType> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(DragonType::valueOf, "value of dragon type " +
                "must be from list " + DragonType.show() + " letter case must be the same");
        dragon.setType(dragonValidator.getValue());
    }

    void setDragonCharacter() throws IllegalArgumentException {
        DragonValidator<DragonCharacter> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(false);
        dragonValidator.validateFunction(DragonCharacter::valueOf, "value of dragon character " +
                "must be from list " + DragonType.show() + " letter case must be the same");
        dragon.setCharacter(dragonValidator.getValue());
    }

    void setEyesCount() throws IllegalArgumentException {
        DragonValidator<Double> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(true);
        dragonValidator.validateFunction(Double::parseDouble, "count of dragon eyes " +
                "must be a number ");
        dragonValidator.validatePredicate(arg -> (int) arg > 0,
                "count of eyes must be a positive");
        if(dragonValidator.getValue() != null) {
            DragonHead dragonHead = new DragonHead();
            dragonHead.setEyesCount(dragonValidator.getValue());
            dragon.setDragonHead(dragonHead);
        }
    }

    public Dragon getDragon() {
        return dragon;
    }

}
