package main.menuitems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the delete account menu item.
 * @author Elena Roncolino
 */
public class DeleteAccountMenuItem {

    /**
     * Method that builds the delete account menu item.
     * @return JMenuItem The delete account menu item.
     */
    public JMenuItem buildDeleteAccountMenuItem() {
        ImageIcon deleteImage = new ImageIcon(new ImageIcon("images/rubbish-bin.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem deleteMenu = new JMenuItem("Delete account", deleteImage);
        deleteMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo delete user method from sqlite handler
            }
        });
        return deleteMenu;
    }
}
