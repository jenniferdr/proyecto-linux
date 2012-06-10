import java.util.Calendar;
import java.util.Date;
import org.jfree.chart.*;
import org.jfree.data.*;
import org.jfree.ui.*;
import org.jfree.data.gantt.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.SymbolAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


        
        
public class ClaseGrafico extends ApplicationFrame {

 
    public ClaseGrafico(String title) {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
        setContentPane(chartPanel);
    }

 
    private static XYPlot createPlot(IntervalXYDataset dataset) {
        DateAxis xAxis = new DateAxis("Date/Time");
        xAxis.setTickMarkPosition(DateTickMarkPosition.END);
        
        SymbolAxis yAxis = new SymbolAxis("Resources", new String[] {"Team A",
                "Team B", "Team C", "Team D", "Team E"});
        yAxis.setGridBandsVisible(false);
        
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer2);
        return plot;
    }


    private static JFreeChart createChart() {
      XYPlot plot=createPlot(createDataset());
      JFreeChart chart = new JFreeChart("EventChart2", plot);
      chart.setBackgroundPaint(Color.white);
      ChartUtilities.applyCurrentTheme(chart);
      try
      {
         ChartUtilities.saveChartAsPNG(new File("test.png"), chart, 400, 300);
      }
      catch(IOException e)
      {
         e.printStackTrace();
      }
      return chart;
    }   

  
    public static JPanel createDemoPanel() {
        return new ChartPanel(createChart());
    }

    
    private static IntervalXYDataset createDataset() {
        XYTaskDataset dataset = new XYTaskDataset(createTasks());
        dataset.setTransposed(true);
        return dataset;
    }

 
    private static TaskSeriesCollection createTasks() {
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        TaskSeries s1 = new TaskSeries("Team A");
        s1.add(new Task("T1a", new Hour(11, new Day())));
        s1.add(new Task("T1b", new Hour(14, new Day())));
        s1.add(new Task("T1c", new Hour(16, new Day())));
        TaskSeries s2 = new TaskSeries("Team B");
        s2.add(new Task("T2a", new Hour(13, new Day())));
        s2.add(new Task("T2b", new Hour(19, new Day())));
        s2.add(new Task("T2c", new Hour(21, new Day())));
        TaskSeries s3 = new TaskSeries("Team C");
        s3.add(new Task("T3a", new Hour(13, new Day())));
        s3.add(new Task("T3b", new Hour(19, new Day())));
        s3.add(new Task("T3c", new Hour(21, new Day())));
        TaskSeries s4 = new TaskSeries("Team D");
        s4.add(new Task("T4a", new Day()));
        TaskSeries s5 = new TaskSeries("Team E");
        s5.add(new Task("T5a", new Day()));
        dataset.add(s1);
        dataset.add(s2);
        dataset.add(s3);
        dataset.add(s4);
        dataset.add(s5);
       
       return dataset;
    }


    public static void main(String[] args) {
        ClaseGrafico demo = new ClaseGrafico(
                "Grafico CPU");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }

}