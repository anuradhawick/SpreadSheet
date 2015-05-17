package expEval;

//~--- JDK imports ------------------------------------------------------------

import java.text.*;

import java.util.*;

public class StringParser {
    private StringParser() {}

    /*
     * this class does not allow creating objects and only allows the use of the static method getList
     * it returns a list containing datatype,formatted data string and actual data
     */
    public static List<String> getList(String parseIt) {
        String       type;
        List<String> sections = new ArrayList<String>();

        try {
            sections.clear();

            int    DD, MM, YYYY, hh, mm, ss;
            String string         = parseIt, temp[], marid,
                   returnString   = "";
            String booltype       = ".*?(((=?[<>])|(!=)|([<>]=?)|<>|><)|==|=).*?";
            String currency       = "[A-Za-z$]{1,2}[ .]?[0-9][0-9]*[.]?[0-9]*";
            String time12hr       = "(1[012]|[1-9]):[0-5][0-9]?[:]?[0-5][0-9]?(am|AM|pm|PM)";
            String time24hr       = "(2[0-4]|1[0-9]|[0-9]):[0-5][0-9]?[:]?[0-5][0-9]?";
            String integer        = "[-]?[0-9]*";
            String floating       = "[-]?[0-9]*[.][0-9]*";
            String dateformatSL   = "[0-9]{2,4}[/-](1[012]|0?[0-9])[/-](1[0-9]|0?[0-9]|2[0-9]|3[01])";
            String dateformatINTL = "(1[0-9]|0?[0-9]|2[0-9]|3[01])[/-](1[012]|0?[0-9])[/-][0-9]{2,4}";
            String tempStr,
                   currType       = "";

            // looks for a boolean expression
            if (string.matches(booltype) && (string.charAt(0) == '=')) {
                string = string.substring(1);
                string = cleanBool(string);
                temp   = string.split("(((=?[<>]=?)|(!=)|<>|><)|==|=)");

                if (!(string.toUpperCase().equals(string.toLowerCase())) || (temp.length != 2)) {
                    type = "Text";
                    sections.add(type);
                    sections.add(string);
                    sections.add("=" + string);

                    return sections;
                } else {
                    sections.add("Boolean");

                    String left     = temp[0];
                    String right    = temp[1];
                    double leftVal  = Double.parseDouble(expressions.giveAnswer(left));
                    double rightVal = Double.parseDouble(expressions.giveAnswer(right));

                    // comparing the values with the comparator
                    if (string.contains(">") && (leftVal > rightVal)) {
                        sections.add("True");
                    } else if ((string.contains(">=") || string.contains("=>")) && (leftVal >= rightVal)) {
                        sections.add("True");
                    } else if ((string.contains("<=") || string.contains("=<")) && (leftVal <= rightVal)) {
                        sections.add("True");
                    } else if ((string.contains("<=") || string.contains("=<")) && (leftVal <= rightVal)) {
                        sections.add("True");
                    } else if (string.contains("<") && (leftVal < rightVal)) {
                        sections.add("True");
                    } else if (string.contains(">") && (leftVal > rightVal)) {
                        sections.add("True");
                    } else if ((string.contains("=") || string.contains("==")) && (leftVal == rightVal)) {
                        sections.add("True");
                    } else if ((string.contains("=") || string.contains("==")) && (leftVal == rightVal)) {
                        sections.add("True");
                    } else if ((string.contains("!=") || string.contains("<>") || string.contains("><"))
                               && (leftVal != rightVal)) {
                        sections.add("True");
                    } else {
                        sections.add("False");
                    }

                    sections.add("=" + string);

                    return sections;
                }
            }    // looks for a date
                    else if (string.matches(dateformatINTL)) {
                boolean    DateValid;
                DateFormat D = new SimpleDateFormat("dd/MM/yyyy");

                // checking if the entered data is precise (leap year and date month correlation)
                try {
                    D.setLenient(false);
                    D.parse(string);
                    DateValid = true;
                } catch (ParseException e) {
                    DateValid = false;
                }

                if (DateValid) {
                    temp = string.split("[/-]");

                    // DD = Integer.parseInt(temp[0]);
                    // MM = Integer.parseInt(temp[1]);
                    // YYYY = Integer.parseInt(temp[2]);
                    type = "Date";

                    // creating the date in the proper format
                    if (temp[0].length() == 1) {
                        returnString += "0" + temp[0];
                    } else {
                        returnString += temp[0];
                    }

                    returnString += "/";

                    if (temp[1].length() == 1) {
                        returnString += "0" + temp[1];
                    } else {
                        returnString += temp[1];
                    }

                    returnString += "/";
                    returnString += temp[2];
                    sections.add(type);
                    sections.add(returnString);
                    sections.add(string);

                    // sections.add(DD);
                    // sections.add(MM);
                    // sections.add(YYYY);
                } else {
                    type = "Text";
                    sections.add(type);
                    sections.add(string);
                    sections.add(string);
                }
            }        // looks for date in local format
                    else if (string.matches(dateformatSL)) {
                boolean    DateValid;
                DateFormat D = new SimpleDateFormat("yyyy/MM/dd");

                try {
                    D.setLenient(false);
                    D.parse(string);
                    DateValid = true;
                } catch (ParseException e) {
                    DateValid = false;
                }

                // checking for the valifity of the date
                if (DateValid) {
                    temp = string.split("[/-]");
                    DD   = Integer.parseInt(temp[2]);
                    MM   = Integer.parseInt(temp[1]);
                    YYYY = Integer.parseInt(temp[0]);

                    // creating the date in the proper format
                    if (temp[2].length() == 1) {
                        returnString += "0" + temp[2];
                    } else {
                        returnString += temp[2];
                    }

                    returnString += "/";

                    if (temp[1].length() == 1) {
                        returnString += "0" + temp[1];
                    } else {
                        returnString += temp[1];
                    }

                    returnString += "/";
                    returnString += temp[0];
                    type         = "Date";
                    sections.add(type);
                    sections.add(returnString);
                    sections.add(string);

                    // sections.add(DD);
                    // sections.add(MM);
                    // sections.add(YYYY);
                }    // if date is not a real value the input will be assigned to a text as in excel
                        else {
                    type = "Text";
                    sections.add(type);
                    sections.add(string);
                    sections.add(string);
                }
            }        // looks for time 24hrs
                    else if (string.matches(time24hr)) {
                temp = string.split("[:]");
                hh   = Integer.parseInt(temp[0]);
                mm   = Integer.parseInt(temp[1]);

                if (temp.length == 3) {
                    ss = Integer.parseInt(temp[2]);

                    if (temp[2].length() == 1) {
                        returnString = "0" + temp[2];
                    }

                    returnString = temp[2];
                } else {
                    returnString = "00";
                    ss           = 0;
                }

                if (temp[1].length() == 1) {
                    returnString = "0" + temp[1] + ":" + returnString;
                }

                returnString = temp[1] + ":" + returnString;
                returnString = temp[0] + ":" + returnString;
                type         = "Time 24H";
                sections.add(type);
                sections.add(returnString);
                sections.add(string);

                // sections.add(hh);
                // sections.add(mm);
                // sections.add(ss);
                marid = "t24";

                // sections.add(marid);
            }    // looks for time 12hrs
                    else if (string.matches(time12hr)) {
                tempStr = "";
                marid   = "";

                for (int i = 0; i < string.length(); i++) {
                    if ((string.charAt(i) == 'a') || (string.charAt(i) == 'A')) {
                        marid = "am";

                        break;
                    }

                    if ((string.charAt(i) == 'p') || (string.charAt(i) == 'P')) {
                        marid = "pm";

                        break;
                    } else {
                        tempStr += string.charAt(i);
                    }
                }

                temp = tempStr.split("[:]");
                hh   = Integer.parseInt(temp[0]);
                mm   = Integer.parseInt(temp[1]);

                if (temp.length == 3) {
                    ss = Integer.parseInt(temp[2]);

                    if (temp[2].length() == 1) {
                        returnString = "0" + temp[2];
                    }

                    returnString = temp[2];
                } else {
                    returnString = "00";
                    ss           = 0;
                }

                if (temp[1].length() == 1) {
                    returnString = "0" + temp[1] + ":" + returnString;
                }

                returnString = temp[1] + ":" + returnString;
                returnString = temp[0] + ":" + returnString;
                type         = "Time 12H";
                sections.add(type);
                sections.add(returnString + marid);
                sections.add(string);

                // sections.add(hh);
                // sections.add(mm);
                // sections.add(ss);
                // sections.add(marid);
            }    // checking for the integer values
                    else if (string.matches(integer)) {

                // intVal = Integer.parseInt(string);
                type = "Integer";
                sections.add(type);
                sections.add(string);
                sections.add(string);
            }            // checking for the floating point values
                    else if (string.matches(floating)) {
                if ((int) Double.parseDouble(string) == Double.parseDouble(string)) {
                    type = "Integer";
                    sections.add(type);
                    sections.add(String.valueOf((int) Double.parseDouble(string)));
                    sections.add(string);
                } else {
                    type = "Float";
                    sections.add(type);
                    sections.add(string);
                    sections.add(string);
                }
            }            // checking the match for a currency type

            // only currency with two Symbolic expressions with be taken
            else if (string.matches(currency)) {
                tempStr = "";
                temp    = string.split("[. ]");

                // case of user giving $.50.36
                if (temp.length == 3) {

                    // re building the floating point value
                    if (string.contains(".")) {
                        tempStr  = temp[1] + '.' + temp[2];
                        currType = temp[0];
                    }
                }        // case of the currency being dollars (special ASCII other than A-Za-z regex)
                        else if (temp.length == 2) {
                    if (string.charAt(0) == '$') {
                        currType = "$";

                        for (int i = 1; i < string.length(); i++) {
                            if (string.charAt(i) != ' ') {
                                tempStr += string.charAt(i);
                            }
                        }
                    } else {

                        // building up the currency code
                        for (int i = 0; i < 2; i++) {
                            currType += string.charAt(i);
                        }

                        // building up the final amount of currency
                        for (int i = 2; i < string.length(); i++) {
                            if (string.charAt(i) != ' ') {
                                tempStr += string.charAt(i);
                            }
                        }
                    }
                } else {

                    // in case the user has given in format $ 50.69
                    if (string.charAt(0) == '$') {
                        currType = "$";

                        for (int i = 1; i < string.length(); i++) {
                            if (string.charAt(i) != ' ') {
                                tempStr += string.charAt(i);
                            }
                        }
                    }    // in case the user has given in format Rs 50.69
                            else {
                        for (int i = 0; i < 2; i++) {
                            currType += string.charAt(i);
                        }

                        for (int i = 2; i < string.length(); i++) {

                            // avoids any additional white spaces
                            if (string.charAt(i) != ' ') {
                                tempStr += string.charAt(i);
                            }
                        }
                    }
                }

                type         = "Currency";
                returnString = currType + " " + tempStr;
                sections.add(type);
                sections.add(returnString);
                sections.add(string);
                sections.add(tempStr);

                // sections.add(currType);
            } else {
                type = "Text";
                sections.add(type);
                sections.add(string);
                sections.add(string);
            }

            // handles the exception if the user inputs a blankspace
        } catch (NullPointerException e) {
            type = "";
            sections.add(type);
            sections.add(parseIt);
            sections.add(parseIt);
        } catch (NumberFormatException e) {
            type = "Text";
            sections.add(type);
            sections.add(parseIt);
            sections.add(parseIt);
        }

        return sections;
    }

    public static String cleanBool(String in) {
        in = in.replace("=>=", ">=");
        in = in.replace("=<=", "<=");
        in = in.replace("<<", "<");
        in = in.replace(">>", ">");

        return in;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
