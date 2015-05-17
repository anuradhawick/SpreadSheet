
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package spreadsheet;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class BooleanValues extends DataType implements cellMethods {
    String formattedData;

    @Override
    public String getFormattedData() {
        return this.formattedData;
    }

    @Override
    public void setFormattedData(String data) {
        this.formattedData = data;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
