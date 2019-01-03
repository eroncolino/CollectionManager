import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author  Elena Roncolino
 *
 * This is a singleton class that allows to create
 * one and only one connection to the SQLite database
 */

public class DatabaseConnection {
    private static Connection conn;

    /**
     * Private constructor that allows that only one instance
     * of the connection exists in the JVM
     */
    private DatabaseConnection(){

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:CarDB.db");
            System.out.println("Connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *  Getter that provides a global access to get the instance
     *  of the connection
     *
     * @return DatabaseConnection
     */

    public static Connection getInstance(){
        if (conn == null)
            new DatabaseConnection();

        return conn;
    }

    /**
     * Method that allows to close the connection to the database one the application is shut down.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("Problems closing the database connection.");
            }
        }
    }
}
