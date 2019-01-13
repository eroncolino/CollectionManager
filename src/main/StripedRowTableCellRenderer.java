package main;

import java.awt.Color;
import java.awt.Component;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Class that allows the table to have striped rows.
 * @author Elena Roncolino
 * Part of the code was taken from <a href="https://github.com/alvinj/Hyde/blob/master/src/main/java/com/devdaily/heidi/StripedRowTableCellRenderer.java">
 *     this Github repository</a> and adapted to fit the needs.
 */
public class StripedRowTableCellRenderer extends DefaultTableCellRenderer {

    private final JCheckBox ckb = new JCheckBox();

    /**
     * Method that allows the background to show up.
     */
    public StripedRowTableCellRenderer() {
        setOpaque(true);
    }

    /**
     * Method that allows to create a customized cell renderer
     * @param table Parameter required by the super constructor.
     * @param value Parameter required by the super constructor.
     * @param isSelected Parameter required by the super constructor.
     * @param hasFocus Parameter required by the super constructor.
     * @param row Parameter required by the super constructor.
     * @param column Parameter required by the super constructor.
     * @return Component The component with the customized cell renderer.
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        JComponent c = (JComponent)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!isSelected)
        {
            if (row % 2 == 0)
            {
                c.setBackground(Color.white);
            }
            else
            {
                c.setBackground(new Color(222, 223, 224));
            }
        }

        if (value instanceof Boolean) {
            ckb.setSelected(((Boolean) value));
            ckb.setHorizontalAlignment(JLabel.CENTER);
            ckb.setBackground(super.getBackground());
            if (isSelected || hasFocus) {
                ckb.setBackground(table.getSelectionBackground());
            }
            return ckb;
        }
        return c;
    }
}
