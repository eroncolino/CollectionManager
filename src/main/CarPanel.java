package main;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that displays the panel where the user can manage his collection.
 *
 * @author Elena Roncolino
 */
public class CarPanel extends JPanel {
    static JComboBox columnsList;
    static JTextField textField;
    static JTable tab;
    private JButton deleteButton;
    private JButton updateButton;
    private static String[] tableColumns;

    private static final Logger logger = Logger.getLogger(CarPanel.class.getName());

    /**
     * Car panel constructor.
     */
    public CarPanel() {

        // Create the container panel
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setOpaque(false);

        //Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        JLabel mainLabel = new JLabel("YOUR CAR COLLECTION MANAGER");
        mainLabel.setFont(new Font("Arial", Font.BOLD, 40));
        mainLabel.setForeground(new Color(14, 35, 46));
        titlePanel.add(mainLabel);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(titlePanel);
        container.add(Box.createRigidArea(new Dimension(0, 30)));

        // Criteria (search by) Panel
        JPanel criteria = new JPanel();
        criteria.setOpaque(false);
        criteria.setLayout(new BoxLayout(criteria, BoxLayout.X_AXIS));
        JLabel searchLabel = new JLabel("Search by: ");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        searchLabel.setForeground(new Color(14, 35, 46));
        String[] boxColumns = new String[]{"Show all", "Name", "Brand", "Cubic capacity", "PS", "KW", "Cylinders", "Fuel type"};
        tableColumns = new String[]{"ID", "Name", "Brand", "Cubic capacity", "PS", "KW", "Cylinders", "Fuel type"};
        columnsList = new JComboBox(boxColumns);
        columnsList.setPreferredSize(new Dimension(200, 20));
        columnsList.setMaximumSize(new Dimension(200, 20));
        criteria.add(searchLabel);
        criteria.add(Box.createRigidArea(new Dimension(30, 0)));
        criteria.add(columnsList);
        container.add(Box.createRigidArea(new Dimension(0, 30)));
        container.add(criteria);

        // Parameter Panel
        JPanel parameterPanel = new JPanel();
        parameterPanel.setOpaque(false);
        parameterPanel.setLayout(new BoxLayout(parameterPanel, BoxLayout.X_AXIS));
        JLabel stringLabel = new JLabel("Enter string: ");
        stringLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        stringLabel.setForeground(new Color(14, 35, 46));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 20));
        textField.setMaximumSize(new Dimension(200, 20));
        parameterPanel.add(stringLabel);
        parameterPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        parameterPanel.add(textField);
        container.add(Box.createRigidArea(new Dimension(0, 20)));
        container.add(parameterPanel);

        // Main Part Panel (the row)
        JPanel mainRow = new JPanel();
        mainRow.setOpaque(false);
        mainRow.setLayout(new BoxLayout(mainRow, BoxLayout.X_AXIS));

        // Table
        JPanel tablePanel = new JPanel();
        tablePanel.setOpaque(false);

        tab = new JTable() {
            /**
             * Method that allows to select one row at a time and to deselect a row after it has been selected.
             * @param rowIndex The row index of the selected cell.
             * @param columnIndex The column index of the selected cell.
             * @param toggle If true a row can be selected.
             * @param extend If true the selection can be extended to multiple rows.
             */
            public void changeSelection(int rowIndex, int columnIndex,
                                        boolean toggle, boolean extend) {
                super.changeSelection(rowIndex, columnIndex, true, false);
            }
        };

        //tab.setModel(new CustomTableModel(myData, tableColumns));
        tab.setDefaultRenderer(Object.class, new StripedRowTableCellRenderer());
        JScrollPane pane = new JScrollPane(tab);
        pane.setPreferredSize(new Dimension(900, 500));
        tablePanel.add(pane);
        mainRow.add(tablePanel);

        try {
            repaintTable(DatabaseConnection.getInstance().getCarsMatrixByUserId(User.getUserId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tab.setToolTipText("Click to select a row.");

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        Dimension d = new Dimension(150, 40);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton findButton = new JButton("Find");
        findButton.setFont(new Font("Arial", Font.PLAIN, 18));
        findButton.setPreferredSize(d);
        findButton.setMaximumSize(new Dimension(150, 40));
        findButton.setIcon(new ImageIcon("glass.png"));
        findButton.setHorizontalTextPosition(AbstractButton.RIGHT);
        findButton.addActionListener(new FindListener());

        updateButton = new JButton("Update");
        updateButton.setEnabled(false);
        updateButton.setFont(new Font("Arial", Font.PLAIN, 18));
        updateButton.setMaximumSize(d);
        updateButton.setIcon(new ImageIcon("update.png"));
        updateButton.setHorizontalTextPosition(AbstractButton.RIGHT);
        updateButton.addActionListener(new UpdateListener());

        JButton insertButton = new JButton("Insert");
        insertButton.setFont(new Font("Arial", Font.PLAIN, 18));
        insertButton.setMaximumSize(d);
        insertButton.setIcon(new ImageIcon("insert.png"));
        insertButton.setHorizontalTextPosition(AbstractButton.RIGHT);
        insertButton.addActionListener(new InsertListener());

        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false);
        deleteButton.setFont(new Font("Arial", Font.PLAIN, 18));
        deleteButton.setMaximumSize(d);
        deleteButton.setIcon(new ImageIcon("delete.png"));
        deleteButton.setHorizontalTextPosition(AbstractButton.RIGHT);
        deleteButton.addActionListener(new DeleteListener());

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        buttonPanel.add(findButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(insertButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainRow.add(buttonPanel);

        container.add(Box.createRigidArea(new Dimension(0, 50)));
        container.add(mainRow);
        add(container);

        //Allow only one row to be selected at a time
        tab.setRowSelectionAllowed(true);
        ListSelectionModel rowSelectionModel = tab.getSelectionModel();
        rowSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        rowSelectionModel.addListSelectionListener(new ListSelectionListener() {
            /**
             * Overrides the default one to make the update and delete buttons enabled if a row is selected.
             * @param e The event of selecting a row.
             */
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[] indexes = tab.getSelectedRows();

                if (indexes.length == 0) {
                    updateButton.setEnabled(false);
                    deleteButton.setEnabled(false);
                } else {
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                }
            }
        });
    }

    /**
     * Fills the table with the desired data.
     *
     * @param dataToBeInserted The Object matrix containing car records.
     */
    public static void repaintTable(Object[][] dataToBeInserted) {
        if (tab != null) {
            logger.log(Level.INFO, "Repainting table");
            tab.setModel(new CustomTableModel(dataToBeInserted, tableColumns));

            TableColumnModel columnModel = tab.getColumnModel();
            columnModel.getColumn(1).setPreferredWidth(150);
            columnModel.getColumn(2).setPreferredWidth(100);
            columnModel.getColumn(3).setPreferredWidth(20);
            columnModel.getColumn(4).setPreferredWidth(20);
            columnModel.getColumn(5).setPreferredWidth(20);
            columnModel.getColumn(6).setPreferredWidth(20);
            columnModel.getColumn(7).setPreferredWidth(50);
            tab.removeColumn(columnModel.getColumn(0));
            textField.setText("");
        }
    }
}
