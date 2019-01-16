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
 */
public class DatabaseHandlerFactoryTest {

    @Test
    public void DatabasaeHandlerFactoryTest() {
        DatabaseHandlerFactory databaseHandlerFactory = new DatabaseHandlerFactory();
        DatabaseHandler sqlDatabaseHandler = databaseHandlerFactory.getDatabaseHandler("sqlite");
        DatabaseHandler nullHandler = databaseHandlerFactory.getDatabaseHandler("");

        assertTrue(sqlDatabaseHandler instanceof SQLiteHandler);
        assertNull(nullHandler);
    }
}