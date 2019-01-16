package main;

/**
 * This is a singleton class that allows to create
 * one and only one connection to the SQLite database.
 *
 * @author Elena Roncolino
 */
public class DatabaseConnection {
    private static final DatabaseHandler databaseInstance = new DatabaseHandlerFactory().getDatabaseHandler("sqlite");

    /**
     * Private constructor that allows the existence of one and only one database connection.
     */
    private DatabaseConnection() {
    }

    /**
     * Getter that provides a global access to get the instance of the connection.
     *
     * @return main.DatabaseConnection
     */
    public static DatabaseHandler getInstance() {
        return databaseInstance;
    }
}
