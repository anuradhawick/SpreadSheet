
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
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

//~--- JDK imports ------------------------------------------------------------

import javax.swing.JPanel;

/**
 *
 * @author Anuradha Sanjeewa
 */
public class piChart {
    private final String   name;
    private final String[] labelSet;
    private final double[] values;
    private final boolean  toolTips;
    private final boolean  Legend;

    public piChart(String name, String[] labelSet, double[] values, boolean Legend, boolean toolTips) {
        this.name     = name;
        this.labelSet = labelSet;
        this.values   = values;
        this.Legend   = Legend;
        this.toolTips = toolTips;
    }

    // draw the graph panel
    public JPanel getPiePanel() {
        PieDataset dataSet  = getDataSet();
        JFreeChart pieChart = ChartFactory.createPieChart(this.name, dataSet, this.Legend, this.toolTips, false);

        return new ChartPanel(pieChart);
    }

    // process data
    private PieDataset getDataSet() {
        DefaultPieDataset dataSet = new DefaultPieDataset();

        for (int i = 0; i < this.labelSet.length; i++) {
            dataSet.setValue(this.labelSet[i], this.values[i]);
        }

        return dataSet;
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
