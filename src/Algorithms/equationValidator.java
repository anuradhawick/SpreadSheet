
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import SheetExceptions.CellTypeMissMatch;
import SheetExceptions.InvalidOperation;

import expEval.StringParser;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class equationValidator {
    private equationValidator() {}

    public static String validate(String in) throws InvalidOperation, CellTypeMissMatch {

        // valid regex patterns
        String   time12hr       = "(1[012]|[1-9]):[0-5][0-9]?[:]?[0-5][0-9]?(am|AM|pm|PM)";
        String   time24hr       = "(2[0-4]|1[0-9]|[0-9]):[0-5][0-9]?[:]?[0-5][0-9]?";
        String   dateformatSL   = "[0-9]{2,4}[/-](1[012]|0?[0-9])[/-](1[0-9]|0?[0-9]|2[0-9]|3[01])";
        String   dateformatINTL = "(1[0-9]|0?[0-9]|2[0-9]|3[01])[/-](1[012]|0?[0-9])[/-][0-9]{2,4}";
        String[] items          = in.substring(1).split("[+|*|(|)|[-]]");

        // detects if the user has entered cells having fields that are uncapable of arithmatics
        for (String i : items) {
            if (i.matches(dateformatINTL) || i.matches(dateformatSL) || i.matches(time12hr) || i.matches(time24hr)) {
                throw new InvalidOperation("Invalid operation on operands dates/times");
            }
        }

        items = in.substring(1).split("[+|[-]|*|/|(|)]");

        String type = null;

        // recognize boolean expressions
        if (in.matches(".*?(((=?[<>])|(!=)|([<>]=?)|<>|><)|==|=).*?")) {
            return "Boolean";
        }

        for (String i : items) {
            if (type == null) {
                type = StringParser.getList(i).get(0);
            } else if ((!StringParser.getList(i).get(0).equals("Integer")
                        &&!StringParser.getList(i).get(0).equals("Float"))) {
                throw new CellTypeMissMatch("Invalid types found");
            }
        }

        return type;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
