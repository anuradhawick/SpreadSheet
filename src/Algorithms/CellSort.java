
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import GUI.CellRenderer;
import GUI.TableSet;

import SheetExceptions.CellTypeMissMatch;
import SheetExceptions.RangeError;

import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class CellSort {
    private TableSet TS;

    private CellSort() {}

    // No thread is used to avoid any user interraction untill the process finishes
    public static void Sort(int row1, int col1, int row2, int col2, TableSet TS, boolean descending)
            throws RangeError, CellTypeMissMatch {
        Cell[][]        CellArray = TS.getModel().getData().clone();
        ArrayList<Cell> AL        = new ArrayList<Cell>();
        int[]           startcell = new int[2];
        int[]           endcell   = new int[2];

        // cell initiatives
        startcell[0] = row1;
        startcell[1] = col1;
        endcell[0]   = row2;
        endcell[1]   = col2;

        String type = null;

        if (!(startcell[1] == endcell[1]) &&!(startcell[0] == endcell[0])) {
            throw new RangeError("False range for sorting");
        }

        if (startcell[0] == endcell[0]) {    // horzontal scenario
            row1 = endcell[0];
            col1 = startcell[1];
            col2 = endcell[1];

            if (col1 > col2) {
                throw new RangeError("Range should start from left to right");
            }

            for (int i = col1; i <= col2; i++) {
                if (CellArray[row1][i] == null) {
                    throw new RangeError("Null field included");
                }

                if (!(CellArray[row1][i].D.getType().equals("Integer")
                        || CellArray[row1][i].D.getType().equals("Float"))) {
                    throw new CellTypeMissMatch("Sorting can be done only for integers and floats");
                }

                AL.add(CellArray[row1][i]);
            }

            Collections.sort(AL);

            if (descending) {    // reversing the array after sorting in ascending order to get descending order
                Collections.reverse(AL);
            }

            for (int i = col1; i <= col2; i++) {
                CellArray[row1][i] = AL.get(i - col1);
            }

            TS.getModel().updateData(CellArray);
            CellRenderer.renderAll(TS);
        } else {                 // Vertical sorting scenorio
            col1 = startcell[1];
            row1 = startcell[0];
            row2 = endcell[0];

            if (row1 > row2) {
                throw new RangeError("Range should start from top to bottom");
            }

            for (int i = row1; i <= row2; i++) {
                if (CellArray[i][col1] == null) {
                    throw new RangeError("Null field included");
                }

                if (CellArray[i][col1].D.getType().equals("Text") || CellArray[i][col1].D.getType().equals("Boolean")
                        || CellArray[i][col1].D.getType().equals("Time 12H")) {
                    throw new CellTypeMissMatch("Sorting cannot be done with these cell types");
                }

                AL.add(CellArray[i][col1]);
            }

            Collections.sort(AL);

            if (descending) {                       // descending order scenario
                Collections.reverse(AL);
            }

            for (int i = row1; i <= row2; i++) {
                CellArray[i][col1] = AL.get(i - row1);
            }

            TS.getModel().updateData(CellArray);    // update the array
            CellRenderer.renderAll(TS);
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
