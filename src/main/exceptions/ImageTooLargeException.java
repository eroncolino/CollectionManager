package main.exceptions;

import javax.swing.*;

/**
 * Class that handles the exception thrown when an image has a size which exceeds 500MB.
 *
 * @author Elena Roncolino.
 */
public class ImageTooLargeException extends RuntimeException {

    /**
     * Constructor that allows the exception to be thrown.
     *
     * @param message The message to be displayed in the JOptionPane.
     */
    public ImageTooLargeException(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
