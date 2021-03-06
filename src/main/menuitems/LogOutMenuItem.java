package main.menuitems;

import main.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that creates the log-out menu item.
 *
 * @author Elena Roncolino
 */
public class LogOutMenuItem extends JMenuItem {
    private static final Logger logger = Logger.getLogger(LogOutMenuItem.class.getName());

    /**
     * Builds the log-out menu item.
     *
     * @return JMenuItem The log-out menu item.
     */
    public JMenuItem buildLogOutMenuItem() {
        ImageIcon logOutImage = new ImageIcon(new ImageIcon("images/log-out.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem logOutMenu = new JMenuItem("Log out", logOutImage);
        logOutMenu.addActionListener(new ActionListener() {
            /**
             * Overrides the default one and asks the user if he is sure he wants to log out.
             * If yes, the logout is done and login panel is shown again.
             * @param e The event generated by pressing the logout menu item.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?",
                        "Log out", JOptionPane.YES_NO_CANCEL_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    logger.log(Level.FINE, "Logging out");
                    Main.showLoginPanel();
                }
            }
        });
        return logOutMenu;
    }
}
