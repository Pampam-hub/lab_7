package ru.itmo.lab.repository.factories;

import ru.itmo.lab.repository.Factory;
import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.entity.DragonCharacter;
import ru.itmo.lab.entity.DragonType;
import ru.itmo.lab.service.OutputMessage;
import ru.itmo.lab.service.handlers.DragonValidator;

import java.time.LocalDateTime;

public class FileDragonFactory implements Factory {
    Dragon dragon;

    public FileDragonFactory(Dragon dragon) {
        this.dragon = dragon;
    }

    @Override
    public void generateDragonData() {
        try {
            checkId();
            checkName();
            checkX();
            checkY();
            checkDate();
            checkAge();
            checkWingspan();
            checkDragonType();
            checkDragonCharacter();
        } catch (IllegalArgumentException e) {
            OutputMessage.printErrorMessage(e.getMessage() + ", change collection, please");
            System.exit(0);
        }

    }

    void checkId() throws IllegalArgumentException {
        Integer id = dragon.getId();
        DragonValidator<Integer> dragonValidator = new DragonValidator<>(id);
        dragonValidator.validateValueNull(false, "id");
    }


    void checkName() throws IllegalArgumentException {
        String name = dragon.getName();
        System.out.println(name);
        DragonValidator<String> dragonValidator = new DragonValidator<>(name);
        dragonValidator.validateValueNull(false, "name");
    }

    void checkX() throws IllegalArgumentException {
        long x = dragon.getCoordinates().getX();
        DragonValidator<Long> dragonValidator = new DragonValidator<>(x);
        dragonValidator.validateValueNull(false, "x");
    }

    void checkY() throws IllegalArgumentException {
        float y = dragon.getCoordinates().getY();
        DragonValidator<Float> dragonValidator = new DragonValidator<>(y);
        dragonValidator.validateValueNull(false, "y");
    }

    void checkDate() throws IllegalArgumentException {
        LocalDateTime dateTime = dragon.getCreationDate();
        DragonValidator<LocalDateTime> dragonValidator =
                new DragonValidator<>(dateTime);
        dragonValidator.validateValueNull(false, "date");
        if(dateTime.compareTo(LocalDateTime.now()) > 0) {
            throw new IllegalArgumentException("date must be in past or present");
        }
    }

    void checkAge() throws IllegalArgumentException {
        int age = dragon.getAge();
        DragonValidator<Integer> dragonValidator = new DragonValidator<>(age);
        dragonValidator.validateValueNull(false, "age");
    }

    void checkWingspan() throws IllegalArgumentException {
        int wingspan = dragon.getWingspan();
        DragonValidator<Integer> dragonValidator = new DragonValidator<>(wingspan);
        dragonValidator.validateValueNull(false, "wingspan");
    }

    void checkDragonType() throws IllegalArgumentException {
        DragonType dragonType = dragon.getType();
        DragonValidator<DragonType> dragonValidator = new DragonValidator<>(dragonType);
        dragonValidator.validateValueNull(false, "type");
    }

    void checkDragonCharacter() throws IllegalArgumentException {
        DragonCharacter dragonCharacter = dragon.getDragonCharacter();
        DragonValidator<DragonCharacter> dragonValidator = new DragonValidator<>(dragonCharacter);
        dragonValidator.validateValueNull(false, "dragon character");
    }

}
