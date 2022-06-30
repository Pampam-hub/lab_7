package ru.itmo.lab.entity;

import java.io.Serializable;

public enum DragonType implements Serializable {
    WATER,
    UNDERGROUND,
    FIRE;

    public static StringBuilder show() {
        StringBuilder dragonTypes = new StringBuilder();
        for(DragonType type: values()) {
            dragonTypes.append(type);
            dragonTypes.append(", ");
        }
        dragonTypes.deleteCharAt(dragonTypes.length()-1);
        dragonTypes.deleteCharAt(dragonTypes.length()-1);
        return dragonTypes;
    }
}
