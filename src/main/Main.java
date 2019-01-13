package main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

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

    /**
     * main.Main method that creates the frame of the application,
     * the menu bar and the background panel.
     *
     * @param args parameters.
     */
    public static void main(String[] args) {

        //Create the frame
        frame = new JFrame("AP Collection Manager 2017");

        try {
            DatabaseConnection.getInstance().initialize("CarDB.db");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        backgroundPanel = new BackgroundPanel();
        frame.add(backgroundPanel);
        showLoginPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1300, 1000));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Method that shows the login panel once the application is started or after a logout.
     */
    public static void showLoginPanel() {
        loginPanel = new LoginPanel();
        loginPanel.setOpaque(false);
        backgroundPanel.add(loginPanel);

        //Add the menu bar to the frame and set defaults
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
        JMenuBar menuBar = menuBarBuilder.buildPlainMenuBar();
        frame.setJMenuBar(menuBar);
    }

    /**
     * Method that shows the panel where the cars will be displayed after the user has logged in.
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
        //todo rechange menu bar once user logsout
    }
}
