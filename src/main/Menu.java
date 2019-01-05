package main;

import main.menuitems.AboutMenuItem;
import main.menuitems.CSVImportMenuItem;
import main.menuitems.DeleteAccountMenuItem;
import main.menuitems.ExitMenuItem;

import javax.swing.*;
import java.awt.*;

public class Menu {

    public JMenu buildHomeMenu(){
        JMenu homeMenu = new JMenu("main.Menu");
        ImageIcon menuImage = new ImageIcon(new ImageIcon("images/menu.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        homeMenu.setIcon(menuImage);

        AboutMenuItem aboutMenuItem = new AboutMenuItem();

        ExitMenuItem exitMenuItem = new ExitMenuItem();

        homeMenu.add(aboutMenuItem.buildAboutMenuItem());
        homeMenu.add(exitMenuItem.buildExitMenuItem());

        return homeMenu;
    }

    public JMenu buildUserMenu(){
        JMenu userMenu = new JMenu(User.getUsername());
        ImageIcon userImage = new ImageIcon(new ImageIcon("images/user.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        userMenu.setIcon(userImage);

        DeleteAccountMenuItem deleteAccountMenuItem = new DeleteAccountMenuItem();
        AboutMenuItem aboutMenuItem = new AboutMenuItem();
        ExitMenuItem exitMenuItem = new ExitMenuItem();

        userMenu.add(deleteAccountMenuItem.buildDeleteAccountMenuItem());
        userMenu.add(aboutMenuItem.buildAboutMenuItem());
        userMenu.add(exitMenuItem.buildExitMenuItem());

        //todo add about, quit, exit, summary
        return userMenu;

    }

    public JMenu buildImportMenu(){
        JMenu importMenu = new JMenu("Import");
        ImageIcon importImage = new ImageIcon(new ImageIcon("images/import.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        importMenu.setIcon(importImage);

        CSVImportMenuItem csvImportMenuItem = new CSVImportMenuItem();

        importMenu.add(csvImportMenuItem.buildCSVImportMenuItem());

        return importMenu;
    }


}
