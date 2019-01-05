import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that builds menu bar
 * @author Elena Roncolino
 */
public class MenuBarBuilder {

    public JMenuBar buildMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenu menu = new JMenu("Menu");
        ImageIcon menuImage = new ImageIcon(new ImageIcon("images/menu.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        menu.setIcon(menuImage);

        // Create the about subMenu and the exit subMenu
        ImageIcon aboutImage = new ImageIcon(
                new ImageIcon("images/about.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem about = new JMenuItem("About", aboutImage);
        String aboutMessage = "This application has been developed by a second year Computer Science and Engineering student\n" +
                " of the Free University of Bolzano as a project for the Advanced Programming course.";
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, aboutMessage);
            }
        });

        ImageIcon exitImage = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem exit = new JMenuItem("Exit", exitImage);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection.getInstance().closeConnection();
                System.exit(0);
            }
        });

        menu.add(about);
        menu.add(exit);
        menuBar.add(menu);
        return menuBar;
    }
}
