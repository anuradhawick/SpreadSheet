
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------

import Algorithms.CellSort;
import Algorithms.sumavg;

import FileHandler.ReaderCS;
import FileHandler.saverCS;

import SheetExceptions.CellTypeMissMatch;
import SheetExceptions.InvalidOperation;
import SheetExceptions.NullCell;
import SheetExceptions.RangeError;

import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.JOptionPane;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class MethodHandler {
    public static Cell[][] C;

    private MethodHandler() {}

    public static void openFile(TableSet T) {
        Thread t = new Thread(new ReaderCS(T));

        t.start();
    }

    public static void saveFile(TableSet T, boolean SaveAs) {
        Thread saver = new Thread(new saverCS(T, SaveAs));

        saver.start();
    }

    public static void sortAsc(TableSet T) {
        int cols[] = T.getTable().getSelectedColumns();
        int rows[] = T.getTable().getSelectedRows();

        T.getTable().editingCanceled(null);
        T.getTable().clearSelection();

        if ((cols.length == 0) || (rows.length == 0)) {
            JOptionPane.showMessageDialog(null, "Please select a range", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        try {
            CellSort.Sort(rows[0], cols[0], rows[rows.length - 1], cols[cols.length - 1], T, false);
        } catch (RangeError | CellTypeMissMatch ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void sortDes(TableSet T) {
        int cols[] = T.getTable().getSelectedColumns();
        int rows[] = T.getTable().getSelectedRows();

        if ((cols.length == 0) || (rows.length == 0)) {
            JOptionPane.showMessageDialog(null, "Please select a range", "Error", JOptionPane.ERROR_MESSAGE);

            return;
        }

        T.getTable().editingCanceled(null);
        T.getTable().clearSelection();

        try {
            CellSort.Sort(rows[0], cols[0], rows[rows.length - 1], cols[cols.length - 1], T, true);
        } catch (RangeError | CellTypeMissMatch ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void sum(TableSet T) {
        String ans;
        String s = JOptionPane.showInputDialog(null, "Enter the range", "Sum", JOptionPane.INFORMATION_MESSAGE);

        if (s.equals("")) {
            return;
        }

        String[] a = s.split("[ ]|[-]|[,]");

        if (a.length != 2) {
            JOptionPane.showMessageDialog(null, "Error please re-enter the range", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ans = sumavg.getSum(a[0], a[1], T);
                T.updateCurrentCell(ans);
            } catch (InvalidOperation | RangeError | NullCell ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void average(TableSet T) {
        String ans;
        String s = JOptionPane.showInputDialog(null, "Enter the range", "Average", JOptionPane.INFORMATION_MESSAGE);

        if (s.equals("")) {
            return;
        }

        String[] a = s.split("[ ]|[-]|[,]");

        if (a.length != 2) {
            JOptionPane.showMessageDialog(null, "Error please re-enter the range", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                ans = sumavg.getAvg(a[0], a[1], T);
                T.updateCurrentCell(ans);
            } catch (InvalidOperation | RangeError | NullCell ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void cutcopy(TableSet TS, boolean cut) {
        int[] rows = TS.getTable().getSelectedRows();
        int[] cols = TS.getTable().getSelectedColumns();

        SpreadSheetUI.clipBoard = new String[rows.length][cols.length];

        int i = 0;
        int j;

        for (int r : rows) {
            j = 0;

            for (int c : cols) {
                Cell C = ((TableModel) TS.getTable().getModel()).getData()[r][c];

                if (C != null) {
                    SpreadSheetUI.clipBoard[i][j] = C.D.getRawData();
                }

                if (cut) {
                    TS.getTable().setValueAt(null, r, c);
                }

                j++;
            }

            i++;
        }
    }

    public static void paste(TableSet TS) {
        int[]  rows = TS.getTable().getSelectedRows();
        int[]  cols = TS.getTable().getSelectedColumns();
        String s;

        if (SpreadSheetUI.clipBoard == null) {
            return;
        }

        int r = 0,
            c = 0;

        for (int i = rows[0]; i < rows[0] + SpreadSheetUI.clipBoard.length; i++) {
            c = 0;

            for (int j = cols[0]; j < cols[0] + SpreadSheetUI.clipBoard[0].length; j++) {
                s = SpreadSheetUI.clipBoard[r][c];

                try {
                    TS.getTable().getModel().setValueAt(s, i, j);
                } catch (ArrayIndexOutOfBoundsException e) {}

                c++;
            }

            r++;
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
