
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import GUI.TableSet;

import spreadsheet.Cell;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class CellSearch implements Runnable {
    private final TableSet TS;
    private final String   match;

    public CellSearch(TableSet TS, String match) {
        this.TS    = TS;
        this.match = match;
    }

    @Override
    public void run() {

        // The object search for the cells that contains values that match the inquirey and make their font color to red
        Cell[][] C = TS.getModel().getData();

        for (Cell[] C1 : C) {
            for (Cell C2 : C1) {
                try {
                    if ((C2 != null) &&!match.equals("")
                            && (C2.D.getFormattedData().contains(match) || C2.D.getRawData().contains(match))) {
                        C2.setColor("red");
                    } else if (C2 != null) {
                        C2.setColor("white");
                    }
                } catch (Exception e) {}
            }
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
