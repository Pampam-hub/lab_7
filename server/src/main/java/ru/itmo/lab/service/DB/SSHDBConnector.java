package ru.itmo.lab.service.DB;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import ru.itmo.lab.repository.exceptions.EntityAlreadyExistsException;
import ru.itmo.lab.repository.exceptions.EntityNotFoundException;
import ru.itmo.lab.service.OutputMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

public class SSHDBConnector implements DBConnectable {
    private static Session session;
    private final String dbBase = "jdbc:postgresql://";
    private final String dbName = "studs";
    private final int dbPort = 5432;
    private final String dbHost = "pg";

    private String svLogin;
    private String svPass;
    private String svAddr;

    private final int sshPort = 2222;
    private int forwardingPort;


    public SSHDBConnector() {
        try {
//            this.svLogin = System.getenv("SV_LOGIN");
//            this.svPass = System.getenv("SV_PASS");
//            this.svAddr = System.getenv("SV_ADDR");
//            this.forwardingPort = Integer.parseInt(System.getenv("FORWARDING_PORT"));
            this.svLogin = "s335093";
          this.svPass = "fcv144";
            this.svAddr = "se.ifmo.ru";
            this.forwardingPort = 4281;

            connectSSH();
            Class.forName("org.postgresql.Driver");
            initializeDB();
        } catch (ClassNotFoundException e) {
            OutputMessage.printErrorMessage("No DB driver!");
            e.printStackTrace();
            System.exit(1);
        } catch (SQLException e) {
            OutputMessage.printErrorMessage("Error occurred during initializing tables!" + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (JSchException e) {
            OutputMessage.printErrorMessage("Troubles during connecting to DB with ssh!");
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalArgumentException e) {
            OutputMessage.printErrorMessage("Mistakes in environment variables!");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public static void closeSSH() {
        if (session != null) {
            session.disconnect();
        }
    }

    private void connectSSH() throws JSchException {
        Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        JSch jsch = new JSch();
        session = jsch.getSession(svLogin, svAddr, sshPort);
        session.setPassword(svPass);
        session.setConfig(config);
        session.connect();
        session.setPortForwardingL(forwardingPort, dbHost, dbPort);
    }

    @Override
    public void handleQuery(SQLConsumer<Connection> queryBody) throws SQLException, EntityAlreadyExistsException, EntityNotFoundException {
        try (Connection connection = DriverManager.getConnection(dbBase + "localhost:" + forwardingPort + "/" + dbName, svLogin, svPass)) {
            queryBody.accept(connection);
        } catch (SQLException e) {
            throw new SQLException("Error occurred during working with DB: " + Arrays.toString(e.getStackTrace()));
        }
    }

    @Override
    public <T> T handleQuery(SQLFunction<Connection, T> queryBody) throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbBase + "localhost:"
                + forwardingPort + "/" + dbName, svLogin, svPass)) {
            return queryBody.apply(connection);
        } catch (SQLException e) {
            throw new SQLException("Error occurred during working with DB: " + Arrays.toString(e.getStackTrace()));
        }
    }


    private void initializeDB() throws SQLException {

        Connection connection = DriverManager.getConnection(dbBase + "localhost:" + forwardingPort + "/" + dbName, svLogin, svPass);

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
                + "eyes_count DOUBLE PRECISION,"

                + "owner_id BIGINT NOT NULL REFERENCES users (id)"
                + ");");

        connection.close();
    }
}
