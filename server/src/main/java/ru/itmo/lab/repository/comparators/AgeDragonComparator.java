package ru.itmo.lab.repository.comparators;

import ru.itmo.lab.entity.Dragon;

import java.util.Comparator;

public class AgeDragonComparator implements Comparator<Dragon> {

    @Override
    public int compare(Dragon o1, Dragon o2) {
        return o2.getAge() - o1.getAge();
    }


}
