package main;

import main.exceptions.UserNotFoundException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Interface that manages data from and to the database.
 * @author Elena Roncolino
 */
public interface DatabaseHandler {

    /**
     * Creates the database if it has not yet been created.
     * It creates the tables and the <code>admin</code> user so that the application
     * can be tasted.
     * @param databaseName The name of the file from.
     * @throws SQLException If there is a problem in the JDBC.
     */
    void initialize(String databaseName) throws SQLException;

    /**
     * Method that closes the connection to the database.
     */
    void closeConnection();

    /**
     * Method that returns where all the data is stored, i.e., the database name.
     * @return String The name of the database.
     */
    String getDatabaseName();

    /**
     *  Method that allows to check if the chosen username is already stored in the database.
     * @param username The username to be checked.
     * @return boolean Returns <code>true</code> if that username already exists in the database.
     * @throws SQLException If there is a problem in the JDBC.
     */
    boolean usernameAlreadyExists(String username) throws  SQLException;

    /**
     * Method that adds a new user to the database.
     * @param user The user that has to be added to the database.
     * @throws IOException If there is a problem with the image output stream.
     * @throws SQLException If there is a problem in the JDBC.
     */
    void addUser(User user) throws IOException, SQLException;


    /**
     * Method that deletes a user from the database.
     * @param user The user to be deleted.
     */
    void deleteUser(User user);

    /**
     * Method that checks if the credentials are stored in the database and returns the current main.User Object.
     * @param username The username.
     * @param password The password.
     * @return main.User The user corresponding to the username and password entered.
     * @throws SQLException If there is a problem in the JDBC.
     * @throws IOException If there is a problem with the image input stream.
     * @throws UserNotFoundException If the user is not registered in the database.
     */
    User getUser(String username, String password) throws SQLException, IOException, UserNotFoundException;

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
