package academy.mischok.persondatabase.configuration;

public class InternalDatabaseConfiguration implements IDatabaseConfiguration {

    @Override
    public String getServer() {
        return "localhost";
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getDatabase() {
        return "Persondatabase";
    }

    @Override
    public String getPassword() {
        return "1234password";
    }
}