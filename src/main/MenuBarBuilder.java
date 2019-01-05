package main;

import javax.swing.*;

/**
 * Class that builds menu bar
 * @author Elena Roncolino
 */
public class MenuBarBuilder {

    public JMenuBar buildPlainMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        Menu menu = new Menu();
        JMenu homeMenu = menu.buildHomeMenu();

        menuBar.add(homeMenu);
        return menuBar;
    }
}
