package main;

/**
 * Factory to instantiate the desired main.DatabaseHandler.
 *
 * @author Elena Roncolino
 */

public class DatabaseHandlerFactory {

    /**
     * Instantiates a handler of the desired type.
     *
     * @param handlerType The type of handler you want to use.
     * @return main.DatabaseHandler The instance of a handler of the specified type.
     */
    public DatabaseHandler getDatabaseHandler(String handlerType) {
        if (handlerType.equalsIgnoreCase("sqlite"))
            return new SQLiteHandler();

        return null;
    }
}
