package academy.mischok.persondatabase.configuration;

/**
 * The InternalDatabaseConfiguration class implements the IDatabaseConfiguration interface.
 * It provides the server, username, database, and password for an internal database.
 * The server is set to "127.0.0.1", the username is set to "root", the database is set to "persondatabase", and the password is set to "2070".
 */
public class InternalDatabaseConfiguration implements IDatabaseConfiguration {

    /**
     * Returns the server for the internal database.
     *
     * @return the server for the internal database
     */
    @Override
    public String getServer() {
        return "127.0.0.1";
    }

    /**
     * Returns the username for the internal database.
     *
     * @return the username for the internal database
     */
    @Override
    public String getUsername() {
        return "root";
    }

    /**
     * Returns the database for the internal database.
     *
     * @return the database for the internal database
     */
    @Override
    public String getDatabase() {
        return "persondatabase";
    }

    /**
     * Returns the password for the internal database.
     *
     * @return the password for the internal database
     */
    @Override
    public String getPassword() {
        return "2070";
    }
}