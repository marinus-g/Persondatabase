package academy.mischok.persondatabase.database;

import academy.mischok.persondatabase.configuration.IDatabaseConfiguration;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {

    private final IDatabaseConfiguration configuration;

    @SneakyThrows
    public DatabaseConnection(IDatabaseConfiguration configuration) {
        this.configuration = configuration;
        Class.forName("com.mysql.cj.jdbc.Driver");
        this.setupDatabase();
    }
// Class.forName("com.mysql.cj.jdbc.Driver");

    public Connection getConnection() {
        // jdbc:mysql://localhost:3306
        try {
            return DriverManager.getConnection("jdbc:mysql://" + configuration.getServer() + ":3306",
                    configuration.getUsername(), configuration.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupDatabase() {
        try (Connection connection = this.getConnection();
             PreparedStatement createSchema = connection.prepareStatement("CREATE SCHEMA IF NOT EXISTS "+ this.configuration.getDatabase() + ";");
             PreparedStatement selectSchema = connection.prepareStatement("USE " + this.configuration.getDatabase() + ";");
             PreparedStatement createTable = connection.prepareStatement("CREATE TABLE IF NOT EXISTS person (" +
                     "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
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
        }
    }
}