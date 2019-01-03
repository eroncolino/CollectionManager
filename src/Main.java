import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Elena Roncolino
 *
 * Main class that allows the execution of the application.
 */

public class Main {

    /**
     * Main method that creates the frame of the application,
     * the menu bar and the background panel.
     *
     * @param args parameters
     */

    public static void main(String[] args) {

        JFrame frame;
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem about, exit;
        ImageIcon menuImage, aboutImage, exitImage;

        //Create the frame
        frame = new JFrame("AP Collection Manager 2017");

        // Create the menuBar
        menuBar = new JMenuBar();

        //Create the menu
        menu = new JMenu("Menu");
        menuImage = new ImageIcon(new ImageIcon("images/menu.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menu.setIcon(menuImage);

        // Create the about subMenu and the exit subMenu
        aboutImage = new ImageIcon(
                new ImageIcon("images/about.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        about = new JMenuItem("About", aboutImage);
        String aboutMessage = "This application has been developed by a second year Computer Science and Engineering student\n" +
                " of the Free University of Bolzano as a project for the Advanced Programming course.";
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, aboutMessage);
            }
        });

        exitImage = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        exit = new JMenuItem("Exit", exitImage);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection.closeConnection();
                System.exit(0);
            }
        });

        menu.add(about);
        menu.add(exit);

        menuBar.add(menu);

        //Add the menu bar to the frame and set defaults
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
