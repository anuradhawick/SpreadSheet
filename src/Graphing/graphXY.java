
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package Graphing;

//~--- non-JDK imports --------------------------------------------------------

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.JPanel;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class graphXY {
    private final String          name;
    private final String          xLabel;
    private final String          yLabel;
    private final double[]        x;
    private final double[]        y;
    private final boolean         Legend;
    private final boolean         toolTips;
    private final PlotOrientation p;

    public graphXY(String name, String x, String y, double[] X, double[] Y, boolean Legend, PlotOrientation p,
                   boolean toolTips) {
        this.name     = name;
        this.Legend   = Legend;
        this.x        = X;
        this.y        = Y;
        this.xLabel   = x;
        this.yLabel   = y;
        this.p        = p;
        this.toolTips = toolTips;
    }

    // get the jpanel
    public JPanel getXYgraphPanel() {
        XYDataset  dataSet = getDataSet();
        JFreeChart graph   = ChartFactory.createXYLineChart(this.name, this.xLabel, this.yLabel, dataSet, this.p,
                                 this.Legend, this.toolTips, false);

        return new ChartPanel(graph);
    }

    // process the data
    private XYDataset getDataSet() {
        XYSeriesCollection dataSet = new XYSeriesCollection();
        XYSeries           series  = new XYSeries(this.name);

        for (int i = 0; i < this.x.length; i++) {
            series.add(this.x[i], this.y[i]);
        }

        dataSet.addSeries(series);

        return dataSet;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com