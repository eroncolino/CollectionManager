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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that provides methods to interact with the database.
 *
 * @author Elena Roncolino
 */
public class SQLiteHandler implements DatabaseHandler {
    private Connection sqliteConnection;
    private String databaseName;
    private int numberOfAddedRecords = 0;
    private int numberOfDeletedRecords = 0;

    private static final Logger logger = Logger.getLogger(SQLiteHandler.class.getName());

    /**
     * Creates the database if it has not yet been created.
     * It creates the tables and the <code>admin</code> user so that the application
     * can be tasted.
     *
     * @param databaseName The name of the file from.
     */
    @Override
    public void initialize(String databaseName) {
        logger.entering(getClass().getName(), "initialise");
        this.databaseName = databaseName;

        try {
            logger.log(Level.INFO, "Connecting to {0}", databaseName);
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

            logger.log(Level.FINE, "Tables created");

            //Insert admin user
            File image = new File("images/boss.png");
            FileInputStream imageBytes = new FileInputStream(image);

            HasherFactory hasherFactory = new HasherFactory();

            String query3 = "INSERT INTO users (username, password, image) VALUES (?,?,?)";
            PreparedStatement stmt3 = sqliteConnection.prepareStatement(query3);
            stmt3.setString(1, "admin");
            stmt3.setString(2, hasherFactory.getHasher("MD5").getSecurePassword("admin"));
            stmt3.setBinaryStream(3, imageBytes, (int) (image.length()));

            stmt3.executeUpdate();

            logger.log(Level.FINE, "Admin user created");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problems creating tables");
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, "Problems finding admin image");
        }

        logger.exiting(getClass().getName(), "initialise");
    }

    /**
     * Closes the sqliteConnection to the database.
     */
    @Override
    public void closeConnection() {
        logger.info("Closing database connection");
        if (sqliteConnection != null) {
            try {
                sqliteConnection.close();
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Problems closing the database sqliteConnection", e);
            }
        }
        logger.info("Database closed successfully");
    }

    /**
     * Returns where all the data is stored, i.e., the database name.
     *
     * @return String The name of the database.
     */
    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * Gets the number of added record during the current session.
     *
     * @return int The number of added records.
     */
    @Override
    public int getAddedRecordsNumber() {
        return numberOfAddedRecords;
    }

    /**
     * Gets the number of deleted record during the current session.
     *
     * @return int The number of deleted records.
     */
    @Override
    public int getDeletedRecordsNumber() {
        return numberOfDeletedRecords;
    }

    /**
     * Checks if the chosen username is already stored in the database.
     *
     * @param username The username to be checked.
     * @return boolean Returns <code>true</code> if that username already exists in the database.
     */
    @Override
    public boolean usernameAlreadyExists(String username) {
        logger.entering(getClass().getName(), "usernameAlreadyExists");
        String query = "SELECT * FROM users WHERE username = ?";

        try {
            PreparedStatement stmt = sqliteConnection.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String message = "This username already exists in the database. If you are already registered, " +
                        "please log in. If you are a new user, please choose a different username!";
                JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }

        logger.exiting(getClass().getName(), "usernameAlreadyExists");
        return true;
    }

    /**
     * Adds a new user to the database.
     *
     * @param username The chosen username.
     * @param password The chosen password.
     * @param image    The chosen image.
     */
    @Override
    public void addUser(String username, String password, Image image) {
        logger.entering(getClass().getName(), "addUser");
        PreparedStatement s;
        String query;

        HasherFactory hasherFactory = new HasherFactory();
        String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(password);

        try {
            if (image != null) {    //If the user has chosen an image, store it

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
                if (bufferedImage != null)
                    ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();

                query = "INSERT INTO users (username, password, image) VALUES (?,?,?)";
                s = sqliteConnection.prepareStatement(query);
                s.setString(1, username);
                s.setString(2, hashedPassword);
                s.setBytes(3, imageBytes);
            } else {    //The user has not chosen any image

                query = "INSERT INTO users (username, password) VALUES (?,?)";
                s = sqliteConnection.prepareStatement(query);
                s.setString(1, username);
                s.setString(2, hashedPassword);
            }

            int res = s.executeUpdate();

            if (res > 0) {
                String message = "Successful registration!\nPlease sign in with the username and password you choose.";
                JOptionPane.showMessageDialog(null, message, "Congratulations!", JOptionPane.INFORMATION_MESSAGE);
                logger.log(Level.INFO, "Registration successful");
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Problem reading image", e);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }
        logger.exiting(getClass().getName(), "addUser");
    }

    /**
     * Deletes a user from the database.
     *
     * @param userId The user to be deleted.
     */
    @Override
    public void deleteUser(int userId) {
        logger.entering(getClass().getName(), "deleteUser");
        String query = "DELETE FROM users WHERE id = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);

            s.setInt(1, userId);

            int res = s.executeUpdate();

            if (res > 0) {
                Main.showLoginPanel();
                JOptionPane.showMessageDialog(null, "Account deleted successfully!", "User deleted!", JOptionPane.PLAIN_MESSAGE);
                logger.log(Level.INFO, "Registration successful");
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }

        logger.exiting(getClass().getName(), "deleteUser");
    }

    /**
     * Checks if the credentials are stored in the database and returns the current main.User Object.
     *
     * @param username The username.
     * @param password The password.
     * @return main.User The user corresponding to the username and password entered.
     */
    @Override
    public User getUser(String username, String password) throws UserNotFoundException {
        logger.entering(getClass().getName(), "getUser");
        User user = null;

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        try {
            PreparedStatement stmt = sqliteConnection.prepareStatement(query);
            stmt.setString(1, username);
            HasherFactory hasherFactory = new HasherFactory();
            String hashedPassword = hasherFactory.getHasher("MD5").getSecurePassword(password);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("id");
                byte[] imageBytes = rs.getBytes("image");
                Image image = null;
                if (imageBytes != null && imageBytes.length > 1) {
                    InputStream imageStream = rs.getBinaryStream("image");
                    image = SwingFXUtils.toFXImage(ImageIO.read(imageStream), null);
                }
                user = new User(userId, username, password, image);
            } else {
                String message = "Wrong username or password! If you are a new user, please sign up; otherwise check your credentials.";
                throw new UserNotFoundException(message);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Problem reading the image", e);
        }
        logger.exiting(getClass().getName(), "getUser");
        return user;
    }

    @Override
    public int getTotalNumberOfRecordsByUserId(int userId) throws SQLException {
        logger.entering(getClass().getName(), "getTotalNumberOfRecordsByUserId");
        int total = 0;

        String query = "SELECT COUNT(*) FROM cars WHERE userid = " + userId;
        Statement s = sqliteConnection.createStatement();

        ResultSet rs = s.executeQuery(query);
        rs.next();

        total = rs.getInt(1);

        logger.exiting(getClass().getName(), "getTotalNumberOfRecordsByUserId");
        return total;
    }

    /**
     * Returns a list of cars given user id as input.
     *
     * @param userId The user id.
     * @return List A list of cars.
     */
    @Override
    public List<Car> getCarsByUserId(int userId) {
        logger.entering(getClass().getName(), "getCarsByUserId");
        ArrayList<Car> carsList = new ArrayList();

        String query = "SELECT * FROM cars WHERE userid = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Car car = new Car(rs.getString("name"), rs.getString("brand"),
                        rs.getInt("cubiccapacity"), rs.getInt("ps"), rs.getInt("kw"),
                        rs.getInt("cylinders"), rs.getString("fueltype"), userId);

                car.setCarId(rs.getInt("id"));
                carsList.add(car);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }

        logger.exiting(getClass().getName(), "getCarsByUserId");
        return carsList;
    }

    /**
     * Allows to retrieve all the cars that a user has stored in his collection manager.
     *
     * @param userId The id of the user.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     * @throws SQLException If there is a problem in the JDBC.
     */
    @Override
    public Object[][] getCarsMatrixByUserId(int userId) {
        logger.entering(getClass().getName(), "getCarsMatrixByUserId");
        List<Car> carsList = getCarsByUserId(userId);

        Object[][] carsArray = new Object[carsList.size()][8];

        for (int i = 0; i < carsList.size(); i++) {
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
        logger.exiting(getClass().getName(), "getCarsMatrixByUserId");
        return carsArray;
    }

    /**
     * Retrieves all the cars with the given string value in the specified column.
     *
     * @param userId The user id.
     * @param column The column name where the string has to be matched.
     * @param string The string to be matched.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     * @throws SQLException If there is a problem in the JDBC.
     */
    @Override
    public Object[][] getCarsByString(int userId, String column, String string) throws SQLException {
        logger.entering(getClass().getName(), "getCarsByString");
        ArrayList<Car> carsList = new ArrayList();

        String query = "SELECT * FROM cars WHERE userId = ? AND UPPER(" + column + ") = UPPER(?)";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);
            s.setString(2, string);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Car car = new Car(rs.getString("name"), rs.getString("brand"),
                        rs.getInt("cubiccapacity"), rs.getInt("ps"), rs.getInt("kw"),
                        rs.getInt("cylinders"), rs.getString("fueltype"), userId);

                car.setCarId(rs.getInt("id"));
                carsList.add(car);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }

        Object[][] carsArray = new Object[carsList.size()][8];

        for (int i = 0; i < carsList.size(); i++) {
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
        logger.exiting(getClass().getName(), "getCarsByString");
        return carsArray;

    }

    /**
     * Retrieves all the cars with the given number in the specified column.
     *
     * @param userId The user id.
     * @param column The column name where the number has to be matched.
     * @param number The number to be matched.
     * @return Object[][] A matrix that contains all the cars data to be displayed in the table.
     * @throws SQLException If there is a problem in the JDBC.
     */
    @Override
    public Object[][] getCarsByInt(int userId, String column, int number) throws SQLException {
        logger.entering(getClass().getName(), "getCarsByInt");
        ArrayList<Car> carsList = new ArrayList();

        String query = "SELECT * FROM cars WHERE userId = ? AND " + column + " = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);
            s.setInt(2, number);
            ResultSet rs = s.executeQuery();

            while (rs.next()) {
                Car car = new Car(rs.getString("name"), rs.getString("brand"),
                        rs.getInt("cubiccapacity"), rs.getInt("ps"), rs.getInt("kw"),
                        rs.getInt("cylinders"), rs.getString("fueltype"), userId);

                car.setCarId(rs.getInt("id"));
                carsList.add(car);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }

        Object[][] carsArray = new Object[carsList.size()][8];

        for (int i = 0; i < carsList.size(); i++) {
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
        logger.exiting(getClass().getName(), "getCarsByInt");
        return carsArray;

    }

    /**
     * Inserts a car record into the database.
     *
     * @param car The car record that has to be inserted.
     */
    @Override
    public void insertCar(Car car) {
        logger.entering(getClass().getName(), "insertCar");
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

            if (result > 0) {
                JOptionPane.showMessageDialog(null, "Car added successfully!", "Insertion complete", JOptionPane.INFORMATION_MESSAGE);
                logger.log(Level.INFO, "Car insertion successful");
                numberOfAddedRecords++;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }
        logger.exiting(getClass().getName(), "insertCar");
    }

    /**
     * Inserts a car list into the database.
     *
     * @param cars The list of cars to be inserted.
     */
    @Override
    public void insertCarList(List<Car> cars) {
        logger.entering(getClass().getName(), "insertCarList");
        String query = "INSERT INTO cars VALUES(null,?,?,?,?,?,?,?,?)";
        int res = 0;

        for (Car car : cars) {
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

                res = s.executeUpdate();

            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Problem with the database", e);
            }
        }

        if (res > 0) {
            //Refresh table
            CarPanel.repaintTable(getCarsMatrixByUserId(User.getUserId()));
            JOptionPane.showMessageDialog(null, cars.size() + " cars added successfully!", "Insertion complete", JOptionPane.INFORMATION_MESSAGE);
            logger.log(Level.INFO, "Car list insertion successful");
            numberOfAddedRecords += cars.size();
        }
        logger.exiting(getClass().getName(), "insertCarList");
    }

    /**
     * Allows to update a car.
     *
     * @param carId         The id of the car to be updated.
     * @param name          The new car name.
     * @param brand         The new car brand.
     * @param cubicCapacity The new cubic capacity.
     * @param ps            The new PS value.
     * @param kw            The new KW value.
     * @param cylinders     The new cylinders value.
     * @param fuel          The new fuel type.
     */
    @Override
    public void updateCar(int carId, String name, String brand, int cubicCapacity, int ps, int kw, int cylinders, String fuel) {
        logger.entering(getClass().getName(), "updateCar");
        String query = "UPDATE cars SET name = ?, brand = ?, cubiccapacity = ?, ps = ?, kw = ?, cylinders = ?, fueltype = ? " +
                "WHERE id = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setString(1, name);
            s.setString(2, brand);
            s.setInt(3, cubicCapacity);
            s.setInt(4, ps);
            s.setInt(5, kw);
            s.setInt(6, cylinders);
            s.setString(7, fuel);
            s.setInt(8, carId);

            int res = s.executeUpdate();

            if (res > 0) {
                //Refresh table
                CarPanel.repaintTable(getCarsMatrixByUserId(User.getUserId()));
                JOptionPane.showMessageDialog(null, "Car record updated successfully!", "Update complete", JOptionPane.INFORMATION_MESSAGE);
                logger.log(Level.INFO, "Car update successful");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }

        logger.exiting(getClass().getName(), "updateCar");
    }

    /**
     * Deletes a car record from the database.
     *
     * @param carId The id of the car to be deleted.
     */
    @Override
    public void deleteCar(int carId) {
        logger.entering(getClass().getName(), "deleteCar");
        String query = "DELETE FROM cars WHERE id = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, carId);

            int res = s.executeUpdate();

            if (res > 0) {
                //Refresh table
                CarPanel.repaintTable(getCarsMatrixByUserId(User.getUserId()));
                JOptionPane.showMessageDialog(null, "Car record deleted successfully!", "Deletion complete", JOptionPane.INFORMATION_MESSAGE);
                logger.log(Level.INFO, "Car deletion successful");
                numberOfDeletedRecords++;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }
        logger.exiting(getClass().getName(), "deleteCar");
    }

    /**
     * Deletes all the car records of a given user.
     *
     * @param userId The id of the user whose cars have to be deleted.
     */
    @Override
    public void deleteCarsFromUserId(int userId) {
        logger.entering(getClass().getName(), "deleteCarsFromUserId");
        String query = "DELETE FROM cars WHERE userid = ?";

        try {
            PreparedStatement s = sqliteConnection.prepareStatement(query);
            s.setInt(1, userId);

            s.executeUpdate();
            logger.log(Level.INFO, "Deleting all user's cars");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }
        logger.exiting(getClass().getName(), "deleteCarsFromUserId");
    }
}
