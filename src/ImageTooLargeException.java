import javax.swing.*;

/**
 * @author Elena Roncolino.
 * Class that handles the exception catched when an image has a size which excedes 500MB.
 */
public class ImageTooLargeException extends RuntimeException{

    /**
     * Constructor that allows the exception to be thrown.
     * @param message The message to be displayed in the JOptionPane.
     */
    public ImageTooLargeException(String message){
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
