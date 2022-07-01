package ru.itmo.lab.service.DB;

import ru.itmo.lab.entity.*;
import ru.itmo.lab.repository.Storage;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.service.commands.clientcommands.ClientCommand;
import ru.itmo.lab.service.handlers.StringEncryptor;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DBStorage {
    private final DBConnectable dbConnector;

    public DBStorage(DBConnectable dbConnector) {
        this.dbConnector = dbConnector;
    }

    public Map<Integer, Dragon> loadCollection() throws SQLException {
        Map<Integer, Dragon> dragons = new ConcurrentHashMap<>();
        return dbConnector.handleQuery((Connection connection) -> {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM dragons");
            while (result.next()) {
            DragonType dragonType
                    = DragonType.valueOf(result.getString("dragon_type"));
            DragonCharacter dragonCharacter
                    = DragonCharacter.valueOf(result.getString("dragon_character"));
            DragonHead dragonHead = new DragonHead();
            dragonHead.setEyesCount(result.getDouble("eyes_count"));

                Integer id = result.getInt("id");
                dragons.put(id, new DragonBuilder()
                        .setId(id)
                        .setName(result.getString("name"))
                        .setCoordinates(result.getLong("x"), result.getFloat("y"))
                        .setDate(result.getDate("creation_date").toLocalDate().atStartOfDay())
                        .setAge(result.getInt("age"))
                        .setWingspan(result.getInt("wingspan"))
                        .setDragonType(dragonType)
                        .setDragonCharacter(dragonCharacter)
                        .setDragonHead(dragonHead)
                        .build());
            }
            return dragons;
        });
    }

    public void addElement(Integer id, Dragon entity, String username)
            throws SQLException, EntityAlreadyExistsException, EntityNotFoundException {
        dbConnector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM dragons WHERE dragons.id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if (result.getInt("count") == 1) {
                throw new EntityAlreadyExistsException(Dragon.class, id);
            }


            String addElementQuery = "INSERT INTO dragons " +
                    "(id, creation_date, name, x, y, age, wingspan, dragon_type, dragon_character, " +
                    "eyes_count, owner_id) " +
                    "SELECT ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, id FROM users WHERE users.login = ?;";
            preparedStatement = connection.prepareStatement(addElementQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setDate(2, Date.valueOf(entity.getCreationDate().toLocalDate()));
            preparedStatement.setString(3, entity.getName());
            preparedStatement.setLong(4, entity.getCoordinates().getX());
            preparedStatement.setFloat(5, entity.getCoordinates().getY());
            preparedStatement.setInt(6, entity.getAge());
            preparedStatement.setInt(7, entity.getWingspan());
            preparedStatement.setString(8, entity.getType().toString());
            preparedStatement.setString(9, entity.getDragonCharacter().toString());
            if (entity.getDragonHead() == null) {
                preparedStatement.setNull(10, Types.DOUBLE);
            } else {
                preparedStatement.setDouble(10, entity.getDragonHead().getEyesCount());
            }
            preparedStatement.setString(11, username);
            preparedStatement.executeUpdate();
        });
    }

    public void update(Integer id, Dragon entity, String username)
            throws SQLException, EntityAlreadyExistsException, EntityNotFoundException {
        dbConnector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM dragons, users WHERE dragons.id = ? "
                    + "AND dragons.owner_id = users.id AND users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if (result.getInt("count") <= 0) {
                throw new EntityNotFoundException(Dragon.class, id);
            }

            connection.createStatement().executeUpdate("BEGIN TRANSACTION;");
            String updateQuery = "UPDATE dragons "
                    + "SET name = ?, "
                    + "x = ?, "
                    + "y = ?, "
                    + "age = ?, "
                    + "wingspan = ?, "
                    + "dragon_type = ?, "
                    + "dragon_character = ?, "
                    + "eyes_count = ? "
                    + "FROM users WHERE dragons.id = ? "
                    + "AND dragons.owner_id = users.id "
                    + "AND users.login = ?;";
            preparedStatement = connection.prepareStatement(updateQuery);
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getCoordinates().getX());
            preparedStatement.setFloat(3, entity.getCoordinates().getY());
            preparedStatement.setInt(4, entity.getAge());
            preparedStatement.setInt(5, entity.getWingspan());
            preparedStatement.setString(6, entity.getType().toString());
            preparedStatement.setString(7, entity.getDragonCharacter().toString());
            if (entity.getDragonHead() == null) {
                preparedStatement.setNull(8, Types.DOUBLE);
            } else {
                preparedStatement.setDouble(8, entity.getDragonHead().getEyesCount());
            }
            preparedStatement.setInt(9, id);
            preparedStatement.setString(10, username);
            preparedStatement.executeUpdate();
            connection.createStatement().execute("COMMIT;");
        });
    }

    public void remove(Integer id, String username)
            throws EntityNotFoundException, EntityAlreadyExistsException, SQLException {
        dbConnector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM dragons, users WHERE dragons.id = ? "
                    + "AND dragons.owner_id = users.id AND users.login = ?;";
            ;
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if (result.getInt("count") <= 0) {
                throw new EntityNotFoundException(Dragon.class, id);
            }


            String removeQuery = "DELETE "
                    + "FROM dragons USING users WHERE dragons.id = ? "
                    + "AND dragons.owner_id = users.id AND users.login = ?;";
            preparedStatement = connection.prepareStatement(removeQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        });
    }

    public List<Integer> removeLowerKey(Integer id, String username)
            throws EntityAlreadyExistsException, SQLException, EntityNotFoundException {
        return dbConnector.handleQuery((Connection connection) -> {

            String removeLowerKeyQuery = "DELETE "
                    + "FROM dragons USING users WHERE dragons.id < ? "
                    + "AND dragons.owner_id = users.id AND users.login = ?"
                    + "RETURNING dragons.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(removeLowerKeyQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> idList = new ArrayList<>();
            while (resultSet.next()) {
                idList.add(resultSet.getInt("id"));
            }
            return idList;
        });
    }

    public List<Integer> removeAll(String username) throws SQLException {
        return dbConnector.handleQuery((Connection connection) -> {

            String clearQuery = "DELETE FROM dragons USING users "
                    + "WHERE dragons.owner_id = users.id "
                    + "AND users.login = ? RETURNING dragons.id;";
            PreparedStatement preparedStatement = connection.prepareStatement(clearQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> idList = new ArrayList<>();
            System.out.println(resultSet);
            while (resultSet.next()) {
                idList.add(resultSet.getInt("id"));
            }
            return idList;
        });
    }

    public void addUser(String username, String password)
            throws SQLException, EntityAlreadyExistsException, EntityNotFoundException {
        dbConnector.handleQuery((Connection connection) -> {
            String addUserQuery = "INSERT INTO users (login, password) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(addUserQuery,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, StringEncryptor.encryptString(password));
            preparedStatement.executeUpdate();
        });
    }

    public String getPassword(String username) throws SQLException {
        return dbConnector.handleQuery((Connection connection) -> {
            String getPasswordQuery = "SELECT (password) "
                    + "FROM users WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(getPasswordQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("password");
            }
            return null;
        });
    }

    public void validateUser(String username, String password) throws SQLException, IllegalArgumentException {
        if (!dbConnector.handleQuery((Connection connection) ->
                StringEncryptor.encryptString(password).equals(getPassword(username)))) {
            throw new IllegalArgumentException("Такого пользователя не существует");
        }
    }

    public boolean checkUsersExistence(String username) throws SQLException {
        return dbConnector.handleQuery((Connection connection) -> {
            String existenceQuery = "SELECT COUNT (*) "
                    + "FROM users "
                    + "WHERE users.login = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(existenceQuery);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("count") > 0;
        });
    }
}