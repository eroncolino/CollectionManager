import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class that creates the exit menu item.
 * @author Elena Roncolino
 */
public class ExitMenuItem extends JMenuItem {
    private ImageIcon exitImage;
    private static JMenuItem exitMenu;

    /**
     *
     */
    public ExitMenuItem() {
        exitImage = new ImageIcon(new ImageIcon("images/exit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        exitMenu = new JMenuItem("Exit", exitImage);
        exitMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseConnection.getInstance().closeConnection();
                System.exit(0);
            }
        });
    }

    /**
     *
     * @return
     */
    public static JMenuItem getExitMenuItem(){
        return exitMenu;
    }
}