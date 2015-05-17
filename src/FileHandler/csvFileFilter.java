
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package FileHandler;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class csvFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String[] l = f.getName().split("[.]");

        if (l[l.length - 1].toLowerCase().equals("csv")) {
            return true;
        }

        return false;
    }

    @Override
    public String getDescription() {
        return "Comma delimetered file (*.csv)";
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
