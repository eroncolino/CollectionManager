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
import main.exceptions.ImageTooLargeException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * Class that will be shown inside the JOptionPane and that allows the user to sign up by choosing a username, a password and an optional profile image.
 * @author Elena Roncolino
 */

public class SignUpPanel extends JPanel{
    private JFXPanel jfxPanel;
    private Circle circle;
    private static JTextField usernameField;
    private static JPasswordField passwordField, confirmField;
    private static File chosenFile;
    private static Image image;

    /**
     * Constructor of the sign up panel.
     */
    public SignUpPanel() {
        initComponents();

        //Choose image button row
        JPanel buttonPanel = new JPanel();
        JButton chooseImageButton = new JButton("Choose your profile image");
        chooseImageButton.addActionListener(new imageChooserListener());
        chooseImageButton.setFont(new Font("Arial", Font.PLAIN, 20));
        buttonPanel.add(chooseImageButton);

        //Username row
        JPanel usernamePanel = new JPanel();

        JLabel username = new JLabel("Choose a username:");
        Properties.setColor(username);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameField = new JTextField();

        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.add(Box.createRigidArea(new Dimension(20,0)));
        usernamePanel.add(username);
        usernamePanel.add(Box.createRigidArea(new Dimension(30, 0)));
        usernamePanel.add(usernameField);
        usernamePanel.add(Box.createRigidArea(new Dimension(20,0)));

        //Password row
        JPanel passwordPanel = new JPanel();

        JLabel password = new JLabel("Choose a password:");
        Properties.setColor(password);
        password.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordField = new JPasswordField();

        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.add(Box.createRigidArea(new Dimension(20,0)));
        passwordPanel.add(password);
        passwordPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createRigidArea(new Dimension(20,0)));

        //Confirm password row
        JPanel confirmPanel = new JPanel();

        JLabel confirm = new JLabel("Confirm password:");
        Properties.setColor(confirm);
        confirm.setFont(new Font("Arial", Font.PLAIN, 20));
        confirmField = new JPasswordField();

        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        confirmPanel.add(Box.createRigidArea(new Dimension(20,0)));
        confirmPanel.add(confirm);
        confirmPanel.add(Box.createRigidArea(new Dimension(45, 0)));
        confirmPanel.add(confirmField);
        confirmPanel.add(Box.createRigidArea(new Dimension(20,0)));

       //Add everything to mail panel and set layout
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(Box.createRigidArea(new Dimension(700, 10)));
        add(buttonPanel);
        add(Box.createRigidArea(new Dimension(700,40)));
        add(usernamePanel);
        add(Box.createRigidArea(new Dimension(700,20)));
        add(passwordPanel);
        add(Box.createRigidArea(new Dimension(700,20)));
        add(confirmPanel);
        add(Box.createRigidArea(new Dimension(700,30)));
    }

    /**
     * Method that initialized the jfxPanel and creates a scene in order to set a profile image inside a circular shape.
     */
    private void initComponents(){
        jfxPanel = new JFXPanel();
        jfxPanel.setPreferredSize(new Dimension(300, 200));

        createScene();

        setLayout(new BorderLayout());
        add(jfxPanel, BorderLayout.CENTER);
    }

    /**
     * Method to create a scene: it draws a circle and fill it with the desired image.
     */
    private void createScene(){
        Platform.setImplicitExit(false); //Prevent thread from dying once jfxPanel is closed
        PlatformImpl.startup(
                new Runnable() {
                    @Override
                    public void run() {
                        Image placeholder;
                        Group root = new Group();
                        Scene scene = new Scene(root, 200, 100);
                        scene.setFill(Color.rgb(238, 238, 238));
                        placeholder = new Image("file:images/man.png");

                        circle = new Circle(80);
                        circle.setTranslateX(60);
                        circle.setTranslateY(60);
                        circle.setCenterX(300);
                        circle.setCenterY(50);
                        circle.setStroke(Color.rgb(14, 35, 46));
                        circle.setStrokeWidth(2.0);
                        circle.setFill(new ImagePattern(placeholder, 0, 0, 1, 1, true));
                        root.getChildren().add(circle);
                        jfxPanel.setScene(scene);
                    }
                });
    }

    /**
     * Private class that implements the listener for the search profile image button.
     */
    private class imageChooserListener implements ActionListener {
        /**
         * Method that overrides the default one and displays the chosen profile image
         *
         * @param e The event that occurs.
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
            fileChooser.setFileFilter(imageFilter);
            int returnValue = fileChooser.showOpenDialog(new JPanel());

            if(returnValue == JFileChooser.APPROVE_OPTION){
                chosenFile = fileChooser.getSelectedFile();

                if (chosenFile.length() < 524288000){
                    image = new Image(chosenFile.toURI().toString());
                    circle.setFill(new ImagePattern(image, 0, 0, 1, 1, true));
                }
                else {
                    throw new ImageTooLargeException("The profile image cannot be larger than 5 MB!");
                }
            }
        }
    }

    /**
     * Getter for the username
     * @return String The entered username
     */
    public static String getUsername(){
        return usernameField.getText();
    }

    /**
     * Getter for the password
     * @return String The entered password
     */
    public static String getPassword(){
        return new String(passwordField.getPassword());
    }

    /**
     * Getter for the confirmed password
     * @return String The entered confirmed password
     */
    public static String getConfirmedPassword(){
        return new String(confirmField.getPassword());
    }

    /**
     * Getter for the inputStream of the chosen image.
     * @return InputStream The inputStream that will be used to store the selected image in the database.
     */
    public static Image getImageFile(){
        return image;
    }
}
