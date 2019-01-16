package main;

import javax.swing.*;

/**
 * Class that builds menu bars.
 *
 * @author Elena Roncolino
 */
public class MenuBarBuilder {

    /**
     * Builds a plain menu bar with the main functionalities that can be accessed
     * when the user has not logged in yet.
     *
     * @return JMenuBar The plain menu bar.
     */
    public JMenuBar buildPlainMenuBar() {
        JMenuBar plainMenuBar = new JMenuBar();

        Menu menu = new Menu();
        JMenu homeMenu = menu.buildHomeMenu();

        plainMenuBar.add(homeMenu);
        return plainMenuBar;
    }

    /**
     * Builds a extended menu bar with the functionalities that can be accessed.
     * when the user has logged in.
     *
     * @return JMenuBar The extended menu bar.
     */
    public JMenuBar buildExtendedMenuBar() {
        JMenuBar extendedMenuBar = new JMenuBar();

        Menu menu = new Menu();
        JMenu extendedMenu = menu.buildUserMenu();

        extendedMenuBar.add(extendedMenu);
        return extendedMenuBar;
    }
}
