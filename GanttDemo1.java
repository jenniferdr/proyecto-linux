import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;

/**
 * A simple demonstration application showing how to create a Gantt chart.
 * <P>
 * This demo is intended to show the conceptual approach rather than being a polished
 * implementation.
 *
 *
 */
public class GanttDemo1 extends ApplicationFrame {

    public GanttDemo1(final String title) {

        super(title);

        final IntervalCategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset, title);

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }

 
    public static IntervalCategoryDataset createDataset() {

           
        final TaskSeries s1 = new TaskSeries("Asignadas al CPU #1");
        
        s1.add(new Task("Proceso ID 2",
               new SimpleTimePeriod((long)1500,(long)3000)));
        s1.add(new Task("Proceso ID 1",
               new SimpleTimePeriod((long) 3000, (long) 5000  )));
        s1.add(new Task("Proceso ID 3",
               new SimpleTimePeriod((long)5000,(long)5500))); /*
        s1.add(new Task("Proceso ID 5",
               new SimpleTimePeriod((long)5500, (long)5600)));
        s1.add(new Task("Proceso ID 1",
               new SimpleTimePeriod(  )));
        s1.add(new Task("Proceso ID 2",
               new SimpleTimePeriod(  )));
        s1.add(new Task("Proceso ID 1",
               new SimpleTimePeriod(  )));
        s1.add(new Task("Proceso ID 1",
               new SimpleTimePeriod( )));*/
       

        final TaskSeries s2 = new TaskSeries("Asignadas al CPU #2");
        s2.add(new Task("Proceso ID 2",
               new SimpleTimePeriod( 5500,6500 )));
       

        final TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        collection.add(s2);

        return collection;
    }


    private JFreeChart createChart(final IntervalCategoryDataset dataset, String titulo) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            titulo,               // chart title
            "Procesos",              // domain axis label
            "Tiempo",              // range axis label
            dataset,             // data
            true,                // include legend
            true,                // tooltips
            false                // urls
        );    
//        chart.getCategoryPlot().getDomainAxis().setMaxCategoryLabelWidthRatio(10.0f);
        return chart;    
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
/*    public static void main(final String[] args) {

        final GanttDemo1 cpu = new GanttDemo1("Diagrama de Gantt de los CPU");
        cpu.pack();
        RefineryUtilities.centerFrameOnScreen(cpu);
        cpu.setVisible(true);
        
        final GanttDemo1 io = new GanttDemo1("Diagrama de Gantt de I/O");
        io.pack();
        RefineryUtilities.centerFrameOnScreen(io);
        io.setVisible(true);
        

    }
*/
}