package GUI;

//~--- non-JDK imports --------------------------------------------------------

import Algorithms.cellMaker;
import Algorithms.parserManager;

import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import java.text.ParseException;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class TableModel extends AbstractTableModel {

    // table is a cell array of 100x100
    public static Cell[][]    sheet            = new Cell[100][100];
    private static final long serialVersionUID = 1L;

    public synchronized void updateData(Cell[][] data) {
        sheet = data;
        fireTableStructureChanged();
    }

    public void setCell(Cell c, int i, int j) {
        sheet[i][j] = c;
    }

    public synchronized Cell[][] getData() {
        return sheet;
    }

    @Override
    public int getRowCount() {
        return sheet.length;
    }

    @Override
    public int getColumnCount() {
        return sheet[0].length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        String out = "";
        String datatype;

        try {
            out      = parserManager.getParsedItems(sheet[i][i1].D.getRawData()).get(1);
            datatype = parserManager.getParsedItems(sheet[i][i1].D.getRawData()).get(0);
            sheet[i][i1].D.setFormattedData(out);
            sheet[i][i1].D.setType(datatype);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            return "";
        } catch (StackOverflowError e) {
            setValueAt(null, i, i1);

            return "";
        }

        return (Object) out;
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return true;    // every cell in the table is editable
    }

    // this was overridden, otherwise it not allowed to edit the cells in the jTable
    @Override
    public void setValueAt(Object o, int i, int j) {
        if ((i < 0) || (i > sheet.length) || (j > sheet.length) || (j < 0)) {
            return;
        }

        String font = null,
               size = null;

        if (sheet[i][j] != null) {
            font = sheet[i][j].getFont();
            size = sheet[i][j].getSize();
        }

        try {
            if (o.toString().equals(getValueAt(i, j))) {
                return;
            }
        } catch (Exception e) {
            if ((o == null) || o.toString().equals("")) {
                sheet[i][j] = null;
            }

            return;
        }

        try {
            sheet[i][j] = cellMaker.getCell(o.toString());

            if ((font != null) && (size != null)) {
                sheet[i][j].setFont(font);
                sheet[i][j].setSize(size);
            }
        } catch (ParseException ex) {}
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
