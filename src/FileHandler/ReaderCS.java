
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package FileHandler;

//~--- non-JDK imports --------------------------------------------------------

import GUI.CellRenderer;
import GUI.SpreadSheetUI;
import GUI.TableModel;
import GUI.TableSet;

import com.opencsv.CSVReader;

import spreadsheet.Cell;
import spreadsheet.TextType;

//~--- JDK imports ------------------------------------------------------------


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class ReaderCS implements Runnable {
    private static final long serialVersionUID = 1L;
    private final TableModel  TB               = new TableModel();
    private final TableModel  TM;
    private final TableSet    TS;

    public ReaderCS(TableSet TS) {
        this.TS = TS;
        this.TM = TS.getModel();
    }

    @Override
    public void run() {
        FileReader fr = null;

        try {
            JFileChooser fc = new JFileChooser();

            fc.setAcceptAllFileFilterUsed(false);
            fc.addChoosableFileFilter(new csvFileFilter());

            int val = fc.showOpenDialog(null);

            if (val == JFileChooser.APPROVE_OPTION) {
                File F = fc.getSelectedFile();

                SpreadSheetUI.F = F;
                fr              = new FileReader(F);
                ReadUpdate(fr);
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File not found. Check the path and try again", "Open",
                                          JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(ReaderCS.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fr.close();
            } catch (IOException ex) {

                // Do nothing
            } catch (NullPointerException ex) {

                // Do nothing
            }
        }
    }

    public void ReadUpdate(FileReader F) throws IOException {
        CSVReader cvsr = new CSVReader(F);
        String[]  line;
        int       row = 0;

        // TableSet.SpreadTable.editingCanceled(null);
        TS.getTable().editingCanceled(null);

        for (int i = 0; i < TS.getTable().getRowCount(); i++) {
            for (int j = 0; j < TS.getTable().getColumnCount(); j++) {
                TB.setValueAt(null, i, j);
            }
        }

        TS.getTable().clearSelection();

        while ((line = cvsr.readNext()) != null) {
            if (line.length > 100) {
                JOptionPane.showMessageDialog(null, "File trying to read is too large. Max supported is 100x100",
                                              "Open", JOptionPane.ERROR_MESSAGE);

                return;
            }

            for (int i = 0; i < line.length; i++) {
                Cell C = new Cell();

                C.D = new TextType();

                if (line[i].matches("[ ]")) {
                    TB.setCell(C, row, i);
                } else {
                    C.D.setRawData(line[i]);
                    TB.setCell(C, row, i);
                }
            }

            row++;
        }

        TM.updateData(TB.getData());
        CellRenderer.renderAll(TS);    // rerender the cells since the table is new
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
