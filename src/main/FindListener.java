package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static main.CarPanel.repaintTable;
import static main.CarPanel.textField;

/**
 * Class that implements the action listener class to listen to the find button.
 *
 * @author Elena Roncolino
 */
public class FindListener implements ActionListener {
    private static final Logger logger = Logger.getLogger(FindListener.class.getName());

    /**
     * Overrides the default one and queries the database in order to find a match for the given input.
     *
     * @param e The generated event caused by pressing the button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String selectedColumn = (String) CarPanel.columnsList.getSelectedItem();
        String stringToBeMatched = CarPanel.textField.getText();
        Object[][] myData;

        if (selectedColumn == "Show all") {
            try {
                repaintTable(DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId()));
            } catch (SQLException e1) {
                logger.log(Level.SEVERE, "Problem with the database", e);
            }
            textField.setText("");
        } else {
            if (stringToBeMatched.length() != 0) {

                switch (selectedColumn) {
                    case "Name":
                        if (stringToBeMatched.length() < 50) {
                            try {
                                myData = DatabaseConnection.getInstance().getCarsByString(User.getUserId(), "name", stringToBeMatched);
                                //If matches to the given string have been found, they are shown in the table. Otherwise all the data from the table are shown again
                                if (myData.length != 0)
                                    repaintTable(myData);

                                else {
                                    JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                    myData = (DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId()));
                                    repaintTable(myData);
                                }
                            } catch (SQLException e1) {
                                logger.log(Level.SEVERE, "Problem with the database", e);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Car name must be shorter than 50 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case "Brand":
                        if (stringToBeMatched.length() < 50) {
                            try {
                                myData = DatabaseConnection.getInstance().getCarsByString(User.getUserId(), "brand", stringToBeMatched);

                                if (myData.length != 0)
                                    repaintTable(myData);

                                else {
                                    JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                    myData = (DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId()));
                                    repaintTable(myData);
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Car brand must be shorter than 50 characters.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;

                    case "Cubic capacity":
                        try {
                            int numberToBeMatched = Integer.parseInt(stringToBeMatched);
                            myData = DatabaseConnection.getInstance().getCarsByInt(User.getUserId(), "cubiccapacity", numberToBeMatched);

                            if (myData.length != 0)
                                repaintTable(myData);

                            else {
                                JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                myData = DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId());
                                repaintTable(myData);
                            }
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "Cubic capacity must be an integer.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case "PS":
                        try {
                            int numberToBeMatched = Integer.parseInt(stringToBeMatched);
                            myData = DatabaseConnection.getInstance().getCarsByInt(User.getUserId(), "ps", numberToBeMatched);

                            if (myData.length != 0)
                                repaintTable(myData);

                            else {
                                JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                myData = DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId());
                                repaintTable(myData);
                            }
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "PS must be an integer.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case "KW":
                        try {
                            int numberToBeMatched = Integer.parseInt(stringToBeMatched);
                            myData = DatabaseConnection.getInstance().getCarsByInt(User.getUserId(), "kw", numberToBeMatched);

                            if (myData.length != 0)
                                repaintTable(myData);

                            else {
                                JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                myData = DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId());
                                repaintTable(myData);
                            }
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "KW must be an integer.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case "Cylinders":
                        try {
                            int numberToBeMatched = Integer.parseInt(stringToBeMatched);
                            myData = DatabaseConnection.getInstance().getCarsByInt(User.getUserId(), "cylinders", numberToBeMatched);

                            if (myData.length != 0)
                                repaintTable(myData);

                            else {
                                JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                myData = DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId());
                                repaintTable(myData);
                            }
                        } catch (NumberFormatException n) {
                            JOptionPane.showMessageDialog(null, "Cylinders must be an integer.", "Warning", JOptionPane.WARNING_MESSAGE);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        break;

                    case "Fuel type":
                        if (stringToBeMatched.equalsIgnoreCase("diesel") ||
                                stringToBeMatched.equalsIgnoreCase("gasoline") ||
                                stringToBeMatched.equalsIgnoreCase("hybrid") ||
                                stringToBeMatched.equalsIgnoreCase("electric")) {
                            try {
                                myData = DatabaseConnection.getInstance().getCarsByString(User.getUserId(), "fueltype", stringToBeMatched);

                                if (myData.length != 0)
                                    repaintTable(myData);

                                else {
                                    JOptionPane.showMessageDialog(null, "No results found for that query!", "Error", JOptionPane.ERROR_MESSAGE);
                                    myData = DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId());
                                    repaintTable(myData);
                                }
                            } catch (SQLException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Fuel type can be diesel, gasoline,\n" +
                                    "hybrid or electric.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                }
                textField.setText("");
            } else
                JOptionPane.showMessageDialog(null, "Enter the string to be found.", "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
}
