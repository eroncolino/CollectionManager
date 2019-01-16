package main.menuitems;

import main.DatabaseConnection;
import main.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that build be menu item where it is possible to check how many records are in the database, how many record
 * have been added and deleted during the open session and the path to the database file.
 *
 * @author Elena Roncolino
 */
public class StatisticsMenuItem extends JMenuItem {
    private static final Logger logger = Logger.getLogger(StatisticsMenuItem.class.getName());

    /**
     * Builds the CSV export menu item.
     *
     * @return JMenuItem The CSV export menu item.
     */
    public JMenuItem buildStatisticsMenuItem() {
        ImageIcon statsImage = new ImageIcon(new ImageIcon("images/graph.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JMenuItem statsMenu = new JMenuItem("Statistics", statsImage);
        statsMenu.addActionListener(new ActionListener() {
            /**
             * Overrides the default method and shows a JOption pane with the statistics.
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                File dbFile = new File(DatabaseConnection.getInstance().getDatabaseName());
                String dbPath = dbFile.getAbsolutePath();

                JPanel firstRow = new JPanel();
                firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));
                JLabel pathLabel = new JLabel("Path to database file: ");
                pathLabel.setForeground(new Color(14, 35, 46));
                pathLabel.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel path = new JLabel(dbPath);
                path.setForeground(new Color(14, 35, 46));
                path.setFont(new Font("Arial", Font.BOLD, 12));
                firstRow.add(pathLabel);
                firstRow.add(path);

                JPanel secondRow = new JPanel();
                secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
                JLabel totalLabel = new JLabel("Number of all your car records: ");
                totalLabel.setForeground(new Color(14, 35, 46));
                totalLabel.setFont(new Font("Arial", Font.BOLD, 12));

                JLabel total = null;
                try {
                    total = new JLabel(String.valueOf(DatabaseConnection.getInstance().getTotalNumberOfRecordsByUserId(User.getUserId())));
                } catch (SQLException e1) {
                    logger.log(Level.SEVERE, "Problem with the database", e);
                }
                total.setForeground(new Color(14, 35, 46));
                total.setFont(new Font("Arial", Font.BOLD, 12));
                secondRow.add(totalLabel);
                secondRow.add(total);

                JPanel thirdRow = new JPanel();
                thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.X_AXIS));
                JLabel addedLabel = new JLabel("Number of records added in this session: ");
                addedLabel.setForeground(new Color(14, 35, 46));
                addedLabel.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel added = new JLabel(String.valueOf(DatabaseConnection.getInstance().getAddedRecordsNumber()));
                added.setForeground(new Color(14, 35, 46));
                added.setFont(new Font("Arial", Font.BOLD, 12));
                thirdRow.add(addedLabel);
                thirdRow.add(added);

                JPanel fourthRow = new JPanel();
                fourthRow.setLayout(new BoxLayout(fourthRow, BoxLayout.X_AXIS));
                JLabel deletedLabel = new JLabel("Number of cars deleted in this session: ");
                deletedLabel.setForeground(new Color(14, 35, 46));
                deletedLabel.setFont(new Font("Arial", Font.BOLD, 12));
                JLabel deleted = new JLabel(String.valueOf(DatabaseConnection.getInstance().getDeletedRecordsNumber()));
                deleted.setForeground(new Color(14, 35, 46));
                deleted.setFont(new Font("Arial", Font.BOLD, 12));
                fourthRow.add(deletedLabel);
                fourthRow.add(deleted);

                JPanel container = new JPanel();
                container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
                container.add(firstRow);
                container.add(Box.createRigidArea(new Dimension(0, 20)));
                container.add(secondRow);
                container.add(Box.createRigidArea(new Dimension(0, 20)));
                container.add(thirdRow);
                container.add(Box.createRigidArea(new Dimension(0, 20)));
                container.add(fourthRow);
                container.add(Box.createRigidArea(new Dimension(0, 20)));

                JOptionPane.showMessageDialog(null, container, "Statistics", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return statsMenu;
    }
}