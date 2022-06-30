package ru.itmo.lab.service.handlers;

import ru.itmo.lab.repository.commandresult.CommandResult;
import ru.itmo.lab.repository.commandresult.CommandResultBuilder;
import ru.itmo.lab.repository.commandresult.CommandStatus;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.repository.request.Request;
import ru.itmo.lab.service.DB.DBStorage;

import java.sql.SQLException;

public class UsersManager {
    private final DBStorage dbStorage;

    public UsersManager(DBStorage dbStorage) {
        this.dbStorage = dbStorage;
    }

    public CommandResult registerNewUser(Request request) {
        try {
            if (!dbStorage.checkUsersExistence(request.getUsernameArgument())) {
                dbStorage.addUser(request.getUsernameArgument(), request.getPasswordArgument());
                return new CommandResultBuilder()
                        .setMessage("Registration was successful")
                        .setStatus(CommandStatus.SUCCESSFUL)
                        .build();
            } else {
                return new CommandResultBuilder()
                        .setMessage("User with this name already exists")
                        .setStatus(CommandStatus.UNSUCCESSFUL)
                        .build();
            }
        } catch (SQLException | EntityAlreadyExistsException | EntityNotFoundException e) {
            return new CommandResultBuilder()
                    .setMessage("error when working with the database")
                    .setStatus(CommandStatus.UNSUCCESSFUL)
                    .build();
        }
    }

    public CommandResult loginUser(Request request) {
        try {
            dbStorage.validateUser(request.getUsernameArgument(), request.getPasswordArgument());
            return new CommandResultBuilder()
                    .setMessage("Login completed successfully")
                    .setStatus(CommandStatus.SUCCESSFUL)
                    .build();
        } catch (IllegalArgumentException e) {
            return new CommandResultBuilder()
                    .setMessage(e.getMessage())
                    .setStatus(CommandStatus.UNSUCCESSFUL)
                    .build();
        } catch (SQLException e) {
            return new CommandResultBuilder()
                    .setMessage("error when working with the database")
                    .setStatus(CommandStatus.UNSUCCESSFUL)
                    .build();
        }
    }
}
