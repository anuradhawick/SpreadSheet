
/*
* This is the panel which contains the table and the first column of row ID's
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------

import spreadsheet.Cell;

//~--- JDK imports ------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultCellEditor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class TableSet extends JPanel {
    public static TableModel  TM               = new TableModel();
    public static JTable      SpreadTable      = new JTable(TM);
    public static freezedCol  FC               = new freezedCol();
    public static JTable      fixedTable       = new JTable(FC);
    private static final long serialVersionUID = 1L;

    public TableSet() {
        super(new GridLayout(1, 0));
        setLayout(new BorderLayout());
        SpreadTable.setCellSelectionEnabled(true);
        SpreadTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        SpreadTable.setRowHeight(20);
        SpreadTable.setPreferredScrollableViewportSize(new Dimension(500, 70));
        SpreadTable.setFillsViewportHeight(true);
        SpreadTable.getTableHeader().setReorderingAllowed(false);
        SpreadTable.setShowGrid(true);

        for (int i = 0; i < SpreadTable.getColumnCount(); i++) {
            SpreadTable.getColumnModel().getColumn(i).setCellRenderer(new CellRenderer());
        }

        fixedTable.getColumnModel().getColumn(0).setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component component = table.getTableHeader().getDefaultRenderer().getTableCellRendererComponent(table,
                                          value, false, false, -1, -2);

                ((DefaultTableCellRenderer) component).setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

                return component;
            }
        });
        fixedTable.getTableHeader().setReorderingAllowed(false);
        fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        fixedTable.setRowHeight(20);
        fixedTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        fixedTable.getColumnModel().getColumn(0).setResizable(false);

        // the below code make a selection model to scroll together
        ListSelectionModel model = fixedTable.getSelectionModel();

        SpreadTable.setSelectionModel(model);

        // adding the scroll pane to the main table SpreadTable
        JScrollPane scrollPane = new JScrollPane(SpreadTable);
        Dimension   fixedSize  = fixedTable.getPreferredSize();
        JViewport   viewport   = new JViewport();

        viewport.setView(fixedTable);
        viewport.setPreferredSize(fixedSize);
        viewport.setMaximumSize(fixedSize);
        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable.getTableHeader());
        scrollPane.setRowHeaderView(viewport);

        // listner for the table
        ((JTextField) ((DefaultCellEditor) SpreadTable.getDefaultEditor(
            Object.class)).getComponent()).getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent de) {
                try {
                    String   s     = de.getDocument().getText(0, de.getDocument().getLength());
                    String[] Array = s.split("[><_()+=/*!-]");
                    Thread   T     = new Thread(new CellHighLighter(Array, SpreadSheetUI.tableSet1));
                    T.start();
                    T.join();
                    
                    updateFx(s);
                } catch (BadLocationException | InterruptedException ex) {
                    Logger.getLogger(TableSet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            @Override
            public void removeUpdate(DocumentEvent de) {
                try {
                    String   s     = de.getDocument().getText(0, de.getDocument().getLength());
                    String[] Array = s.split("[><_()+=/*!-]");
                    Thread   T     = new Thread(new CellHighLighter(Array, SpreadSheetUI.tableSet1));
                    T.start();
                    T.join();
                    updateFx(s);
                } catch (BadLocationException | InterruptedException ex) {
                    Logger.getLogger(TableSet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            @Override
            public void changedUpdate(DocumentEvent de) {}
        });

        // key adapter for the jTable
        SpreadTable.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    TextBoxSet.fxText.setText("");

                    Thread T1 = new Thread(new CellHighLighter(new String[0], SpreadSheetUI.tableSet1));

                    try {
                        T1.start();
                        T1.join();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(TableSet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if ((ke.getKeyCode() == KeyEvent.VK_RIGHT) || (ke.getKeyCode() == KeyEvent.VK_LEFT)
                           || (ke.getKeyCode() == KeyEvent.VK_UP) || (ke.getKeyCode() == KeyEvent.VK_DOWN)) {
                    dataProcess();
                }
            }
        });

        // Mouse adapter for the jTable
        SpreadTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                dataProcess();
            }
        });
        add(scrollPane);
    }

    // this is the code setup for upfating the label at right corner to indicate the data type and the cell ID
    public static void setLabel(String s) {
        int    row = SpreadTable.getSelectedRow();
        int    col = SpreadTable.getSelectedColumn();
        String ID;

        ID = SpreadTable.getColumnName(col);
        ID += String.valueOf(row + 1);

        if (s != null) {
            ID = s + " " + ID;
        }

        TextBoxSet.dataLabel.setText(ID);
    }

    public void setLabel() {
        int    row = SpreadTable.getSelectedRow();
        int    col = SpreadTable.getSelectedColumn();
        String ID;

        ID = SpreadTable.getColumnName(col);
        ID += String.valueOf(row + 1);
        TextBoxSet.dataLabel.setText(ID);
    }

    // the function to update the datalabels and textboxes
    public void dataProcess() {
        int    i = SpreadTable.getSelectedRow();
        int    j = SpreadTable.getSelectedColumn();
        String s;

        setLabel();

        try {
            TextBoxSet.fxText.setText(TableModel.sheet[i][j].D.getRawData());
            SpreadSheetUI.jComboBox1.setSelectedItem(TableModel.sheet[i][j].getFont());
            SpreadSheetUI.jComboBox2.setSelectedItem(TableModel.sheet[i][j].getSize());
            s = TableModel.sheet[i][j].D.getType();
            setLabel(s);
        } catch (NullPointerException e) {
            setLabel();
            TextBoxSet.fxText.setText("");
        }
    }

    public void updateFx(String s) {
        TextBoxSet.fxText.setText(s);
    }

    public TableModel getModel() {
        return TM;
    }

    public JTable getTable() {
        return SpreadTable;
    }

    public void clearTable() {
        TM.updateData(new Cell[100][100]);
    }

    public void updateCurrentCell(String s) {
        int i = SpreadTable.getSelectedRow();
        int j = SpreadTable.getSelectedColumn();

        TM.setValueAt(s, i, j);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
