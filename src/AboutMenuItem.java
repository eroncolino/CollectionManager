import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the about menu item.
 * @author Elena Roncolino
 */
public class AboutMenuItem extends JMenuItem {
    private ImageIcon aboutImage;
    private static JMenuItem aboutMenu;

    /**
     *
     */
    public AboutMenuItem() {
        aboutImage = new ImageIcon(new ImageIcon("images/about.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        aboutMenu = new JMenuItem("About", aboutImage);
        String aboutMessage = "This application has been developed by a second year Computer Science and Engineering student\n" +
                " of the Free University of Bolzano as a project for the Advanced Programming course.";
        aboutMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, aboutMessage);
            }
        });
    }

    /**
     *
     * @return
     */
    public static JMenuItem getExitMenuItem(){
        return aboutMenu;
    }
}
