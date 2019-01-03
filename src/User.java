import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class User {
    public String username, password;
    public static File image;
    private static Connection connection;

    /**
     * User constructor
     * @param username The username chosen.
     * @param password The password chosen.
     * @param image The chosen profile image.
     */
    public User(String username, String password, File image) {

        this.username = username;
        this.password = password;
        this.image = image;
    }


    public static void registerUser(String username, String password, File image) {
        connection = DatabaseConnection.getInstance();

        PreparedStatement s;
        String query;

        HasherFactory hasherFactory = new HasherFactory();
        String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(password);

        try {
            if (image != null) {    //If the user has chosen an image, store it

                FileInputStream inputImage = new FileInputStream(image);

                query = "INSERT INTO user (username, password, image) VALUES (?,?,?)";
                s = connection.prepareStatement(query);
                s.setString(1, username);
                s.setString(2, hashedPassword);
                s.setBinaryStream(3, inputImage, (int)image.length());
            }

            else {    //The user has not chosen any image

                query = "INSERT INTO user (username, password) VALUES (?,?)";
                s = connection.prepareStatement(query);
                s.setString(1, username);
                s.setString(2, hashedPassword);
            }

            int res = s.executeUpdate();

            if (res > 0){
                String message = "Successful registration!\nPlease sign in with the username and password you choose.";
                JOptionPane.showMessageDialog(null, message, "Congratulations!", JOptionPane.PLAIN_MESSAGE);
            }

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
