package main.exceptions;

import javax.swing.*;

/**
 * Class that handles the exception catched when the entered username and password could not match any
 * user present in the database.
 *
 * @author Elena Roncolino.
 */
public class UserNotFoundException extends RuntimeException {
    /**
     * Constructor that allows the exception to be thrown.
     *
     * @param message The message to be displayed in the JOptionPane.
     */
    public UserNotFoundException(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
