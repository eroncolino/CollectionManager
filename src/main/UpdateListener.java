package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that implements the action listener class to listen to the update button.
 *
 * @author Elena Roncolino
 */
public class UpdateListener implements ActionListener {
    private static final Logger logger = Logger.getLogger(UpdateListener.class.getName());

    /**
     * Overrides the default one and queries the database in order to update the selected record.
     *
     * @param e The generated event caused by pressing the button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int index = CarPanel.tab.getSelectedRow();

        // Container
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new BoxLayout(addPanel, BoxLayout.Y_AXIS));
        addPanel.add(Box.createRigidArea(new Dimension(500, 50)));

        // First row: Car name
        JPanel firstRow = new JPanel();
        firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));

        JLabel name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField nameField = new JTextField(String.valueOf(CarPanel.tab.getModel().getValueAt(index, 1)));
        firstRow.add(name);
        firstRow.add(Box.createRigidArea(new Dimension(138, 0)));
        firstRow.add(nameField);

        addPanel.add(firstRow);

        // Second row: Car brand
        JPanel secondRow = new JPanel();
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));

        JLabel brand = new JLabel("Brand");
        brand.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField brandField = new JTextField(String.valueOf(CarPanel.tab.getModel().getValueAt(index, 2)));
        secondRow.add(brand);
        secondRow.add(Box.createRigidArea(new Dimension(138, 0)));
        secondRow.add(brandField);

        addPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addPanel.add(secondRow);

        // Third row: cubic capacity
        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.X_AXIS));

        JLabel cubicCapacity = new JLabel("Cubic capacity");
        cubicCapacity.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField cubicCapacityField = new JTextField(String.valueOf(CarPanel.tab.getModel().getValueAt(index, 3)));
        thirdRow.add(cubicCapacity);
        thirdRow.add(Box.createRigidArea(new Dimension(70, 0)));
        thirdRow.add(cubicCapacityField);

        addPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addPanel.add(thirdRow);

        // Fourth row: PS
        JPanel fourthRow = new JPanel();
        fourthRow.setLayout(new BoxLayout(fourthRow, BoxLayout.X_AXIS));

        JLabel ps = new JLabel("PS");
        ps.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField psField = new JTextField(String.valueOf(CarPanel.tab.getModel().getValueAt(index, 4)));
        fourthRow.add(ps);
        fourthRow.add(Box.createRigidArea(new Dimension(162, 0)));
        fourthRow.add(psField);

        addPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addPanel.add(fourthRow);

        // Fifth row: KW
        JPanel fifthRow = new JPanel();
        fifthRow.setLayout(new BoxLayout(fifthRow, BoxLayout.X_AXIS));

        JLabel kw = new JLabel("KW");
        kw.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField kwField = new JTextField(String.valueOf(CarPanel.tab.getModel().getValueAt(index, 5)));
        fifthRow.add(kw);
        fifthRow.add(Box.createRigidArea(new Dimension(158, 0)));
        fifthRow.add(kwField);

        addPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addPanel.add(fifthRow);

        // Sixth row: cylinders
        JPanel sixthRow = new JPanel();
        sixthRow.setLayout(new BoxLayout(sixthRow, BoxLayout.X_AXIS));

        JLabel cylinders = new JLabel("Cylinders");
        cylinders.setFont(new Font("Arial", Font.PLAIN, 18));
        JTextField cylindersField = new JTextField(String.valueOf(CarPanel.tab.getModel().getValueAt(index, 6)));
        sixthRow.add(cylinders);
        sixthRow.add(Box.createRigidArea(new Dimension(112, 0)));
        sixthRow.add(cylindersField);

        addPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addPanel.add(sixthRow);

        // Seventh row: Fuel type
        JPanel seventhRow = new JPanel();
        seventhRow.setLayout(new BoxLayout(seventhRow, BoxLayout.X_AXIS));

        JLabel fuelType = new JLabel("Fuel type");
        fuelType.setFont(new Font("Arial", Font.PLAIN, 18));
        String[] possibleFuels = {"Diesel", "Gasoline", "Hybrid", "Electric"};
        JComboBox comboBox = new JComboBox(possibleFuels);
        String fuel = (String) CarPanel.tab.getModel().getValueAt(index, 7);
        switch (fuel) {
            case "Diesel":
                comboBox.setSelectedIndex(0);
                break;

            case "Gasoline":
                comboBox.setSelectedIndex(1);
                break;

            case "Hybrid":
                comboBox.setSelectedIndex(2);
                break;

            case "Electric":
                comboBox.setSelectedIndex(3);
                break;
        }
        seventhRow.add(fuelType);
        seventhRow.add(Box.createRigidArea(new Dimension(115, 0)));
        seventhRow.add(comboBox);

        addPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        addPanel.add(seventhRow);
        addPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        //Add everything to JOptionPane
        int result = JOptionPane.showConfirmDialog(null,
                addPanel, "Update car record.", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {

            if (nameField.getText().length() == 0 || nameField.getText().length() > 50) {
                JOptionPane.showMessageDialog(null, "Car name field cannot be null and can be at most 50 characters long.\n" +
                        "No record will be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Incorrect car name format");
                return;
            }

            if (brandField.getText().length() == 0 || brandField.getText().length() > 50) {
                JOptionPane.showMessageDialog(null, "Car brand field cannot be null and can be at most 50 characters long.\n" +
                        "No record will be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Incorrect car brand format");
                return;
            }

            try {
                Integer.parseInt(cubicCapacityField.getText());
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Cubic capacity must be an integer.\n" +
                        "No record will be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Incorrect cubic capacity format");
                return;
            }

            try {
                Integer.parseInt(psField.getText());
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "PS must be an integer.\n" +
                        "No record will be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Incorrect ps format");
                return;
            }

            try {
                Integer.parseInt(kwField.getText());
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "KW must be an integer.\n" +
                        "No record will be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Incorrect kw format");
                return;
            }

            try {
                Integer.parseInt(cylindersField.getText());
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, "Cylinders must be an integer.\n" +
                        "No record will be updated.", "Error", JOptionPane.ERROR_MESSAGE);
                logger.log(Level.WARNING, "Incorrect cylinders format");
                return;
            }

            String selectedFuel = (String) comboBox.getSelectedItem();

            int carId = (int) CarPanel.tab.getModel().getValueAt(index, 0);

            try {
                DatabaseConnection.getInstance().updateCar(carId, nameField.getText(), brandField.getText(),
                        Integer.parseInt(cubicCapacityField.getText()),
                        Integer.parseInt(psField.getText()), Integer.parseInt(kwField.getText()),
                        Integer.parseInt(cylindersField.getText()), selectedFuel);

                CarPanel.repaintTable(DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId()));
            } catch (SQLException e1) {
                logger.log(Level.SEVERE, "Problem with the database", e);
            }
        }
    }
}
