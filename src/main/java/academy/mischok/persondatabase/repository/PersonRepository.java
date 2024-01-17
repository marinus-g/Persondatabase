package academy.mischok.persondatabase.repository;

import academy.mischok.persondatabase.database.DatabaseConnection;
import academy.mischok.persondatabase.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonRepository {
    private final DatabaseConnection database;

    public PersonRepository(DatabaseConnection database) {
        this.database = database;
    }

    public void savePerson(final Person person) {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("INSERT INTO person SET first_name = ?, last_name = ?, email = ?, country = ?, birthday = ?, salary = ?, bonus = ? " +
                     "ON DUPLICATE KEY UPDATE first_name = ?, last_name = ?, email = ?, country = ?, birthday = ?, salary = ?, bonus = ?;", PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getCountry());
            statement.setDate(5, new java.sql.Date(person.getBirthday().getTime()));
            statement.setInt(6, person.getSalary());
            statement.setInt(7, person.getBonus());
            statement.setString(8, person.getFirstName());
            statement.setString(9, person.getLastName());
            statement.setString(10, person.getEmail());
            statement.setString(11, person.getCountry());
            statement.setDate(12, new java.sql.Date(person.getBirthday().getTime()));
            statement.setInt(13, person.getSalary());
            statement.setInt(14, person.getBonus());
            statement.execute();
            try (final ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    person.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Person> findPersonById(final long id) {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE id = ?;")) {
            statement.setLong(1, id);
            try (final ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Person(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("country"),
                            rs.getDate("birthday"),
                            rs.getInt("salary"),
                            rs.getInt("bonus")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    public List<Person> findPersonByFirstName(final String firstName) {
        final List<Person> results = new ArrayList<>();
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE first_name = ?;")) {
            statement.setString(1, firstName);
            findAllFromResultSet(results, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public void test() {

        // Wolfgang Metzger
        // Wolfgang Kremer
        List<Person> test = findPersonByFirstName("Wolfgang");
    }

    public List<Person> findPersonByLastName(final String lastName) {
        final List<Person> results = new ArrayList<>();
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE last_name = ?;")) {
            statement.setString(1, lastName);
            this.findAllFromResultSet(results, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public List<Person> findByPersonByFirstOrLastName(final String firstOrLastName) {
        final List<Person> results = new ArrayList<>();
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person WHERE first_name = ? OR last_name = ?;")) {
            statement.setString(1, firstOrLastName);
            statement.setString(2, firstOrLastName);
            this.findAllFromResultSet(results, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public boolean deletePerson(Person person) {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE id = ?;")) {
            statement.setLong(1, person.getId());
            if (statement.executeUpdate() == 0) {
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public List<Person> findAllPersons() {
        final List<Person> results = new ArrayList<>();
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person;")) {
            this.findAllFromResultSet(results, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public long size() {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT COUNT(id) FROM person;");
             final ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0L;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    /*
    public Optional<Person> findPersonByFilterQuery(FilterQuery query) {

    }
     */

    private void findAllFromResultSet(List<Person> results, PreparedStatement statement) throws SQLException {
        try (final ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                results.add(new Person(
                        rs.getLong("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("country"),
                        rs.getDate("birthday"),
                        rs.getInt("salary"),
                        rs.getInt("bonus")
                ));
            }
        }
    }
}