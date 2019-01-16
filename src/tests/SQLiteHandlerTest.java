package tests;

import main.Car;
import main.SQLiteHandler;
import main.exceptions.UserNotFoundException;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;

import static junit.framework.TestCase.*;

/**
 * Tests if {@link main.SQLiteHandler} works as expected.
 */
public class SQLiteHandlerTest {

    @Test
    public void SQLiteHandlerTest() {
        String testFilename = "testDb";

        SQLiteHandler handler = new SQLiteHandler();
        handler.initialize(testFilename);

        assertEquals(testFilename, handler.getDatabaseName());

        try {
            handler.getUser("dsnflsd", "gnlfs");
            fail("User should not exist.");
        } catch (UserNotFoundException e) {
            //If the exception is catched the test has succeeded.
        }

        handler.addUser("administrator", "admin", null);
        //False if the username already exists
        assertFalse(handler.usernameAlreadyExists("administrator"));

        try {
            assertEquals(0, handler.getTotalNumberOfRecordsByUserId(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Car car = new Car("Fiesta", "Ford", 1500, 90, 75, 4, "Diesel", 2);
        handler.insertCar(car);
        assertEquals(1, handler.getAddedRecordsNumber());
        Car car2 = new Car("Fiesta", "Ford", 2000, 110, 90, 4, "Gasoline", 2);
        handler.insertCar(car2);

        handler.deleteCar(1);
        assertEquals(1, handler.getDeletedRecordsNumber());
        try {
            assertEquals(1, handler.getTotalNumberOfRecordsByUserId(2));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        handler.closeConnection();

        //Deletes database file after executing test, otherwise if the tests are re-executed, they won't work
        File file = new File(testFilename);
        Path path = Paths.get(file.getAbsolutePath());
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
