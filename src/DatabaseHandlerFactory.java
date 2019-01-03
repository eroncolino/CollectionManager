/**
 * Factory to instantiate the desired DatabaseHandler.
 * @author Elena Roncolino
 */

public class DatabaseHandlerFactory {

    /**
     * Method that instantiates a handler of the desired type.
     * @param handlerType The type of handler you want to use.
     * @return DatabaseHandler The instance of a handler of the specified type.
     */
    public DatabaseHandler getDatabaseHandler(String handlerType){
        if (handlerType.equalsIgnoreCase("sqlite"))
            return new SQLiteHandler();

        return null;

    }
}
