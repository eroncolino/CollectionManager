package main.menuitems;
import main.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the exit menu item.
 * @author Elena Roncolino
 */
public class ExitMenuItem extends JMenuItem {

    /**
     * Method that builds the exit menu item.
     * @return JMenuItem The exit menu item.
     */
    public JMenuItem buildExitMenuItem() {
        ImageIcon exitImage = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem exitMenu = new JMenuItem("Exit", exitImage);
        exitMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection.getInstance().closeConnection();
                System.exit(0);
            }
        });

        return exitMenu;
    }
}