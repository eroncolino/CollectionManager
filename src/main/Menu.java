package main;

import main.menuitems.*;

import javax.swing.*;
import java.awt.*;

/**
 * Class that builds menu according to the functionalities that have to be displayed:
 * a menu for when the user has not logged in yet and a menu that provides extended functionalities.
 * @author Elena Roncolino
 */
public class Menu {

    /**
     * Builds a simple menu and adds to this menu the required menu items.
     * @return JMenu The menu with an about and an exit menu items.
     */
    public JMenu buildHomeMenu(){
        JMenu homeMenu = new JMenu("Menu");
        ImageIcon menuImage = new ImageIcon(new ImageIcon("images/menu.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        homeMenu.setIcon(menuImage);

        AboutMenuItem aboutMenuItem = new AboutMenuItem();

        ExitMenuItem exitMenuItem = new ExitMenuItem();

        homeMenu.add(aboutMenuItem.buildAboutMenuItem());
        homeMenu.add(exitMenuItem.buildExitMenuItem());

        return homeMenu;
    }

    /**
     * Builds an extended menu and adds to this menu the required menu items.
     * @return JMenu The menu with several menu items.
     */
    public JMenu buildUserMenu(){
        JMenu userMenu = new JMenu(User.getUsername());
        ImageIcon userImage = new ImageIcon(new ImageIcon("images/user.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        userMenu.setIcon(userImage);

        CSVImportMenuItem importMenuItem = new CSVImportMenuItem();
        CSVExportMenuItem exportMenuItem = new CSVExportMenuItem();
        DeleteAccountMenuItem deleteAccountMenuItem = new DeleteAccountMenuItem();
        LogOutMenuItem logOutMenuItem = new LogOutMenuItem();
        StatisticsMenuItem statisticsMenuItem = new StatisticsMenuItem();
        AboutMenuItem aboutMenuItem = new AboutMenuItem();
        ExitMenuItem exitMenuItem = new ExitMenuItem();

        userMenu.add(importMenuItem.buildCSVImportMenuItem());
        userMenu.add(exportMenuItem.csvExportMenuItem());
        userMenu.add(deleteAccountMenuItem.buildDeleteAccountMenuItem());
        userMenu.add(logOutMenuItem.buildLogOutMenuItem());
        userMenu.add(statisticsMenuItem.buildStatisticsMenuItem());
        userMenu.add(aboutMenuItem.buildAboutMenuItem());
        userMenu.add(exitMenuItem.buildExitMenuItem());

        return userMenu;

    }
}
