package expEval;

//~--- JDK imports ------------------------------------------------------------
import java.util.*;

public class expressions {
    private static boolean userVerify = false;

    private expressions() {}

    public static String giveAnswer(String S) {
        double answer = 0;

        // seperatig the elements in to tockens
        String Str;

        try {
            Str = purify(S);
        } catch (NumberFormatException e) {
            return "#ERR?";
        }

        Str = validate(Str);

        List<String> value;

        if (S.charAt(0) == '=') {
            value = seperator(Str.substring(1));
        } else {
            value = seperator(Str);
        }

        int leftbr  = 0;
        int rightbr = 0;

        for (int i = 0; i < Str.length(); i++) {
            if (!(Str.charAt(i) + "").matches("[0-9]|[/*+=()^.]|[-]")) {
                return "#NAME?";
            }

            if ((Str.charAt(i) + "").matches("[(]")) {
                leftbr++;
            } else if ((Str.charAt(i) + "").matches("[)]")) {
                rightbr++;
            }
        }

        if (leftbr != rightbr) {
            return "#EQN-ERR";
        }

        try {
            answer = bracketRemove(value);
        } catch (NumberFormatException e) {
            return "#ERR?";
        } catch (EmptyStackException e) {
            return "#ERR?";
        }

        value.clear();

        return String.valueOf(answer);
    }

    /*
     * this function use a stack to evaluate the multiplications and divisions
     * once multiplication is found it is popped out of the stack and multiplied with the proceeding element
     * and the answered is pushed backed to the array once again
     * the final array is returned (ir is passed to solve additions and subtractions directly to the next function)
     */

    /*
     * The calculation is done by passing the string list in to the bracket solver
     * In the bracket solver the ones inside the brackets are taken away and sent to simplify
     * From the function to solve * and / i the simplified array is sent to simplify + and -
     * the final answer is returned and pushed back to the stack
     * the above two steps happen over again once all brackets are removed
     */
    static double odmSolver(List<String> L) {
        Stack<String> solved = new Stack<String>();
        double        num1, num2;

        for (int i = 0; i < L.size(); i++) {

            // Modulus division
            if ("%".equals(L.get(i))) {
                num1 = Double.parseDouble(solved.pop());
                i++;
                num2 = Double.parseDouble(L.get(i));
                solved.push(String.valueOf(num1 % num2));
            }    // solving exponentiation
                    else if ("^".equals(L.get(i))) {
                num1 = Double.parseDouble(solved.pop());
                i++;
                num2 = Double.parseDouble(L.get(i));
                solved.push(String.valueOf(Math.pow(num1, num2)));
            }    // solving multiplications
                    else if ("*".equals(L.get(i))) {
                num1 = Double.parseDouble(solved.pop());
                i++;
                num2 = Double.parseDouble(L.get(i));
                solved.push(String.valueOf(num1 * num2));
            }    // solving divisions
                    else if ("/".equals(L.get(i))) {
                num1 = Double.parseDouble(solved.pop());
                i++;
                num2 = Double.parseDouble(L.get(i));
                solved.push(String.valueOf(num1 / num2));
            } else {

                // if they are additions or subtractions they are just added to the stack
                solved.push(L.get(i));
            }
        }

        // this return the answer after simplifying + and -
        return asSolver(solved);
    }

    // function to simplify additions and subtractions within a stack
    static double asSolver(List<String> S) {
        double Answer = 0;
        int    i      = 0;

        try {
            Answer = Double.parseDouble(S.get(0));
            i      = 1;
        } catch (Exception e) {
            i = 0;
        }

        for (i = i; i < S.size(); i++) {
            if ("+".equals(S.get(i))) {
                Answer += 1 * (Double.parseDouble(S.get(i + 1)));
                i++;
            } else if ("-".equals(S.get(i))) {
                Answer += -1 * (Double.parseDouble(S.get(i + 1)));
                i++;
            }
        }

        return Answer;
    }

    // function to seperate the string into the required components

    /**
     *
     * @param str string to be separated in to tokens for simplification
     * @return
     */
        public static List<String> seperator(String str) {

        // The array that will carry the parameters that are seperated
        List<String> ListOut = new ArrayList<String>();

        /*
         * The values here are all saved in the
         * string format and are expected to convert as and when
         * required in the bracked reduction
         */
        String tempStr = "";

        for (int i = 0; i < str.length(); i++) {
            if ((str.charAt(i) == '(') || (str.charAt(i) == ')') || (str.charAt(i) == '*') || (str.charAt(i) == '/')
                    || (str.charAt(i) == '+') || (str.charAt(i) == '-') || (str.charAt(i) == '^')
                    || (str.charAt(i) == '%')) {
                if (tempStr != "") {
                    ListOut.add(tempStr);
                }

                tempStr = "" + String.valueOf(str.charAt(i));
                ListOut.add(tempStr);
                tempStr = "";
            } else {
                tempStr += String.valueOf(str.charAt(i));
            }
        }

        ListOut.add(tempStr);

        return ListOut;
    }

    static double bracketRemove(List<String> parameters) {
        Stack<String> values = new Stack<String>();
        String        iterator;

        // reducing the bracketed items according to the bodmans using a stack
        // the bracketed items will be reduced by back simplifying once we find the close bracket symbol
        for (int i = 0; i < parameters.size(); i++) {

            /*
             * If the closing bracket is found the stack is popped until the opening bracket corresponding to
             * to that particular closing bracket is met the whole thing inside them will be simplified.
             * The final value after simplifying the multiplications will be added to the Ftemp array
             */
            if (!parameters.get(i).equals(")")) {
                values.push(parameters.get(i).toString());
            }                    // if a closing bracket is found the following operation is done
                    else {
                if (values.empty()) {
                    continue;    // in case the stack is empty this should happen
                }    // a new temporary array, the ones inside this array are the ones that are inside the brackets

                List<String> temp = new ArrayList<String>();

                while (true) {
                    iterator = values.pop();

                    if (iterator.equals("(")) {
                        break;
                    }

                    temp.add(iterator);
                }

                // at this point the entire thing which was inside the bracket is removed
                // What is required at this moment is to get the simplified dinal answer and push it into the stack
                Collections.reverse(temp);
                values.push(String.valueOf(odmSolver(temp)));
                temp.clear();
            }
        }

        return odmSolver(values);
    }

    // this function will purify the input string and look for possible errors
    private static String purify(String in) {
        String str = in;

        str = str.replace(")(", ")*(");
        str = str.replace("++", "+");
        str = str.replace("--", "+");
        str = str.replace("+-", "-");
        str = str.replace("-+", "-");
        str = str.replace("//", "*");
        str = str.replace("**", "^");
        str = str.replace("()", "(0)");

        if (str.contains("/-") || str.contains("/+") || str.contains("-/") || str.contains("+/") || str.contains("*+")
                || str.contains("*-") || str.contains("/*") || str.contains("*/")) {
            throw new NumberFormatException();
        }

        return str;
    }

    static String validate(String s) {
        char   prev = 0;
        String out  = "";

        for (int i = 0; (i < s.length()) && (s.length() != 1); i++) {

            // System.out.println(i);
            if (i == 0) {
                prev = s.charAt(i);
                out  += prev;
            } else if (i < s.length() - 1) {
                prev = s.charAt(i - 1);

                if ((prev + "").matches("[0-9]*") && (s.charAt(i) == '(')) {
                    out        += "*" + String.valueOf(s.charAt(i));
                    userVerify = true;
                } else {
                    out += String.valueOf(s.charAt(i));
                }
            }
        }

        out += s.charAt(s.length() - 1);

        return out;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
