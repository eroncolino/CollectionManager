package main;

/**
 * Interface to hast strings. The implementation of this interface will provide
 * a method to hash passwords so that they can be store safely in the database.
 *
 * @author Elena Roncolino
 */
public interface Hasher {

    /**
     * Hashes the given string.
     *
     * @param password The string to be hashed.
     * @return String The hashed password.
     */
    String getSecurePassword(String password);
}
