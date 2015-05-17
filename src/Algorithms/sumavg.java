
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import GUI.TableSet;

import SheetExceptions.InvalidOperation;
import SheetExceptions.NullCell;
import SheetExceptions.RangeError;

import expEval.StringParser;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class sumavg {
    private sumavg() {}

    public static String getSum(String start, String end, TableSet TS) throws RangeError, InvalidOperation, NullCell {
        String[] s = start.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
        String[] e = end.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
        int      row1, col1, row2, col2;
        String   answer;

        row1 = Integer.parseInt(s[1]) - 1;
        row2 = Integer.parseInt(e[1]) - 1;
        col1 = CellReferenceStringMaker.colConv(s[0]) - 1;
        col2 = CellReferenceStringMaker.colConv(e[0]) - 1;

        double sum = 0;

        if ((row1 > TS.getModel().getRowCount() - 1) || (row2 > TS.getModel().getRowCount() - 1)
                || (col1 > TS.getModel().getColumnCount() - 1) || (col2 > TS.getModel().getColumnCount() - 1)) {
            throw new RangeError("Invalid range");
        } else if ((col1 > col2) || (row1 > row2)) {
            throw new RangeError("Invalid range");
        }

        for (int i = row1; i <= row2; i++) {
            for (int j = col1; j <= col2; j++) {
                if (TS.getModel().getData()[i][j] == null) {
                    throw new NullCell("Contain empty cells");
                } else if (!TS.getModel().getData()[i][j].D.getType().equals("Integer")
                           &&!TS.getModel().getData()[i][j].D.getType().equals("Float")) {
                    throw new InvalidOperation("Invalid operation");
                }

                sum += Double.parseDouble(TS.getModel().getData()[i][j].D.getFormattedData());
            }
        }

        answer = StringParser.getList(String.valueOf(sum)).get(1);

        return answer;
    }

    public static String getAvg(String start, String end, TableSet TS) throws RangeError, InvalidOperation, NullCell {
        String   in = getSum(start, end, TS);
        String[] s  = start.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
        String[] e  = end.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
        int      row1, col1, row2, col2;
        String   answer;

        row1 = Integer.parseInt(s[1]) - 1;
        row2 = Integer.parseInt(e[1]) - 1;
        col1 = CellReferenceStringMaker.colConv(s[0]) - 1;
        col2 = CellReferenceStringMaker.colConv(e[0]) - 1;

        int    i = (row2 - row1 + 1) * (col2 - col1 + 1);
        double j = Double.parseDouble(in) / i;

        return StringParser.getList(String.valueOf(j)).get(1);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
