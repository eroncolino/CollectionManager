package main;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that checks if the username and the two password entered meet the requirements.
 * @author Elena Roncolino
 */
public class CredentialsValidator {
    static JPanel container;
    static JFXPanel jfxPanel;
    static User user;

    private static final Logger logger = Logger.getLogger(CredentialsValidator.class.getName());

    /**
     * Checks if the username has less than 5 characters and if it contains more than one dash, underscore or period.
     * @param username The username entered.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>, whiche means that all entered data is correct.
     */
    public static boolean checkDataRequirements(String username, String password, String confirm) {
        logger.log(Level.INFO, "Checking username correctness");
        //Check username length
        if (username.length() < 5 || username.length() > 30) {
            JOptionPane.showMessageDialog(null, "The username must be between 5 and 30 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        else {
            //Check if username contains more than one dot, one dash or one underscore
            Pattern p = Pattern.compile("[-._][^-._]*[-._]+");
            Matcher m = p.matcher(username);

            if (m.find()) {
                JOptionPane.showMessageDialog(null, "The username can only contain one dash, one underscore or one dot!", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Username does not meet requirements");
                return false;
            }
            else {
                return checkAlreadyUsedUsername(username, password, confirm);
            }
        }
    }

    /**
     * Checks if the username entered already exists in the database.
     * @param username The username entered.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>.
     */
    public static boolean checkAlreadyUsedUsername (String username, String password, String confirm){
        logger.log(Level.INFO, "Checking if username is already taken");
        try {
            if (DatabaseConnection.getInstance().usernameAlreadyExists(username))
                return checkPasswordRequirements(password, confirm);

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problem with the database", e);
        }
        return false;
    }

    /**
     * Checks if the password has more than 5 characters and, if it does, it calls the checkMatchingPassword() method.
     * @param password The password entered.
     * @param confirm The confirm password entered.
     * @return <code>true</code> if the method that it calls returns <code>true</code>.
     */
    public static boolean checkPasswordRequirements(String password, String confirm) {
        logger.log(Level.INFO, "Checking password requirements");
        if (password.length() < 5) {
            JOptionPane.showMessageDialog(null, "The password must be at least 5 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, "Password does not meet the requirements");
            return false;
        }
        else
            return checkMatchingPasswords(password, confirm);
    }

    /**
     * Checks if the two password entered match.
     * @param password The password entered.
     * @param confirmedPassword The second confirm password entered.
     * @return <code>true</code> if the two password coincide.
     */
    public static boolean checkMatchingPasswords(String password, String confirmedPassword) {
        if (!password.equals(confirmedPassword)) {
            JOptionPane.showMessageDialog(null, "The two passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, "Passwords do not match");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Checks if the entered credentials are correct and retrieves the right user.
     * @param username The entered username.
     * @param password The entered password.
     */
    public static void checkCredentials(String username, String password) {
        logger.log(Level.INFO, "Checking credentials");
        if (username.length() < 5 || username.length() > 30) {
            JOptionPane.showMessageDialog(null, "The username must be between 5 and 30 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.log(Level.WARNING, "Username does not meet the requirements");
        }
        else {
            if (password.length() < 5){
                JOptionPane.showMessageDialog(null, "The password must be at least 5 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Password does not meet the requirements");
            }
            else {
                try {
                    user = DatabaseConnection.getInstance().getUser(username, password);
                    container = new JPanel();
                    container.setPreferredSize(new Dimension(450, 250));
                    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
                    initComponents();
                    JPanel labelPanel = new JPanel();
                    JLabel message = new JLabel("Welcome back " + username + "! Your data will be loaded.");
                    message.setForeground(new java.awt.Color(14, 35, 46));
                    message.setFont(new Font("Arial", Font.PLAIN, 18));
                    labelPanel.add(message);
                    message.setHorizontalAlignment(SwingUtilities.CENTER);
                    container.add(labelPanel);
                    container.add(Box.createRigidArea(new Dimension(40,0)));
                    JOptionPane.showMessageDialog(null, container, "Successful login", JOptionPane.PLAIN_MESSAGE);
                    Main.showCarPanel();
                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "Problem with the database", e);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Problem retrieving user", e);
                }
            }
        }
    }

    /**
     * Initializes the jFXPanel and creates a scene in order to set a profile image inside a circular shape.
     */
    private static void initComponents(){
        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(100, 130));

        createScene();
        jfxPanel.setLayout(new BorderLayout());
        container.add(jfxPanel, BorderLayout.CENTER);
    }

    /**
     * Creates a scene, draws a circle and fills it with the desired image.
     */
    private static void createScene(){
        Platform.setImplicitExit(false); //Prevent thread from dying once jfxPanel is closed
        PlatformImpl.startup(
                new Runnable() {
                    /**
                     * Overrides the default one and paints the scene with the right image inside.
                     */
                    @Override
                    public void run() {
                        javafx.scene.image.Image placeholder;
                        Group root = new Group();
                        Scene scene = new Scene(root, 100, 80);
                        scene.setFill(javafx.scene.paint.Color.rgb(238, 238, 238));

                        if (User.getImage() != null){
                            placeholder = User.getImage();
                        } else
                            placeholder = new Image("file:images/man.png");

                        Circle circle = new Circle(80);
                        circle.setTranslateX(60);
                        circle.setTranslateY(60);
                        circle.setCenterX(160);
                        circle.setCenterY(30);
                        circle.setStroke(Color.rgb(14, 35, 46));
                        circle.setStrokeWidth(2.0);
                        circle.setFill(new ImagePattern(placeholder, 0, 0, 1, 1, true));
                        root.getChildren().add(circle);
                        jfxPanel.setScene(scene);
                    }
                });
    }
}
