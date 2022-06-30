package ru.itmo.lab.entity;

import java.io.Serializable;

public enum DragonCharacter implements Serializable {
    GOOD,
    CHAOTIC,
    FICKLE;

    public static StringBuilder show() {
        StringBuilder dragonCharacters = new StringBuilder("");
        for(DragonCharacter character: values()) {
            dragonCharacters.append(character);
            dragonCharacters.append(", ");
        }
        dragonCharacters.deleteCharAt(dragonCharacters.length()-1);
        dragonCharacters.deleteCharAt(dragonCharacters.length()-1);
        return dragonCharacters;
    }
}
