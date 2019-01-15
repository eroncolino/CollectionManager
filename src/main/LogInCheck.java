package main;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that checks if the usenname and the two password entered meet the requirements.
 * @author Elena Roncolino
 */
public class LogInCheck {

    /**
     * Method that checks if the username has less than 5 characters and if it contains more than one dash, underscore or period.
     * @param username The username entered.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>, whiche means that all entered data is correct.
     */
    public static boolean checkDataRequirements(String username, String password, String confirm) {

        //Check username length
        if (username.length() < 5 || username.length() > 30) {
            JOptionPane.showMessageDialog(null, "The username must be between 5 and 30 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        else {
            //Check if username contains more than one dot, one dash or one underscore
            Pattern p = Pattern.compile("[-._][-._]+");
            Matcher m = p.matcher(username);

            if (m.find()) {
                JOptionPane.showMessageDialog(null, "The username can only contain one dash, one underscore or one dot!", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else {
                return checkAlreadyUsedUsername(username, password, confirm);
            }
        }
    }

    /**
     * Method that checks if the username entered already exists in the database.
     * @param username The username entered.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>.
     */
    private static boolean checkAlreadyUsedUsername (String username, String password, String confirm){
        try {
            if (DatabaseConnection.getInstance().usernameAlreadyExists(username))
                return checkPasswordRequirements(password, confirm);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method that checks if the password has more than 5 characters and, if it does, it calls the checkMatchingPassword() method.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>.
     */
    private static boolean checkPasswordRequirements(String password, String confirm) {

        if (password.length() < 5) {
            JOptionPane.showMessageDialog(null, "The password must be at least 5 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else
            return checkMatchingPasswords(password, confirm);
    }

    /**
     * Method that checks if the two password entered match.
     * @param password The password entered.
     * @param confirmedPassword The second confirm password entered.
     * @return <code>true</code> if the two password coincide.
     */
    private static boolean checkMatchingPasswords(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            JOptionPane.showMessageDialog(null, "The two passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Method that checks if the entered credentials are correct and retrieves the right user.
     * @param username The entered username.
     * @param password The entered password.
     */
    public static void checkCredentials(String username, String password) {
        if (username.length() < 5 || username.length() > 30) {
            JOptionPane.showMessageDialog(null, "The username must be between 5 and 30 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (password.length() < 5){
                JOptionPane.showMessageDialog(null, "The password must be at least 5 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                try {
                    DatabaseConnection.getInstance().getUser(username, password);
                    String message = "Welcome back " + username + "! Your data will be loaded.";
                    JOptionPane.showMessageDialog(null, message, "Successful login", JOptionPane.PLAIN_MESSAGE);
                    Main.showCarPanel();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
