import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class SQLiteHandler implements DatabaseHandler {
    Connection connection = DatabaseConnection.getInstance();

    @Override
    public void initialize(String databaseName) {

    }

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public void userAlreadyExists(String username) {

    }

    @Override
    public void addUser(User user) {
        PreparedStatement s;
        String query;

        HasherFactory hasherFactory = new HasherFactory();
        String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(user.password);

        try {
            if (user.image != null) {    //If the user has chosen an image, store it

                FileInputStream inputImage = new FileInputStream(user.image);

                query = "INSERT INTO user (username, password, image) VALUES (?,?,?)";
                s = connection.prepareStatement(query);
                s.setString(1, user.username);
                s.setString(2, hashedPassword);
                s.setBinaryStream(3, inputImage, (int)user.image.length());
            }

            else {    //The user has not chosen any image

                query = "INSERT INTO user (username, password) VALUES (?,?)";
                s = connection.prepareStatement(query);
                s.setString(1, user.username);
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

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public void insertCar(Car car) {

    }

    @Override
    public void deleteCar(Car car) {

    }
}
