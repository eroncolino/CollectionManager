package main.menuitems;

import main.DatabaseConnection;
import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Class that creates the delete account menu item.
 *
 * @author Elena Roncolino
 */
public class DeleteAccountMenuItem {
    private static final Logger logger = Logger.getLogger(DeleteAccountMenuItem.class.getName());

    /**
     * Builds the delete account menu item.
     *
     * @return JMenuItem The delete account menu item.
     */
    public JMenuItem buildDeleteAccountMenuItem() {
        ImageIcon deleteImage = new ImageIcon(new ImageIcon("images/rubbish-bin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem deleteMenu = new JMenuItem("Delete account", deleteImage);
        deleteMenu.addActionListener(new ActionListener() {
            /**
             * Overrides the default one and deletes a user account.
             * @param e The event of pressing the button.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "ATTENTION: if you delete your " +
                                "account all your data will be lost \nand you won't be able to retrieve them! Are you sure you want to proceed?",
                        "Confirm delete account", JOptionPane.YES_NO_CANCEL_OPTION);

                if (result == JOptionPane.YES_OPTION) {

                    DatabaseConnection.getInstance().deleteCarsFromUserId(User.getUserId());
                    DatabaseConnection.getInstance().deleteUser(User.getUserId());

                }
            }
        });
        return deleteMenu;
    }
}
