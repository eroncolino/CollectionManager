package main;

import javafx.scene.image.Image;
import main.exceptions.UserNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;

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
     */
    void initialize(String databaseName);

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
     * Gets the number of added record during the current session.
     * @return int The number of added records.
     */
    int getAddedRecordsNumber();

    /**
     * Gets the number of deleted record during the current session.
     * @return int The number of deleted records.
     */
    int getDeletedRecordsNumber();

    /**
     *  Method that allows to check if the chosen username is already stored in the database.
     * @param username The username to be checked.
     * @return boolean Returns <code>true</code> if that username already exists in the database.
     */
    boolean usernameAlreadyExists(String username);

    /**
     * Method that adds a new user to the database.
     * @param username The chosen username.
     * @param password The chosen password.
     * @param image The chosen image.
     */
    void addUser(String username, String password, Image image);

    /**
     * Method that deletes a user from the database.
     * @param userId The user to be deleted.
     */
    void deleteUser(int userId);

    /**
     * Method that checks if the credentials are stored in the database and returns the current main.User Object.
     * @param username The username.
     * @param password The password.
     * @return main.User The user corresponding to the username and password entered.
     * @throws UserNotFoundException If the user is not registered in the database.
     */
    User getUser(String username, String password) throws UserNotFoundException;

    /**
     * Gets the total number of records a user has stored in the database.
     * @param userId The user id.
     * @return int The number of records.
     * @throws SQLException If there is a problem in the JDBC.
     */
    int getTotalNumberOfRecordsByUserId(int userId) throws SQLException;

    /**
     * Method that returns a list of cars given user id as input.
     * @param userId The user id.
     * @return ArrayList A list of cars.
     */
    ArrayList<Car> getCarsByUserId(int userId);

    /**
     * Method that allows to retrieve all the cars that a user has stored in his collection manager.
     * @param userId The id of the user.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     */
    Object[][] getCarsMatrixByUserId(int userId);

    /**
     * Method that allows to retrieve all the cars with the given string value in the specified column.
     * @param userId The user id.
     * @param column The column name where the string has to be matched.
     * @param string The string to be matched.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     */
    Object[][] getCarsByString(int userId, String column, String string);

    /**
     * Method that allows to retrieve all the cars with the given number in the specified column.
     * @param userId The user id.
     * @param column The column name where the number has to be matched.
     * @param number The number to be matched.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     */
    Object[][] getCarsByInt(int userId, String column, int number);

    /**
     * Method that inserts a car record into the database.
     * @param car The car record that has to be inserted.
     */
    void insertCar(Car car);

    /**
     * Method that inserts a car list into the database.
     * @param cars The list of cars to be inserted.
     */
    void insertCarList(ArrayList<Car> cars);

    /**
     * Method that allows to update a car.
     * @param carId The id of the car to be updated.
     * @param name The new car name.
     * @param brand The new car brand.
     * @param cubicCapacity The new cubic capacity.
     * @param ps The new PS value.
     * @param kw The new KW value.
     * @param cylinders The new cylinders value.
     * @param fuel The new fuel type.
     */
    void updateCar(int carId, String name, String brand, int cubicCapacity, int ps, int kw, int cylinders, String fuel);

    /**
     * Method that deletes a car record from the database.
     * @param carId The id of the car to be deleted.
     */
    void deleteCar(int carId);

    /**
     * Method that deletes all the car records of a given user.
     * @param userId The id of the user whose cars have to be deleted.
     */
    void deleteCarsFromUserId(int userId);

}
