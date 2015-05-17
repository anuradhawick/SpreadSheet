
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import GUI.TableSet;

import SheetExceptions.CellTypeMissMatch;
import SheetExceptions.InvalidCellReference;
import SheetExceptions.InvalidOperation;
import SheetExceptions.NullCell;

import expEval.StringParser;
import expEval.expressions;

//~--- JDK imports ------------------------------------------------------------

import java.util.List;

import javax.swing.JOptionPane;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class parserManager {
    private parserManager() {}

    public static List<String> getParsedItems(String cellValue) {
        List<String> list;
        String       temp;
        int          i            = TableSet.SpreadTable.getSelectedRow();
        int          j            = TableSet.SpreadTable.getSelectedColumn();
        String       selectedCell = TableSet.SpreadTable.getColumnName(j) + (i + 1);    // reference name of selected Cell

        if ((cellValue == null) || cellValue.equals("")) {
            return null;
        } else if (cellValue.charAt(0) == '=') {
            if (!cellValue.toLowerCase().equals(cellValue.toUpperCase())) {
                try {
                    temp = CellReferenceStringMaker.getDataString(cellValue);

                    try {
                        equationValidator.validate(temp);
                    } catch (InvalidOperation ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid operation",
                                                      JOptionPane.WARNING_MESSAGE);

                        return null;
                    } catch (CellTypeMissMatch ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Data type error",
                                                      JOptionPane.WARNING_MESSAGE);

                        return null;
                    }

                    // if the returned string is an inequality
                    if (cellValue.contains(">") || cellValue.contains(">=") || cellValue.contains("<=")
                            || cellValue.contains("<") || cellValue.contains("=>") || cellValue.contains("=<")) {
                        list = StringParser.getList(temp);
                    } else {
                        list = StringParser.getList(expressions.giveAnswer(temp));
                    }

                    list.remove(2);
                    list.add(cellValue);

                    return list;
                } catch (NullCell ex) {
                    JOptionPane.showMessageDialog(null, "Cell values entered contains null areas", "Null cell error",
                                                  JOptionPane.WARNING_MESSAGE);

                    return null;
                } catch (InvalidCellReference | StackOverflowError ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Cell ID error", JOptionPane.WARNING_MESSAGE);

                    return null;
                }
            } else {
                if (cellValue.substring(1).contains(">") || cellValue.substring(1).contains(">=")
                        || cellValue.substring(1).contains("<=") || cellValue.substring(1).contains("<")
                        || cellValue.substring(1).contains("=>") || cellValue.substring(1).contains("=<")
                        || cellValue.substring(1).contains("=")) {
                    list = StringParser.getList(cellValue);
                } else {
                    list = StringParser.getList(expressions.giveAnswer(cellValue));
                }

                list.remove(2);
                list.add(cellValue);

                return list;
            }
        } else {
            list = StringParser.getList(cellValue);
        }

        // this list will have data type, parsed value and the original figure
        return list;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
