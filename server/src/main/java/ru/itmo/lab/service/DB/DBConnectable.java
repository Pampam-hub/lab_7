package ru.itmo.lab.service.DB;

import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;

import java.sql.Connection;
import java.sql.SQLException;

public interface DBConnectable {
    void handleQuery(SQLConsumer<Connection> queryBody) throws SQLException, EntityAlreadyExistsException, EntityNotFoundException;

    <T> T handleQuery(SQLFunction<Connection, T> queryBody) throws SQLException;
}
