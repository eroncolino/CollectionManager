package main;

import javax.swing.table.DefaultTableModel;

/**
 * Class that allows the creation of a customised table model.
 * @author Elena Roncolino
 */
public class CustomTableModel extends DefaultTableModel{

    /**
     * Constructor of the table model.
     * @param data The data to be inserted into the table.
     * @param colName The name of the table columns.
     */
    public CustomTableModel(Object[][] data, String[] colName) {
        super(data, colName);
    }

    /**
     * Method that prevents a cell from being edited.
     * @param rowIndex The row the selected cell is in.
     * @param columnIndex The column the selected cell is in.
     * @return boolean Returns <code>true</code> if the cell is editable.
     */
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
