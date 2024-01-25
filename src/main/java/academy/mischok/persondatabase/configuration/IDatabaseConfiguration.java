package academy.mischok.persondatabase.configuration;

/**
 * The IDatabaseConfiguration interface defines the methods that a class must implement to provide database configuration.
 * It includes methods to get the server, username, database, and password.
 * This interface is implemented by classes that provide specific configurations for different types of databases, such as internal and external databases.
 */
public interface IDatabaseConfiguration {

    /**
     * Returns the server of the database.
     *
     * @return the server of the database
     */
    String getServer();

    /**
     * Returns the username of the database.
     *
     * @return the username of the database
     */
    String getUsername();

    /**
     * Returns the name of the database.
     *
     * @return the name of the database
     */
    String getDatabase();

    /**
     * Returns the password of the database.
     *
     * @return the password of the database
     */
    String getPassword();

}