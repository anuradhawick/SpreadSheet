
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------

import expEval.StringParser;

import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class CellRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        Cell c;

        // getting the cell
        try {
            c = ((TableModel) table.getModel()).getData()[row][column];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }

        // get the component to render
        Component tableCellRendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
                                                   row, column);
        int align = DefaultTableCellRenderer.LEFT;    // defult alignment

        if ((value != null)
                &&!(StringParser.getList(value.toString()).get(0).equals("Boolean")
                    || StringParser.getList(value.toString()).get(0).equals("Text"))) {
            align = DefaultTableCellRenderer.RIGHT;    // aligning right
        }

        if (c != null) {

            // setting the tooltip to display the raw data
            try {
                ((DefaultTableCellRenderer) tableCellRendererComponent).setToolTipText(
                    ((TableModel) table.getModel()).getData()[row][column].D.getRawData());
            } catch (NullPointerException e) {

                // do nothing
            }

            // setting the font
            if (c.getFont() != null) {
                tableCellRendererComponent.setFont(new Font(c.getFont(), Font.PLAIN, Integer.parseInt(c.getSize())));
            }

            // clearing the tooltip
        } else if ((c == null) || c.D.getRawData().equals("")) {
            ((DefaultTableCellRenderer) tableCellRendererComponent).setToolTipText(null);
        }

        ((DefaultTableCellRenderer) tableCellRendererComponent).setHorizontalAlignment(align);

        // colourin the cell text
        if ((c != null) && c.getColor().equals("red")) {
            tableCellRendererComponent.setForeground(Color.red);
        } else {
            tableCellRendererComponent.setForeground(Color.black);
        }

        // updating the raw value as equation text field changes
        int i = table.getSelectedRow();
        int j = table.getSelectedColumn();

        if (!TextBoxSet.fxText.getText().equals("") && (i == row) && (j == column) && TextBoxSet.editing) {
            ((JLabel) tableCellRendererComponent).setText(TextBoxSet.fxText.getText());
            ((DefaultTableCellRenderer) tableCellRendererComponent).setHorizontalAlignment(
                DefaultTableCellRenderer.LEFT);
        }

        // return the component
        return tableCellRendererComponent;
    }

    // for the purpose of rerendering the table
    public synchronized static void renderAll(TableSet TS) {
        for (int i = 0; i < TS.getTable().getColumnCount(); i++) {
            TS.getTable().getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
