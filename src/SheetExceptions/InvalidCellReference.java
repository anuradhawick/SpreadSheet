
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package SheetExceptions;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class InvalidCellReference extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidCellReference(String msg) {
        super(msg);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
