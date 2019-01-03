/**
 * Interface that manages data from and to the database.
 * @author Elena Roncolino
 */
public interface DatabaseHandler {
// TODO: 1/3/2019
    /**
     * Creates the database if it has not yet been created.
     * It creates the tables and the <code>admin</code> user so that the application
     * can be tasted.
     * @param databaseName The name of the file from
     */
    void initialize(String databaseName);

    /**
     * Method that returns where all the data is stored, i.e., the database name.
     * @return String The name of the database.
     */
    String getDatabaseName();

    /**
     * Method that allows to check if the chosen username is already stored in the database.
     * @param username The username to be checked.
     */
    void userAlreadyExists(String username);

    /**
     * Method that adds a new user to the database.
     * @param user The user that has to be added to the database.
     */
    void addUser(User user);

    /**
     * Method that deletes a user from the database.
     * @param user The user to be deleted.
     */
    void deleteUser(User user);

    /**
     * Method that insert a car record into the database.
     * @param car The car record that has to be inserted.
     */
    void insertCar(Car car);

    /**
     * Method that deletes a car record from the database.
     * @param car The car record to be deleted.
     */
    void deleteCar(Car car);







}
