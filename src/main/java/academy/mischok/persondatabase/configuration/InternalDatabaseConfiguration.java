package academy.mischok.persondatabase.configuration;

public class InternalDatabaseConfiguration implements IDatabaseConfiguration {

    @Override
    public String getServer() {
        return "127.0.0.1";
    }

    @Override
    public String getUsername() {
        return "root";
    }

    @Override
    public String getDatabase() {
        return "persondatabase";
    }

    @Override
    public String getPassword() {
        return "2070";
    }
}