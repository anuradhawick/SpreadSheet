
/*
* The panel that hold the equation bar and the row col display
 */
package GUI;

//~--- non-JDK imports --------------------------------------------------------

import Algorithms.CellReferenceStringMaker;

import expEval.StringParser;

import static GUI.TableSet.SpreadTable;

//~--- JDK imports ------------------------------------------------------------

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class TextBoxSet extends JPanel {
    private static final long serialVersionUID = 1L;
    public static boolean     editing          = false;
    public static JTextField  fxText;
    public static JTextField  dataLabel;
    public static JLabel      typeLabel;
    private final JLabel      fxLabel;

    public TextBoxSet() {
        fxLabel = new JLabel("  f(x)  ");    // label saying
        fxLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        fxText    = new JTextField();        // textbox to enter or see data
        dataLabel = new JTextField("A1");    // to display the row and column

        Dimension D = new Dimension(100, 14);

        dataLabel.setPreferredSize(D);
        dataLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        setLayout(new BorderLayout());
        add(fxLabel, BorderLayout.WEST);
        fxText.setPreferredSize(new Dimension(400, 14));

        JPanel textBox = new JPanel(new BorderLayout());

        textBox.add(fxText, BorderLayout.WEST);
        add(textBox);
        add(dataLabel, BorderLayout.EAST);

        // key adapter for datalabel
        dataLabel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                String   s;
                String[] temp;

                if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                    s    = dataLabel.getText();
                    temp = s.split("[^A-Z0-9]+|(?<=[A-Z])(?=[0-9])|(?<=[0-9])(?=[A-Z])");

                    int col = CellReferenceStringMaker.colConv(temp[0]) - 1;
                    int row = Integer.parseInt(temp[1]) - 1;

                    if ((col > SpreadTable.getColumnCount()) || (row > SpreadTable.getRowCount()) || (col < 0)
                            || (row < 0) ||!s.matches("[A-Z]*[0-9]*")) {
                        dataLabel.setText("");
                        JOptionPane.showMessageDialog(null, "Invalid cell selections", "Error", JOptionPane.ERROR);
                    } else {
                        SpreadTable.changeSelection(row, col, false, false);

                        try {
                            fxText.setText(((TableModel) SpreadTable.getModel()).getData()[row][col].D.getRawData());
                        } catch (NullPointerException e) {
                            fxText.setText("");
                        }
                    }
                }

                editing = false;
            }
        });
        dataLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dataLabel.setText("");
                editing = true;
            }
        });

        // focus listener
        fxText.addFocusListener(new FocusListener() {
            @Override
            public void focusLost(FocusEvent e) {

                // highliting the referred cells
                Thread T1 = new Thread(new CellHighLighter(new String[0], SpreadSheetUI.tableSet1));

                try {
                    T1.start();
                    T1.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TextBoxSet.class.getName()).log(Level.SEVERE, null, ex);
                }

                editing = false;
            }
            @Override
            public void focusGained(FocusEvent e) {
                String[] Array = fxText.getText().split("[><_()+=/*!-]");
                Thread   T     = new Thread(new CellHighLighter(Array, SpreadSheetUI.tableSet1));

                try {

                    // highlighting referred cells
                    T.start();
                    T.join();
                } catch (InterruptedException ex) {
                    Logger.getLogger(TextBoxSet.class.getName()).log(Level.SEVERE, null, ex);
                }

                editing = true;
            }
        });

        // Key adapter for the fxText
        fxText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                editing = true;

                /*
                 * records the keys being released and update the table UI to make them appear realtime
                 */
                SpreadTable.editingCanceled(null);

                try {
                    if ((fxText.getText() == null) || fxText.getText().equals("")) {
                        SpreadTable.setValueAt(null, TableSet.SpreadTable.getSelectedRow(),
                                               TableSet.SpreadTable.getSelectedColumn());
                    } else if (fxText.getText().charAt(0) != '=') {
                        TableSet.SpreadTable.setValueAt(fxText.getText(), TableSet.SpreadTable.getSelectedRow(),
                                                        TableSet.SpreadTable.getSelectedColumn());
                        TableSet.setLabel(StringParser.getList(fxText.getText()).get(0));
                    } else {
                        String[] Array = fxText.getText().split("[><_()+=/*!-]");

                        // Thread to highlight the refered cells
                        Thread T = new Thread(new CellHighLighter(Array, SpreadSheetUI.tableSet1));
                        T.start();
                        T.join();
                    }

                    if (ke.getKeyCode() == KeyEvent.VK_ENTER) {
                        TableSet.SpreadTable.setValueAt(fxText.getText(), TableSet.SpreadTable.getSelectedRow(),
                                                        TableSet.SpreadTable.getSelectedColumn());
                        fxText.setText("");
                        editing = false;

                        Thread T1 = new Thread(new CellHighLighter(new String[0], SpreadSheetUI.tableSet1));
                        T1.start();
                        T1.join();
                        
                    } else {}
                } catch (NumberFormatException e) {

                    // Do nothing
                } catch (InterruptedException ex) {

                    // Do nothing
                }
            }
        });
    }
}


//~ Formatted by Jindent --- http://www.jindent.com