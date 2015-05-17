
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Graphing;

//~--- non-JDK imports --------------------------------------------------------

import GUI.TableSet;

import org.jfree.chart.plot.PlotOrientation;

//~--- JDK imports ------------------------------------------------------------
import javax.swing.JPanel;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class handler {
    private handler() {}

    // process data and forward them to make graph panels
    // 1 - horizontal 2 - vertical
    // 1 - graph 2 - bar 3 - pie
    public static JPanel getChart(String name, String xlabel, String bargraph, String ylabel, int[] x, int[] y,
                                  TableSet TS, boolean legend, boolean toolTips, int type, PlotOrientation p)
            throws NumberFormatException {
        if (type == 1) {
            double X[] = new double[x.length];
            double Y[] = new double[x.length];

            for (int i = 0; i < X.length; i++) {
                X[i] = Double.parseDouble(TS.getModel().getValueAt(x[i], y[0]).toString());
                Y[i] = Double.parseDouble(TS.getModel().getValueAt(x[i], y[1]).toString());
            }

            graphXY XY = new graphXY(name, xlabel, ylabel, X, Y, legend, p, toolTips);

            return XY.getXYgraphPanel();
        } else if (type == 2) {
            String X[] = new String[x.length];
            double Y[] = new double[x.length];

            for (int i = 0; i < X.length; i++) {
                X[i] = TS.getModel().getValueAt(x[i], y[0]).toString();
                Y[i] = Double.parseDouble(TS.getModel().getValueAt(x[i], y[1]).toString());
            }

            barGraph bar = new barGraph(name, bargraph, xlabel, ylabel, X, Y, p, legend, toolTips);

            return bar.getBarGraph();
        } else {
            String X[] = new String[x.length];
            double Y[] = new double[x.length];

            for (int i = 0; i < X.length; i++) {
                X[i] = TS.getModel().getValueAt(x[i], y[0]).toString();
                Y[i] = Double.parseDouble(TS.getModel().getValueAt(x[i], y[1]).toString());
            }

            piChart PC = new piChart(name, X, Y, legend, toolTips);

            return PC.getPiePanel();
        }
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
