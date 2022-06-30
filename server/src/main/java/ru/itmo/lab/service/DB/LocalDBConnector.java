package ru.itmo.lab.service.DB;

import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.service.OutputMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class LocalDBConnector implements DBConnectable {
    private final String dbUrl = "jdbc:postgresql://pg:5432/studs";
    private final String user =  System.getenv("SV_LOGIN");
    private final String pass =  System.getenv("SV_PASS");

    public LocalDBConnector() {
        try {
            Class.forName("org.postgresql.Driver");
            initializeDB();
        } catch (ClassNotFoundException e) {
            OutputMessage.printErrorMessage("No DB driver!");
            System.exit(1);
        } catch (SQLException e) {
            OutputMessage.printErrorMessage("Error occurred during initializing tables!" + e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public void handleQuery(SQLConsumer<Connection> queryBody) throws SQLException, EntityAlreadyExistsException, EntityNotFoundException {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {
            queryBody.accept(connection);
        } catch (SQLException e) {
            throw new SQLException("Error occurred during working with DB: " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public <T> T handleQuery(SQLFunction<Connection, T> queryBody) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbUrl, user, pass)) {
            return queryBody.apply(connection);
        } catch (SQLException e) {
            throw new SQLException("Error occurred during working with DB: " + Arrays.toString(e.getStackTrace()));
        }
    }


    private void initializeDB() throws SQLException{

        Connection connection = DriverManager.getConnection(dbUrl, user, pass);

        Statement statement = connection.createStatement();

        statement.execute("CREATE SEQUENCE IF NOT EXISTS users_id_seq INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 2147483647 CACHE 1");

        statement.execute("CREATE TABLE IF NOT EXISTS users "
                + "("
                + "login VARCHAR(255) NOT NULL UNIQUE CHECK(login<>''),"
                + "password varchar(255) NOT NULL CHECK(password<>''),"
                + "id int NOT NULL PRIMARY KEY DEFAULT nextval('users_id_seq')"
                + ");");

        statement.execute("CREATE TABLE IF NOT EXISTS dragons "
                + "("
                + "id INT NOT NULL PRIMARY KEY,"
                + "creation_date DATE NOT NULL,"
                + "name varchar(50) NOT NULL CHECK(name <> ''),"
                + "x BIGINT NOT NULL,"
                + "y FLOAT NOT NULL,"
                + "age INT NOT NULL CHECK(age > 0),"
                + "wingspan INT NOT NULL CHECK(wingspan > 0),"
                + "dragon_type varchar(11) NOT NULL CHECK(dragon_type = 'WATER' "
                + "OR dragon_type = 'UNDERGROUND' "
                + "OR dragon_type = 'FIRE'),"
                + "dragon_character varchar(7) NOT NULL CHECK(dragon_character = 'GOOD' "
                + "OR dragon_character = 'CHAOTIC' "
                + "OR dragon_character = 'FICKLE'),"
                + "eyes_count DOUBLE,"

                + "owner_id BIGINT NOT NULL REFERENCES users (id)"
                + ");");

        connection.close();
    }
}
