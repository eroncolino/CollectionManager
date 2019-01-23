package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

/**
 * Class that implements the action listener class to listen to the update button.
 *
 * @author Elena Roncolino
 */
public class DeleteListener implements ActionListener {
    private static final Logger logger = Logger.getLogger(DeleteListener.class.getName());

    /**
     * Overrides the default one and queries the database in order to update the selected record.
     *
     * @param e The generated event caused by pressing the button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = CarPanel.tab.getSelectedRow();
        int carId = (int) CarPanel.tab.getModel().getValueAt(index, 0);

        int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to permanently delete this car?", "Warning", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.YES_OPTION) {

            DatabaseConnection.getInstance().deleteCar(carId);
        }
    }
}
