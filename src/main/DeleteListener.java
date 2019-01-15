package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Class that implements the action listener class to listen to the update button.
 *
 * @author Elena Roncolino
 */
public class DeleteListener implements ActionListener {

    /**
     * Method that overrides the default one and queries the database in order to update the selected record.
     *
     * @param e The generated event caused by pressing the button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = CarPanel.tab.getSelectedRow();
        int carId = (int) CarPanel.tab.getModel().getValueAt(index, 0);

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this car?", "Warning", JOptionPane.WARNING_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            try {
                DatabaseConnection.getInstance().deleteCar(carId);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
