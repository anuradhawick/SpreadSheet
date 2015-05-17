
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import GUI.TableSet;

import SheetExceptions.InvalidCellReference;
import SheetExceptions.NullCell;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class CellReferenceStringMaker {
    private CellReferenceStringMaker() {}

    public static String getDataString(String in) throws NullCell, InvalidCellReference {
        if (in.charAt(0) == '=') {
            in = in.substring(1);
        }

        String[] s = null;

        s = in.split("([+|[-]|*|/|(|)])|(((=?[<>]=?)|(!=)|<>|><)|==|=)");

        String[]          cellID;
        ArrayList<String> out    = new ArrayList<String>();
        String            temp,
                          output = "";
        Object            cell;
        int               row, col;

        // selectedCell = TableSet.SpreadTable.getColumnName(TableSet.SpreadTable.getSelectedColumn()) + (TableSet.SpreadTable.getSelectedRow() + 1);
        for (String i : s) {
            if (i.matches("[A-Z][A-Z]*[0-9][0-9]*")) {
                cellID = i.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");
                row    = Integer.parseInt(cellID[1]) - 1;

                if (cellID[0].toLowerCase().equals(cellID[0])) {
                    throw new InvalidCellReference("Invalid cell reference");
                }

                col  = colConv(cellID[0]) - 1;
                cell = TableSet.SpreadTable.getValueAt(row, col);

                if ((cell == null) || cell.toString().equals("")) {
                    temp = "0";
                } else {
                    temp = cell.toString();
                    out.add(temp);
                }
            } else {
                if (!i.equals("")) {
                    out.add(i);
                }
            }
        }

        /*
         * remaking the string for calculation with the values refered by the Cell references
         */
        int j = 0;

        for (int i = 0; i < in.length() - 1; i++) {
            if ((in.charAt(i) + "").matches("[><_+!=()/*[-]]")) {
                output += in.charAt(i);
            } else if (!(in.charAt(i) + "").matches("[><_()+=/*![-]]")
                       && (in.charAt(i + 1) + "").matches("[><_!()+=/*[-]]")) {
                output += out.get(j);
                j++;
            }
        }

        if ((in.charAt(in.length() - 1) + "").matches("[><_()+=/*[-]]")) {
            output += in.charAt(in.length() - 1);
        }

        if (out.size() == j + 1) {
            output = output + out.get(j);
        }

        output = "=" + output;

        /*  */
        return output;
    }

    // converting alphabetic reference of index to numeric figures
    public synchronized static int colConv(String Col) {
        String alpha  = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int    i, j,
               result = 0;

        for (i = 0, j = Col.length() - 1; i < Col.length(); i++, j--) {
            result += Math.pow(26, j) * (alpha.indexOf(Col.charAt(i)) + 1);
        }

        return result;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
