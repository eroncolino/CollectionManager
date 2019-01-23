package tests;

import main.CredentialsValidator;
import main.DatabaseHandler;
import main.DatabaseHandlerFactory;
import main.SQLiteHandler;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Tests if {@link CredentialsValidator} works as expected.
 *
 * @author Elena Roncolino
 */
public class DatabaseHandlerFactoryTest {

    /**
     * Tests if the database handler factory returns the right database handler instance.
     */
    @Test
    public void databaseHandlerFactoryTest() {
        DatabaseHandlerFactory databaseHandlerFactory = new DatabaseHandlerFactory();
        DatabaseHandler sqlDatabaseHandler = databaseHandlerFactory.getDatabaseHandler("sqlite");
        DatabaseHandler nullHandler = databaseHandlerFactory.getDatabaseHandler("");

        assertTrue(sqlDatabaseHandler instanceof SQLiteHandler);
        assertNull(nullHandler);
    }
}