package academy.mischok.persondatabase.repository;

import academy.mischok.persondatabase.database.DatabaseConnection;
import academy.mischok.persondatabase.database.query.FilterQuery;
import academy.mischok.persondatabase.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The PersonRepository class provides methods for interacting with the database.
 * It includes methods for saving, finding, deleting, and counting persons.
 */
public class PersonRepository {
    private final DatabaseConnection database;

    /**
     * Constructs a new PersonRepository with the given database connection.
     *
     * @param database the database connection to use
     */
    public PersonRepository(DatabaseConnection database) {
        this.database = database;
    }

    /**
     * Saves a person to the database.
     * If the person already has an ID, their details are updated.
     *
     * @param person the person to save
     */
    public void savePerson(final Person person) {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement((person.getId() == null ?
                             "INSERT INTO person (first_name, last_name, email, country, birthday, salary, bonus) VALUES (?, ?, ?, ?, ?, ?, ?)" :
                             "UPDATE person SET first_name = ?, last_name = ?, email = ?, country = ?, birthday = ?, salary = ?, bonus = ? WHERE id = ?"),
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, person.getFirstName());
            statement.setString(2, person.getLastName());
            statement.setString(3, person.getEmail());
            statement.setString(4, person.getCountry());
            statement.setDate(5, new java.sql.Date(person.getBirthday().getTime()));
            statement.setInt(6, person.getSalary());
            if (person.getBonus() == null) {
                statement.setNull(7, 4);
            } else {
                statement.setInt(7, person.getBonus());
            }
            if (person.getId() != null) {
                statement.setLong(8, person.getId());
            }
            statement.executeUpdate();
            if (person.getId() == null) {
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    if (rs.next()) {
                        person.setId(rs.getLong(1));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Finds a person in the database by their ID.
     *
     * @param id the ID of the person to find
     * @return an Optional containing the found person, or an empty Optional if no person was found
     */
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

    /**
     * Deletes a person from the database.
     *
     * @param person the person to delete
     * @return true if the person was deleted, false otherwise
     */
    public boolean deletePerson(Person person) {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("DELETE FROM person WHERE id = ?;")) {
            statement.setLong(1, person.getId());
            if (!statement.execute() && statement.getUpdateCount() == 1) {
                return true;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    /**
     * Finds all persons in the database.
     *
     * @return a list of all persons
     */
    public List<Person> findAll() {
        final List<Person> results = new ArrayList<>();
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person;")) {
            this.findAllFromResultSet(results, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    /**
     * Counts the number of persons in the database.
     *
     * @return the number of persons
     */
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


    /**
     * Checks if the database is empty.
     *
     * @return true if the database is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Finds persons in the database that match the given filter query.
     *
     * @param filterQuery the filter query to use for finding persons
     * @return a list of persons that match the filter query
     */
    @SuppressWarnings("SqlSourceToSinkFlow")
    public List<Person> findByFilterQuery(FilterQuery filterQuery) {
        final List<Person> result = new ArrayList<>();
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("SELECT * FROM person " + filterQuery.buildFilter())) {
            findAllFromResultSet(result, statement);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }


    /**
     * Processes the result set of a list of persons
     *
     * @param results   the final list
     * @param statement the statement the result set is built from
     * @throws SQLException gets thrown if an exception occurs
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

    /**
     * Deletes all persons from the database.
     */
    public void deleteAll() {
        try (final Connection connection = this.database.getConnection();
             final PreparedStatement statement = connection.prepareStatement("TRUNCATE person")) {
            statement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}