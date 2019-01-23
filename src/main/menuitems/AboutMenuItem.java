package main.menuitems;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the about menu item.
 *
 * @author Elena Roncolino
 */
public class AboutMenuItem extends JMenuItem {

    /**
     * Builds the about menu item.
     *
     * @return JMenuItem The about menu item.
     */
    public JMenuItem buildAboutMenuItem() {
        ImageIcon aboutImage = new ImageIcon(new ImageIcon("images/about.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem aboutMenu = new JMenuItem("About", aboutImage);

        String aboutMessage = "This application has been developed by a second year Computer Science and Engineering student\n" +
                " of the Free University of Bolzano as a project for the Advanced Programming course.\nThe icons are takes from the website: https://www.flaticon.com/.";
        aboutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, aboutMessage);
            }
        });

        return aboutMenu;
    }

}
