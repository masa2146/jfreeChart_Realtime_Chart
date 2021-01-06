package jfree.test;

/**
 * @author Fatih
 */
//RealTimeChart .java
import jfree.FastChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.SamplingXYLineRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RealTimeChart extends FastChartPanel implements Runnable
{
    private static TimeSeries timeSeries;
    private long value=0;

    public RealTimeChart(String chartContent,String title,String yaxisName)
    {
        super(createChart(chartContent,title,yaxisName));
    }

    private static JFreeChart createChart(String chartContent,String title,String yaxisName){
        // Create a sequence diagram object
                timeSeries = new TimeSeries(chartContent,Millisecond.class);
        TimeSeriesCollection timeseriescollection = new TimeSeriesCollection(timeSeries);
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(title, "time (seconds)", yaxisName, timeseriescollection, true, true, false);
        XYPlot xyplot = jfreechart.getXYPlot();
        xyplot.setRenderer(new SamplingXYLineRenderer());
                 // ordinate setting
        ValueAxis valueaxis = xyplot.getDomainAxis();
                 // Automatically set the data axis data range
        valueaxis.setAutoRange(true);
                 // Data axis fixed data range 30s
        valueaxis.setFixedAutoRange(30_000D);

        valueaxis = xyplot.getRangeAxis();
        //valueaxis.setRange(0.0D,200D);

        return jfreechart;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                update();
                Thread.sleep(1);
            }
            catch (InterruptedException e)  {   }
        }
    }

    public void update(){
        timeSeries.add(new Millisecond(), randomNum());
        System.out.println(timeSeries.getItemCount());
    }


    private long randomNum()
    {
        return (long)(Math.random()*20+80);
    }
}
