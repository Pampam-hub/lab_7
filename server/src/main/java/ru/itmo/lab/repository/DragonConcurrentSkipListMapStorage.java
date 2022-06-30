package ru.itmo.lab.repository;

import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.service.commands.clientcommands.ClientCommand;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

public class DragonConcurrentSkipListMapStorage implements Storage<Integer, Dragon> {
    /**
     * Счетчик id элементов, служит для обеспечения уникальности поля id у каждого элемента
     */
    private static Integer idCounter = 1;
    private Map<Integer, Dragon> dragonConcurrentSkipListMap;
    private final Deque<String> history;
    private LocalDate dateOfInitialization;
    private HashSet<String> previousFiles = new HashSet<>();

    public DragonConcurrentSkipListMapStorage() {
        dateOfInitialization = LocalDate.now();
        history = new ArrayDeque<>();
    }

    public void setIdCounter(Integer id) {
        idCounter = id;
    }

    @Override
    public void addElement(Integer id, Dragon entity)
            throws EntityAlreadyExistsException {
        if (dragonConcurrentSkipListMap.containsKey(id)) {
            throw new EntityAlreadyExistsException(Dragon.class, id);
        }
        entity.setId(id);
        entity.setCreationDate(LocalDateTime.now());
        dragonConcurrentSkipListMap.put(id, entity);
        if(id > idCounter) {
            idCounter = ++id;
        }
    }

    @Override
    public void loadCollection(Map<Integer, Dragon> dragonConcurrentSkipListMap) {
        this.dragonConcurrentSkipListMap = dragonConcurrentSkipListMap;
    }

    @Override
    public Dragon read(Integer id)
            throws EntityNotFoundException {
        Dragon dragon = dragonConcurrentSkipListMap.get(id);
        if (dragon == null) {
            throw new EntityNotFoundException(Dragon.class, id);
        }
        return dragon;
    }

    @Override
    public void update(Integer id, Dragon entity)
            throws EntityNotFoundException {
        if (!dragonConcurrentSkipListMap.containsKey(id)) {
            throw new EntityNotFoundException(Dragon.class, id);
        }
        entity.setId(id);
        entity.setCreationDate(LocalDateTime.now());
        dragonConcurrentSkipListMap.replace(id, entity);
    }

    @Override
    public void remove(Integer id)
            throws EntityNotFoundException {
        if (!dragonConcurrentSkipListMap.containsKey(id)) {
            throw new EntityNotFoundException(Dragon.class, id);
        }
        dragonConcurrentSkipListMap.remove(id);
    }

    @Override
    public void removeLower(Dragon entity) {
        entity.setId(idCounter++);
        entity.setCreationDate(LocalDateTime.now());
        for (Integer i = 0; i <= entity.getId(); i++)
            dragonConcurrentSkipListMap.remove(i);
    }

    @Override
    public void removeLowerKey(Integer id) {
        for (Integer i = 0; i < id; i++)
            dragonConcurrentSkipListMap.remove(i);
    }

    @Override
    public void removeAll(List<Integer> listId) {
        Set<Integer> keySet = dragonConcurrentSkipListMap.keySet();
        for(Integer key: keySet) {
            if(listId.contains(key)) dragonConcurrentSkipListMap.remove(key);
        }
    }

    @Override
    public List<Dragon> readAll() {
        return new ArrayList<>(dragonConcurrentSkipListMap.values());
    }

    @Override
    public Dragon min(Comparator<Dragon> com) {
        List<Dragon> listHelper
                = new ArrayList<>(dragonConcurrentSkipListMap.values());
        listHelper.sort(com);
        return listHelper.get(listHelper.size() - 1);
    }

    @Override
    public List<Dragon> sortElements(Comparator<Dragon> com) {
        List<Dragon> listHelper
                = new ArrayList<>(dragonConcurrentSkipListMap.values());
        listHelper.sort(com);
        return listHelper;
    }

    @Override
    public void fillHistory(ClientCommand command) {
        int numElements = 12;
        if (history.size() == numElements) {
            history.removeFirst();
        }
        history.offerLast(command.getName());
    }

    @Override
    public void addPreviousFiles(String file) {
        this.previousFiles.add(file) ;
    }

    @Override
    public void deleteFromPreviousFiles(String file) {
        this.previousFiles.remove(file);
    }

    @Override
    public Deque<String> getHistory() {
        return history;
    }

    @Override
    public HashSet<String> getPreviousFiles() {
        return previousFiles;
    }

    @Override
    public String getInfo() {
        return "Collection type: " + dragonConcurrentSkipListMap.getClass().getSimpleName() +
                ", elements type: " + Dragon.class.getSimpleName() + ", data of " +
                "initialisation: " + dateOfInitialization + ", number of elements: " +
                dragonConcurrentSkipListMap.size();
    }

}
