package ru.itmo.lab.entity;

import ru.itmo.lab.repository.commandresult.CommandResult;

import java.time.LocalDateTime;

public class DragonBuilder {
    Dragon dragon = new Dragon();

    public DragonBuilder setId(Integer id) {
        dragon.id = id;
        return this;
    }

    public DragonBuilder setName(String name) {
        dragon.name = name;
        return this;
    }

    public DragonBuilder setCoordinates(long x, float y) {
        Coordinates coordinates = new Coordinates();
        coordinates.setX(x);
        coordinates.setY(y);
        dragon.coordinates = coordinates;
        return this;
    }

    public DragonBuilder setDate(LocalDateTime creationDate) {
        dragon.creationDate = creationDate;
        return this;
    }

    public DragonBuilder setAge(Integer age) {
        dragon.age = age;
        return this;
    }

    public DragonBuilder setWingspan(int wingspan) {
        dragon.wingspan = wingspan;
        return this;
    }

    public DragonBuilder setDragonType(DragonType dragonType) {
        dragon.dragonType = dragonType;
        return this;
    }

    public DragonBuilder setDragonCharacter(DragonCharacter dragonCharacter) {
        dragon.dragonCharacter = dragonCharacter;
        return this;
    }

    public DragonBuilder setDragonHead(DragonHead dragonHead) {
        dragon.setDragonHead(dragonHead);
        return this;
    }

    public Dragon build() {
        return dragon;
    }
}
