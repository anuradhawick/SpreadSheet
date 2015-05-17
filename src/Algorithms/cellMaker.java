
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Algorithms;

//~--- non-JDK imports --------------------------------------------------------

import spreadsheet.Cell;
import spreadsheet.Currency;
import spreadsheet.DateType;
import spreadsheet.FloatingPointValue;
import spreadsheet.IntegerType;
import spreadsheet.TextType;
import spreadsheet.TimeType;

//~--- JDK imports ------------------------------------------------------------

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class cellMaker {
    private cellMaker() {}

    /*
     * this class makes cell using the parser manager and return the cells
     * to be added to the spreadsheet. The cell will have the type original value and the formatted content
     */
    public static Cell getCell(String content) throws ParseException {
        Cell             C = new Cell();
        String           type;
        List<String>     components = parserManager.getParsedItems(content);
        SimpleDateFormat sdf        = new SimpleDateFormat("MM/dd/yyyy");
        Date             date;
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        sdf1.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            type = components.get(0);
        } catch (NullPointerException e) {
            return null;
        }

        if ("".equals(components.get(1))) {
            return null;
        }

        switch (type) {
        case "Boolean" :
            C.D = new TextType();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);

            break;

        case "Text" :
            C.D = new TextType();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);

            break;

        case "Date" :
            C.D = new DateType();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);
            date  = sdf.parse(components.get(1));
            C.val = date.getTime();

            break;

        case "Integer" :
            C.D = new IntegerType();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);
            C.val = Double.parseDouble(components.get(1));

            break;

        case "Float" :
            C.D = new FloatingPointValue();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);
            C.val = Double.parseDouble(components.get(1));

            break;

        case "Time 24H" :
            C.D = new TimeType();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);
            date  = sdf1.parse("1970-01-01 " + components.get(1));
            C.val = date.getTime();

            break;

        case "Time 12H" :
            C.D = new TimeType();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);

            break;

        case "Currency" :
            C.D = new Currency();
            C.D.setFormattedData(components.get(1));
            C.D.setRawData(components.get(2));
            C.D.setType(type);

            try {
                C.val = Double.parseDouble((components.get(3)));
            } catch (ArrayIndexOutOfBoundsException e) {}

            break;
        }

        return C;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
