package main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Main class that allows the execution of the application.
 *
 * @author Elena Roncolino
 */
public class Main {
    static JFrame frame;
    private static BackgroundPanel backgroundPanel;
    private static LoginPanel loginPanel;
    private static CarPanel carPanel;

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * Main method that creates the frame of the application,
     * the menu bar and the background panel.
     *
     * @param args parameters.
     */
    public static void main(String[] args) {
        logger.setLevel(Level.ALL);

        try {
            FileHandler handler = new FileHandler("CollectionManager-Log.%u.%g.txt", 1024 * 1024 * 8, 10, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Problems opening the log file", e);
        }


        //Create the frame
        frame = new JFrame("AP Collection Manager 2017");

        try {
            logger.log(Level.INFO, "Logging started, connecting to the database");
            DatabaseConnection.getInstance().initialize("CarDB.db");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Problems connecting to the database", e);
        }

        backgroundPanel = new BackgroundPanel();
        frame.add(backgroundPanel);
        showLoginPanel();
        logger.log(Level.INFO, "Application started successfully");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1300, 1000));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Shows the login panel once the application is started or after a logout.
     */
    public static void showLoginPanel() {
        loginPanel = new LoginPanel();
        if (carPanel != null)
            carPanel.setVisible(false);
        loginPanel.setOpaque(false);
        backgroundPanel.add(loginPanel);

        //Add the menu bar to the frame and set defaults
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
        JMenuBar menuBar = menuBarBuilder.buildPlainMenuBar();
        frame.setJMenuBar(menuBar);

        logger.log(Level.FINE, "Login panel displayed and menu bar created");
    }

    /**
     * Shows the panel where the cars will be displayed after the user has logged in.
     */
    public static void showCarPanel() {
        loginPanel.setVisible(false);
        carPanel = new CarPanel();
        carPanel.setOpaque(false);
        carPanel.setVisible(true);
        backgroundPanel.add(carPanel);
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
        JMenuBar extendedMenuBar = menuBarBuilder.buildExtendedMenuBar();
        Main.frame.setJMenuBar(extendedMenuBar);
        logger.log(Level.INFO, "Car panel displayed and user menu bar created");
    }
}
