import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import jdk.internal.util.xml.impl.Input;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

/**
 * @// TODO: 1/4/2019  
 */
public class SQLiteHandler implements DatabaseHandler {
    private Connection sqliteConnection;
    private String databaseName;

    /**
     * Creates the database if it has not yet been created.
     * It creates the tables and the <code>admin</code> user so that the application
     * can be tasted.
     * @param databaseName The name of the file from.
     */
    @Override
    public void initialize(String databaseName)  {
        this.databaseName = databaseName;

        try {
            sqliteConnection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
            Statement stmt1 = sqliteConnection.createStatement();
            String query1 = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username VARCHAR(30) NOT NULL, " +
                    "password CHAR(32) NOT NULL, " +
                    "image BLOB" +
                    ");";
            stmt1.execute(query1);

            Statement stmt2 = sqliteConnection.createStatement();
            String query2 = "CREATE TABLE IF NOT EXISTS cars (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name VARCHAR(50) NOT NULL,\n" +
                    "brand VARCHAR(50) NOt NULL,\n" +
                    "cubiccapacity INTEGER,\n" +
                    "ps INTEGER,\n" +
                    "kw INTEGER,\n" +
                    "cylinders INTEGER,\n" +
                    "fueltype VARCHAR(30),\n" +
                    "userid INTEGER NOT NULL,\n" +
                    "FOREIGN KEY(userid) REFERENCES users(id)\n" +
                    ");";

            stmt2.execute(query2);

            //todo insert into database the initial 50 records

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that closes the sqliteConnection to the database.
     */
    @Override
    public void closeConnection() {
        if (sqliteConnection != null) {
            try {
                sqliteConnection.close();
            } catch (SQLException e) {
                System.out.println("Problems closing the database sqliteConnection.");
            }
        }
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     *  Method that allows to check if the chosen username is already stored in the database.
     * @param username The username to be checked.
     * @return boolean Returns <code>true</code> if that username already exists in the database.
     */
    @Override
    public boolean usernameAlreadyExists(String username){
        String query = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement stmt = sqliteConnection.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                String message = "This username already exists in the database. If you are already registered, " +
                "please log in. If you are a new user, please choose a different username!";
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Method that adds a new user to the database.
     * @param user The user that has to be added to the database.
     */
    @Override
    public void addUser(User user) {
        PreparedStatement s;
        String query;

        HasherFactory hasherFactory = new HasherFactory();
        String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(user.password);

        try {
            if (user.image != null) {    //If the user has chosen an image, store it

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(user.image, null);
                if(bufferedImage != null)
                    ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                query = "INSERT INTO users (username, password, image) VALUES (?,?,?)";
                s = sqliteConnection.prepareStatement(query);
                s.setString(1, user.username);
                s.setString(2, hashedPassword);
                s.setBytes(3, imageBytes);
            }

            else {    //The user has not chosen any image

                query = "INSERT INTO user (username, password) VALUES (?,?)";
                s = sqliteConnection.prepareStatement(query);
                s.setString(1, user.username);
                s.setString(2, hashedPassword);
            }

            int res = s.executeUpdate();

            if (res > 0){
                String message = "Successful registration!\nPlease sign in with the username and password you choose.";
                JOptionPane.showMessageDialog(null, message, "Congratulations!", JOptionPane.PLAIN_MESSAGE);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(User user) {

    }

    @Override
    public User getUser(String username, String password) throws UserNotFoundException {
        User user = null;

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement stmt = sqliteConnection.prepareStatement(query);
            stmt.setString(1, username);
            HasherFactory hasherFactory = new HasherFactory();
            String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(password);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                byte[] imageBytes = rs.getBytes("image");
                Image image = null;
                if (imageBytes != null && imageBytes.length > 1) {
                    InputStream imageStream = rs.getBinaryStream("image");
                    image = SwingFXUtils.toFXImage(ImageIO.read(imageStream), null);
                }
                user = new User(username, password, image);
            }
            else {
                String message = "Wrong username or password! If you are a new user, please sign up; otherwise check your credentials.";
                throw new UserNotFoundException(message);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void insertCar(Car car) {

    }

    @Override
    public void deleteCar(Car car) {

    }
}
