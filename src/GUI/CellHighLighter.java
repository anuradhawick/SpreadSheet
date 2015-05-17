
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------

import Algorithms.CellReferenceStringMaker;

import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class CellHighLighter implements Runnable {
    private final TableSet TS;
    private final String[] Array;

    public CellHighLighter(String[] Array, TableSet TS) {
        this.Array = Array;
        this.TS    = TS;
    }

    @Override
    public void run() {
        ArrayList<String> cellSet = new ArrayList<String>();
        Cell[][]          C       = ((TableModel) TS.getTable().getModel()).getData();

        if (Array.length > 1) {
            for (String i : Array) {
                if (i.matches("[A-Z][A-Z]*[0-9][0-9]*")) {
                    cellSet.add(i);
                }
            }

            // clearing the whole table
            for (int m = 0; m < C.length; m++) {
                for (int n = 0; n < C[0].length; n++) {
                    try {
                        ((TableModel) TS.getTable().getModel()).getData()[m][n].setColor("white");
                    } catch (NullPointerException e) {}
                }
            }

            // coloring the required part
            for (String i : cellSet) {
                String[] ID  = i.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
                int      row = Integer.parseInt(ID[1]) - 1;
                int      col = CellReferenceStringMaker.colConv(ID[0]) - 1;

                try {
                    ((TableModel) TS.getTable().getModel()).getData()[row][col].setColor("red");
                } catch (Exception e) {}
            }
        } else {
            for (int m = 0; m < C.length; m++) {
                for (int n = 0; n < C[0].length; n++) {
                    try {
                        ((TableModel) TS.getTable().getModel()).getData()[m][n].setColor("white");
                    } catch (NullPointerException e) {}
                }
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
