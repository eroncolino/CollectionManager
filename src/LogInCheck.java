import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that checks if the usenname and the two password entered meet the requirements.
 * @author Elena Roncolino
 */
public class LogInCheck {
    private static Connection connection = DatabaseConnection.getInstance();

    /**
     * Method that checks if the username has less than 5 characters and if it contains more than one dash, underscore or period.
     * @param username The username entered.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>, whiche means that all entered data is correct.
     */
    public static boolean checkDataCorrectness(String username, String password, String confirm) {
        System.out.println(confirm);

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
     *
     * @param username The username entered.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>.
     */
    private static boolean checkAlreadyUsedUsername (String username, String password, String confirm){
        String query = "SELECT * FROM user WHERE username = ?";

        PreparedStatement s;

        try {
            s = connection.prepareStatement(query);
            s.setString(1, username);

            ResultSet rs = s.executeQuery();

            if(rs.next()){
                String message = "This username already exists in the database. If you are already registered, " +
                        "please log in. If you are a new user, please choose a different username!";
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return checkPasswordRequirements(password, confirm);
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

    public static void checkUserExists(String username, String password) {
        if (username.length() < 5 || username.length() > 30) {
            JOptionPane.showMessageDialog(null, "The username must be between 5 and 30 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (password.length() < 5){
                JOptionPane.showMessageDialog(null, "The password must be at least 5 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String checkData = "SELECT * FROM user WHERE username = ? AND password = ?";
                try {
                    PreparedStatement s = connection.prepareStatement(checkData);
                    s.setString(1, username);
                    HasherFactory hasherFactory = new HasherFactory();
                    String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(password);
                    s.setString(2, hashedPassword);

                    ResultSet rs = s.executeQuery();

                    if (rs.next()){
                        String message = "Welcome back " + username + "! Your data will be loaded.";
                        JOptionPane.showMessageDialog(null, message, "Successful login", JOptionPane.PLAIN_MESSAGE);


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
