package main;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

/**
 * main.Main class that allows the execution of the application.
 * @author Elena Roncolino
 */
public class Main {

    /**
     * main.Main method that creates the frame of the application,
     * the menu bar and the background panel.
     * @param args parameters.
     */
    public static void main(String[] args) {

        JFrame frame;
        JMenu menu;
        JMenuItem about, exit;
        ImageIcon menuImage, aboutImage, exitImage;

        //Create the frame
        frame = new JFrame("AP Collection Manager 2017");

        try {
            DatabaseConnection.getInstance().initialize("CarDB.db");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Add the menu bar to the frame and set defaults
        MenuBarBuilder menuBarBuilder = new MenuBarBuilder();
        JMenuBar menuBar = menuBarBuilder.buildPlainMenuBar();
        frame.setJMenuBar(menuBar);

        frame.add(new BackgroundPanel());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1300, 1000));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
