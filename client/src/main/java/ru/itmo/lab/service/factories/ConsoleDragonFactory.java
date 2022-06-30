package ru.itmo.lab.service.factories;

import ru.itmo.lab.service.handlers.DragonValidator;
import ru.itmo.lab.repository.Factory;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.entity.*;
import ru.itmo.lab.repository.ConsoleWorker;

import java.util.Scanner;

public class ConsoleDragonFactory implements Factory {
    private Dragon dragon;
    private Coordinates coordinates = new Coordinates();

    private Scanner scanner = ConsoleWorker.getConsoleWorker().getScanner();

    public ConsoleDragonFactory() {
        dragon = new Dragon();
    }

    @Override
    public void generateDragonData() {
        dragon.setCreationDate();
        setValue("Enter dragon name:", this::setName);
        setValue("Enter x coordinate, value must be an integer", this::setX);
        setValue("Enter y coordinate, value must be a number", this::setY);
        setValue("Enter dragon age, value must be a positive number", this::setAge);
        setValue("Enter dragon wingspan, value must be an integer positive ", this::setWingspan);
        setValue("Enter dragon type, available values: " +
                DragonType.show(), this::setDragonType);
        setValue("Enter dragon character, available values: " +
                DragonCharacter.show(), this::setDragonCharacter);
        setValue("Enter count of dragon eyes, value must be a positive number, " +
                "if you want to leave this field null - press enter", this::setEyesCount);
    }

    void setValue(String message, Runnable runnable) {
        System.out.println(message);
        boolean isRunning = true;
        while (isRunning) {
            try {
                runnable.run();
                isRunning = false;
            } catch (IllegalArgumentException e) {
                OutputMessage.printErrorMessage(e.getMessage() + ", repeat input");
            }

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
                "value of wingspan must be a positive");
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
                "must be from list " + DragonCharacter.show() + " letter case must be the same");
        dragon.setCharacter(dragonValidator.getValue());
    }

    void setEyesCount() throws IllegalArgumentException {
        DragonValidator<Double> dragonValidator = new DragonValidator<>(scanner);
        dragonValidator.validateNull(true);
        dragonValidator.validateFunction(Double::parseDouble, "count of dragon eyes " +
                "must be a number ");
        dragonValidator.validatePredicate(arg -> (double) arg > 0,
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
