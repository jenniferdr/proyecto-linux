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
import java.sql.Time;
import java.util.Stack;

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
import org.jfree.data.time.*;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


        
/**
 *
 * @author juli
 */
public class Grafica extends ApplicationFrame{

   Caja1 pid = new Caja1();
    public Grafica(String title, Caja1 p, int n) {
        super(title);
        this.pid= p;
        String[] PID = new String[n];
        int[] ini = new int[n];
        int[] fin = new int[n];
   
        int i=0;
        while(i<n){
             PID[i]=pid.popPid()+"";
             ini[i]=pid.popIni();
             fin[i]=pid.popFin();

         i++;
         }

       
        JPanel chartPanel = crearPanel(PID,ini,fin,n);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
        setContentPane(chartPanel);
    }
    
     private static XYPlot crearPlot(IntervalXYDataset dataset , String []PID) {
              DateAxis xAxis = new DateAxis("Tiempo");
        xAxis.setTickMarkPosition(DateTickMarkPosition.START);
        
        SymbolAxis yAxis = new SymbolAxis("Resources", PID);
        yAxis.setGridBandsVisible(false);
        
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        
        XYPlot plot = new XYPlot(dataset, xAxis, yAxis, renderer2);
        return plot;
    }
private static JFreeChart crearChart(String [] a,int [] b,int [] c, int n) {
      XYPlot plot=crearPlot(crearData(a,b,c,n),a);
      JFreeChart chart = new JFreeChart("CPU@", plot);
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
    
    public static JPanel crearPanel(String [] a, int [] b, int [] c,int n) {

        return new ChartPanel(crearChart(a,b,c,n));
    }

    private static IntervalXYDataset crearData(String []a,int  [] b,int  [] c,int n) {
        XYTaskDataset dataset = new XYTaskDataset(crearTiempos(a,b,c,n));
        dataset.setTransposed(true);
        return dataset;
    }
private static TaskSeriesCollection crearTiempos(String[]a,int [] b,int [] c,int n) {
    
        TaskSeriesCollection dataset = new TaskSeriesCollection();
        int i=0;
        while(i<n){
        TaskSeries s1 = new TaskSeries("Proceso " +a[i]);
       s1.add(new Task("T1b", new Millisecond(b[i], new Second())));
        
               
        dataset.add(s1);
        i++;
        }
       return dataset;
    }

}
