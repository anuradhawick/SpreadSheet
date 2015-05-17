
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package FileHandler;

//~--- non-JDK imports --------------------------------------------------------

import GUI.SpreadSheetUI;
import GUI.TableSet;
import com.opencsv.CSVWriter;
import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class saverCS implements Runnable {
    private Cell[][] Data;
    private TableSet TS;
    private boolean  SaveAs;

    public saverCS(TableSet TS, boolean SaveAs) {
        TS.getTable().editingCanceled(null);
        TS.getTable().clearSelection();
        Data        = TS.getModel().getData().clone();    // obtains a copy so that user will not be able to modify it
        this.TS     = TS;
        this.SaveAs = SaveAs;
    }

    @Override
    public void run() throws NullPointerException {
        String[]     s;
        JFileChooser fc = new JFileChooser();

        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(new csvFileFilter());

        int val;

        if (SaveAs || (SpreadSheetUI.F == null)) {
            val = fc.showSaveDialog(null);
        } else {
            val = JFileChooser.APPROVE_OPTION;
        }

        try {
            if (val == JFileChooser.APPROVE_OPTION) {
                File F;

                if (SaveAs || (SpreadSheetUI.F == null)) {
                    F = new File(fc.getSelectedFile().toString() + ".csv");
                    F.createNewFile();
                    SpreadSheetUI.F = F;
                } else {
                    F = SpreadSheetUI.F;
                    F.createNewFile();
                }

                CSVWriter csvw = new CSVWriter(new FileWriter(F));

                // checking for the active region
                for (Cell[] Data1 : Data) {
                    s = new String[Data.length];

                    for (int j = 0; j < Data[0].length; j++) {
                        if (Data1[j] == null) {
                            s[j] = " ";
                        } else {
                            s[j] = Data1[j].D.getRawData();
                        }
                    }

                    csvw.writeNext(s);
                }

                csvw.close();
                JOptionPane.showMessageDialog(null, "File saved successfully to file " + F.getName(), "Save",
                                              JOptionPane.INFORMATION_MESSAGE);
            } else {}
        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "The file could not be created, select another place", "Error",
                                          JOptionPane.ERROR_MESSAGE);
        } finally {}
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
