
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
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.JPanel;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class barGraph {
    private final String          bargraph, name, X, Y;
    private final boolean         toolTip, Legend;
    private final String[]        category;
    private final double[]        data;
    private final PlotOrientation p;

    public barGraph(String name, String bargraph, String x, String y, String[] category, double[] data,
                    PlotOrientation p, boolean legend, boolean tooltip) {
        this.name     = name;
        this.X        = x;
        this.Y        = y;
        this.bargraph = bargraph;
        this.p        = p;
        this.toolTip  = tooltip;
        this.Legend   = legend;
        this.category = category;
        this.data     = data;
    }

    // making the jpanel
    public JPanel getBarGraph() {
        CategoryDataset dataSet = getDataSet();
        JFreeChart      chart   = ChartFactory.createBarChart(this.name, this.X, this.Y, dataSet, this.p, this.Legend,
                                      this.toolTip, false);

        return new ChartPanel(chart);
    }

    // get process the data set
    private CategoryDataset getDataSet() {
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();

        for (int i = 0; i < this.category.length; i++) {
            dataSet.addValue(this.data[i], this.bargraph, this.category[i]);
        }

        return dataSet;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com