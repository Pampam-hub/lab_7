package ru.itmo.lab.repository.exceptions;

public class EntityAlreadyExistsException extends Exception {
    private static final String messageFormat = "Entity %s with id %d already exists!";

    public EntityAlreadyExistsException(Class<?> clazz, Integer id) {
        super(String.format(messageFormat, clazz.getSimpleName(), id));
    }
}
