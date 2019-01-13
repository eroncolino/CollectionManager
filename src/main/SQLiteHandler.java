package main;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import main.exceptions.UserNotFoundException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that provides methods to interact with the database.
 * @author Elena Roncolino
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
                    "brand VARCHAR(50) NOT NULL,\n" +
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

    /**
     * Method that returns where all the data is stored, i.e., the database name.
     * @return String The name of the database.
     */
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
     * @param username The chosen username.
     * @param password The chosen password.
     * @param image The chosen image.
     */
    @Override
    public void addUser(String username, String password, Image image) {
        PreparedStatement s;
        String query;

        HasherFactory hasherFactory = new HasherFactory();
        String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(password);

        try {
            if (image != null) {    //If the user has chosen an image, store it

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                if(bufferedImage != null)
                    ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                query = "INSERT INTO users (username, password, image) VALUES (?,?,?)";
                s = sqliteConnection.prepareStatement(query);
                s.setString(1, username);
                s.setString(2, hashedPassword);
                s.setBytes(3, imageBytes);
            }

            else {    //The user has not chosen any image

                query = "INSERT INTO users (username, password) VALUES (?,?)";
                s = sqliteConnection.prepareStatement(query);
                s.setString(1, username);
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

    /**
     * Method that checks if the credentials are stored in the database and returns the current main.User Object.
     * @param username The username.
     * @param password The password.
     * @return main.User The user corresponding to the username and password entered.
     */
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
                int userId = rs.getInt("id");
                byte[] imageBytes = rs.getBytes("image");
                Image image = null;
                if (imageBytes != null && imageBytes.length > 1) {
                    InputStream imageStream = rs.getBinaryStream("image");
                    image = SwingFXUtils.toFXImage(ImageIO.read(imageStream), null);
                }
                user = new User(userId, username, password, image);
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

    /**
     * Method that allows to retrieve all the cars that a user has stored in his collection manager.
     * @param userId The id of the user.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     * @throws SQLException If there is a problem in the JDBC.
     */
    @Override
    public Object[][] getCarsByUserId(int userId) {
        ArrayList<Car> carsList = new ArrayList();

        String query = "SELECT * FROM cars WHERE userid = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);
            ResultSet rs = s.executeQuery();

            while(rs.next()){
                Car car = new Car(rs.getString("name"), rs.getString("brand"),
                        rs.getInt("cubiccapacity"), rs.getInt("ps"), rs.getInt("kw"),
                        rs.getInt("cylinders"), rs.getString("fueltype"), userId);

                car.setCarId(rs.getInt("id"));
                carsList.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] carsArray = new Object[carsList.size()][8];

        for(int i = 0; i < carsList.size(); i++){
            Car car = carsList.get(i);
            carsArray[i][0] = car.getCarId();
            carsArray[i][1] = car.getCarName();
            carsArray[i][2] = car.getCarBrand();
            carsArray[i][3] = car.getCubicCapacity();
            carsArray[i][4] = car.getPs();
            carsArray[i][5] = car.getKw();
            carsArray[i][6] = car.getCylinders();
            carsArray[i][7] = car.getFuelType();
        }
        return carsArray;
    }

    /**
     * Method that allows to retrieve all the cars with the given string value in the specified column.
     * @param userId The user id.
     * @param column The column name where the string has to be matched.
     * @param string The string to be matched.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     * @throws SQLException If there is a problem in the JDBC.
     */
    @Override
    public Object[][] getCarsByString(int userId, String column, String string) throws SQLException {
        ArrayList<Car> carsList = new ArrayList();

        String query = "SELECT * FROM cars WHERE userId = ? AND UPPER(" + column + ") = UPPER(?)";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);
            s.setString(2, string);
            ResultSet rs = s.executeQuery();

            while(rs.next()){
                Car car = new Car(rs.getString("name"), rs.getString("brand"),
                        rs.getInt("cubiccapacity"), rs.getInt("ps"), rs.getInt("kw"),
                        rs.getInt("cylinders"), rs.getString("fueltype"), userId);

                car.setCarId(rs.getInt("id"));
                carsList.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] carsArray = new Object[carsList.size()][8];

        for(int i = 0; i < carsList.size(); i++){
            Car car = carsList.get(i);
            carsArray[i][0] = car.getCarId();
            carsArray[i][1] = car.getCarName();
            carsArray[i][2] = car.getCarBrand();
            carsArray[i][3] = car.getCubicCapacity();
            carsArray[i][4] = car.getPs();
            carsArray[i][5] = car.getKw();
            carsArray[i][6] = car.getCylinders();
            carsArray[i][7] = car.getFuelType();
        }
        return carsArray;
    }

    /**
     * Method that allows to retrieve all the cars with the given number in the specified column.
     * @param userId The user id.
     * @param column The column name where the number has to be matched.
     * @param number The number to be matched.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     * @throws SQLException If there is a problem in the JDBC.
     */
    @Override
    public Object[][] getCarsByInt(int userId, String column, int number) throws SQLException {
        ArrayList<Car> carsList = new ArrayList();

        String query = "SELECT * FROM cars WHERE userId = ? AND " + column + " = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);
            s.setInt(2, number);
            ResultSet rs = s.executeQuery();

            while(rs.next()){
                Car car = new Car(rs.getString("name"), rs.getString("brand"),
                        rs.getInt("cubiccapacity"), rs.getInt("ps"), rs.getInt("kw"),
                        rs.getInt("cylinders"), rs.getString("fueltype"), userId);

                car.setCarId(rs.getInt("id"));
                carsList.add(car);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Object[][] carsArray = new Object[carsList.size()][8];

        for(int i = 0; i < carsList.size(); i++){
            Car car = carsList.get(i);
            carsArray[i][0] = car.getCarId();
            carsArray[i][1] = car.getCarName();
            carsArray[i][2] = car.getCarBrand();
            carsArray[i][3] = car.getCubicCapacity();
            carsArray[i][4] = car.getPs();
            carsArray[i][5] = car.getKw();
            carsArray[i][6] = car.getCylinders();
            carsArray[i][7] = car.getFuelType();
        }
        return carsArray;
    }

    /**
     * Method that inserts a car record into the database.
     * @param car The car record that has to be inserted.
     */
    @Override
    public void insertCar(Car car) {
        String query = "INSERT INTO cars VALUES(null,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setString(1, car.getCarName());
            s.setString(2, car.getCarBrand());
            s.setInt(3, car.getCubicCapacity());
            s.setInt(4, car.getPs());
            s.setInt(5, car.getKw());
            s.setInt(6, car.getCylinders());
            s.setString(7, car.getFuelType());
            s.setInt(8, car.getCarOwnerId());

            int result = s.executeUpdate();

            if (result > 0){
                JOptionPane.showMessageDialog(null, "Car added successfully!", "Insertion complete", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }

    /**
     * Method that inserts a car list into the database.
     * @param cars The list of cars to be inserted.
     */
    @Override
    public void insertCarList(List<Car> cars) {
        String query = "INSERT INTO cars VALUES(null,?,?,?,?,?,?,?,?)";

        for(Car car : cars) {
            try {
                PreparedStatement s = sqliteConnection.prepareStatement(query);
                s.setString(1, car.getCarName());
                s.setString(2, car.getCarBrand());
                s.setInt(3, car.getCubicCapacity());
                s.setInt(4, car.getPs());
                s.setInt(5, car.getKw());
                s.setInt(6, car.getCylinders());
                s.setString(7, car.getFuelType());
                s.setInt(8, car.getCarOwnerId());

                s.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //Refresh table
        CarPanel.repaintTable(getCarsByUserId(User.getUserId()));
        JOptionPane.showMessageDialog(null, cars.size() + " cars added successfully!", "Insertion complete", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    public void updateCar(int carId){
        //todo
    }

    @Override
    public void deleteCar(Car car) {
        //todo delete car method
    }
}
