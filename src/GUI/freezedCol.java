
/*
* the column that is used to display the row numbers
 */
package GUI;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class freezedCol extends AbstractTableModel {
    private static final long serialVersionUID = 1L;

    @Override
    public int getRowCount() {
        return 1000;
    }

    @Override
    public int getColumnCount() {
        return 1;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return String.valueOf(i + 1);
    }

    @Override
    public String getColumnName(int column) {
        return null;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
