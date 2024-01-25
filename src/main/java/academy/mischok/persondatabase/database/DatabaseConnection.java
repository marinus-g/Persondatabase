package academy.mischok.persondatabase.database;

import academy.mischok.persondatabase.configuration.IDatabaseConfiguration;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The DatabaseConnection class is responsible for establishing and managing the connection to the database.
 * It includes methods for getting a connection and setting up the database.
 */
public class DatabaseConnection {

    /**
     * The configuration for the database.
     */
    private final IDatabaseConfiguration configuration;

    /**
     * A flag indicating whether the database has been set up.
     */
    private boolean setup;

    /**
     * Constructs a new DatabaseConnection with the given configuration.
     * It also loads the MySQL JDBC driver and sets up the database.
     *
     * @param configuration the configuration for the database
     */
    @SneakyThrows
    public DatabaseConnection(IDatabaseConfiguration configuration) {
        this.configuration = configuration;
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.setupDatabase();
    }

    /**
     * Returns a connection to the database.
     *
     * @return a connection to the database
     * @throws RuntimeException if a SQLException occurs when getting the connection
     */
    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + configuration.getServer() + ":3307"
                            + (this.setup ? "/" + this.configuration.getDatabase() : "") ,
                    configuration.getUsername(), configuration.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets up the database by creating the schema and table if they do not exist.
     *
     * @throws RuntimeException if a SQLException occurs when setting up the database
     */
    private void setupDatabase() {
        try (Connection connection = this.getConnection();
             PreparedStatement createSchema = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS "+ this.configuration.getDatabase() + ";");
             PreparedStatement selectSchema = connection.prepareStatement("USE " + this.configuration.getDatabase() + ";");
             PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS person (" +
                     "id BIGINT PRIMARY KEY," +
                     "first_name VARCHAR(100) NOT NULL," +
                     "last_name VARCHAR(100) NOT NULL," +
                     "email VARCHAR(256) NOT NULL," +
                     "country VARCHAR(50)," +
                     "birthday DATE NOT NULL," +
                     "salary INT NOT NULL," +
                     "bonus INT," +
                     "INDEX(id)" +
                     ");")) {

            createSchema.execute();
            selectSchema.execute();
            createTable.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            this.setup = true;
        }
    }
}