package ru.itmo.lab.repository;

import ru.itmo.lab.entity.Dragon;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.service.commands.clientcommands.ClientCommand;

import java.util.*;

public interface Storage<U, T> {
    void addElement(U id, T entity) throws EntityAlreadyExistsException;
    T read(U id) throws EntityNotFoundException;
    void loadCollection(Map<U, T> map);
    void update(U id, T entity) throws EntityNotFoundException;
    void remove(U id) throws EntityNotFoundException;
    void removeLower(T entity);
    void removeLowerKey(U id);
    // if work with clients we need clientName to removeAll
    void removeAll(List<U> listId);
    List<T> readAll();
    T min(Comparator< Dragon> com);
    List<T> sortElements(Comparator<Dragon> com);
    void fillHistory(ClientCommand command);
    void addPreviousFiles(String file);
    void deleteFromPreviousFiles(String file);
    Deque<String> getHistory();
    HashSet<String> getPreviousFiles();
    String getInfo();
}
