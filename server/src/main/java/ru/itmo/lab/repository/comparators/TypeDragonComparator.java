package ru.itmo.lab.repository.comparators;

import ru.itmo.lab.entity.Dragon;

import java.util.Comparator;

public class TypeDragonComparator implements Comparator<Dragon> {

    @Override
    public int compare(Dragon o1, Dragon o2) {
        return o1.getType().compareTo(o2.getType());
    }
}
