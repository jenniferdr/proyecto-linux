
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


import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.gantt.XYTaskDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.Hour;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.RefineryUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Interfaz {

    public static Caja cajaLargo;
    public static Caja cajaCorto;
    public static Caja cajaDisco;
<<<<<<< HEAD
    public static Caja1 CPU1;  
=======
    public static Runqueue cola_CPU1;
    public static Runqueue cola_CPU2;

    

>>>>>>> 38ab1edabe9d4ac0870969175459779323e6a14b
    private static Tiempo t;
    public static Caja1 CPU2 ; 
    
    // aqui deberia ir donde se van a guardar los procesos
    static int nprocesos = -1;
    static Proceso [] est_procesos; 

    private static boolean esNumero(String cadena){
	try {
	    Integer.parseInt(cadena);
	    return true;
	} catch (NumberFormatException nfe){
	    return false;
	}
    }

    public static void ordenarProc(Proceso proce,int pos,int nprocesos) {
	boolean cambio = false;
    
	for(int j=0; j<=pos;j++) {
	    Proceso iterador = est_procesos[j];
	    	    
	    if(iterador.getTiempo_llegada() >= proce.getTiempo_llegada()) {
		
		Proceso aux = est_procesos[j];
		est_procesos[j] = proce;
		
		for(int k=j+1; k<=pos; k++) {
		    Proceso aux2 = est_procesos[k];
		    est_procesos[k] = aux;
		    aux = aux2;
		}
		j=pos;
		cambio = true; 		
	    }
		
	}
    }
    
    public static LinkedList<Integer> cargarRafagas(NodeList rafagas){

	LinkedList<Integer> rafagasL = new LinkedList<Integer>();
	
	for(int j=0; j<rafagas.getLength(); j++) {
	    Node cpu_nodo = rafagas.item(j);
	    if(cpu_nodo.getNodeType()== Node.ELEMENT_NODE){
		Element cpu = (Element) cpu_nodo;
		String cpuR = cpu.getAttribute("ciclos");
		if (esNumero(cpuR)) {
		    int raf = Integer.parseInt(cpuR);
		    if(raf>0) {
			rafagasL.add(raf);
		    } else {
			System.out.println("Las rafagas de CPU deben ser enteros > 0");
			System.exit(1);
		    }
		} else {
		    System.out.println("Las rafagas de CPU deben ser enteros > 0");
		    System.exit(1);
		}
	    }
	}
	
	return rafagasL; 
    }	


    public static void cargarProcesos(NodeList listaProc){
	
	int t_llegada = -1;
	int id = 1;
	nprocesos = listaProc.getLength();
	est_procesos = new Proceso[nprocesos];
		
	// Recorrer los procesos 
	for(int i=0; i<listaProc.getLength(); i++){
	    Proceso proc_e = new Proceso();
	    Node proceso= listaProc.item(i);
	    
	    if(proceso.getNodeType()== Node.ELEMENT_NODE){
		Element proc= (Element) proceso;
		String llegada =  proc.getAttribute("tiempo_llegada");
		
		// Guarda el tiempo llegada en t_llegada
		if (esNumero(llegada)) {
		    t_llegada = Integer.parseInt(llegada);
		    if(t_llegada < 0) {
			System.out.println("El tiempo de llegada debe ser entero > 0");
			System.exit(1);
		    }
		} else {
		    System.out.println("El tiempo de llegada debe ser entero > 0");
		    System.exit(1);
		}
		
		NodeList rafagas= proc.getChildNodes();
		LinkedList<Integer> rafagasL = cargarRafagas(rafagas);
				
		// Seteando el proceso
		proc_e.setId(id);
		id++;
		proc_e.setTiempo_llegada(t_llegada);
		proc_e.setTiempo_espera(0);
		proc_e.setRafagas_cpu(rafagasL);
		proc_e.setPrioridad(0);

		if( i==0 ) {
		    est_procesos[0] = proc_e;
		} else {
		    est_procesos[i] = proc_e;
		    ordenarProc(proc_e,i,nprocesos);
		}
		
		/* Aqui estoy immprimiendo los datos de cada proceso, no lo borre por si acaso lo 
		   llegan a necesitar, sino pueden borrarlo tranquilamente
			
		System.out.println("# de procesos: "+nprocesos);
		System.out.println("El tiempo llegada es "+proc_e.getTiempo_llegada());
		for(int ii=0; ii<proc_e.getRafagas_cpu().size();ii++) {
		    System.out.println(proc_e.getRafagas_cpu().get(ii));

		 }
		*/


		// aqui deberia guardarse el proc
	    }
	}
    }	
   

    public static void parserXML(String nombre_ArchivoXML) {
	
	DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
	
	try {
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    File entrada =  new File(nombre_ArchivoXML);
	    Document doc = db.parse(entrada);
	    
	    NodeList listaProc = doc.getElementsByTagName("proceso");
	
	    cargarProcesos(listaProc);
	
	     System.out.println("# de procesos: "+nprocesos);
	
	    for(int ii=0; ii<nprocesos;ii++) {
		System.out.println("Proceso "+est_procesos[ii].getId());
		System.out.println("    Tiempo llegada: "+est_procesos[ii].getTiempo_llegada());


		}
	    

	} catch (Exception e) {
	    //System.err.println("Error al abrir el archivo");
	    e.printStackTrace();
	    
	}
    }


    //private static void graficas(int p , int i, int fi, int n){
      private static void graficas(){
	JFrame f = new JFrame();
	Container container = f.getContentPane();
	container.setLayout(new GridBagLayout());
	((JPanel)container).setBorder(BorderFactory.createTitledBorder("Simulador del Linux-Kernel"));
	GridBagConstraints c = new GridBagConstraints();

	JToolBar toolbar = new JToolBar();
	for (int i = 0; i<10; i++)
	    toolbar.add(new JButton("<" + i + ">"));

	f.setSize(800,600);
	f.setTitle("Simulador del Linux-Kernel");
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	c.weightx = 1.0; 
	c.weighty = 0.0;


	JLabel mensajeCorto = new JLabel("");
	container.add(mensajeCorto,c);
	JLabel tiempo = new JLabel("");
	container.add(tiempo);
	JLabel mensajeLargo = new JLabel("");
	while (true){	    
	     if (!(cajaCorto.empty())){
		 c.fill = GridBagConstraints.NONE;
		 c.gridx = 0; 
		 c.gridy = 1;
		 c.gridwidth = 1; 
		 c.gridheight = 1;
		 c.anchor = GridBagConstraints.WEST;
 
		 f.setVisible(true);
		 container.remove(mensajeCorto);
		 mensajeCorto = new JLabel(cajaCorto.pop());
		 container.add(mensajeCorto,c);
	     }
	     if (!(cajaLargo.empty())){
		 c.gridy = 0;
		 c.gridx = 2;
		 c.fill = GridBagConstraints.NONE;
		 c.anchor = GridBagConstraints.CENTER;

		 f.setVisible(true);
		 container.remove(mensajeLargo);
		 mensajeLargo = new JLabel(cajaLargo.pop());
		 container.add(mensajeLargo,c);
	     }

	     c.gridy = 1;
	     c.gridx = 4;
	     c.fill = GridBagConstraints.NONE;
	     c.gridwidth = GridBagConstraints.RELATIVE;
	     c.anchor = GridBagConstraints.EAST;

	     f.setVisible(true);
	     container.remove(tiempo);
	     tiempo = new JLabel(String.valueOf(t.getTiempo()));
	     container.add(tiempo,c);
	}

    }
    private static void pruebas (){
        int i=0;
      
      while(i< 10){  
        CPU1.push(i, i*10 , i*10+2);
       CPU2.push(i, i*10 , i*10+2);
        i++;
      }
      
    }
    public static void main (String args[]){
	try {
	    
	    if(args.length!= 2){
		System.err.println("Uso: java proceso <nombre_ArchivoXML> <retardo>");
		System.exit(-1);
	    }
	    
	    parserXML(args[0]);
	    
	} catch (Exception e) {
	    System.err.println("Error al abrir el archivo");
<<<<<<< HEAD
	    e.printStackTrace();
	    
=======
	    //e.printStackTrace();   
	}
	int retardo=10;
	if (esNumero(args[1])) {
	    retardo = Integer.parseInt(args[1]);
	    if(retardo<=0){
		System.err.println("El retardo debe ser entero positivo");
		System.exit(-1);
	    }
	}else{
	    System.err.println("El retardo debe ser un numero entero positivo");
	    System.exit(-1);
>>>>>>> 38ab1edabe9d4ac0870969175459779323e6a14b
	}


<<<<<<< HEAD
        t = new Tiempo();
	new Contador(t);
            
=======
	// Prueba de tiempo.
	t = new Tiempo();
	new Contador(t,retardo);
>>>>>>> 38ab1edabe9d4ac0870969175459779323e6a14b

	cajaLargo = new Caja();	
	cajaCorto = new Caja();	
	cajaDisco = new Caja();
<<<<<<< HEAD
	CPU1 = new Caja1();
        CPU2 = new Caja1();
	Disco disco = new Disco(t);
	new PlanificadorLargo(t,1,cajaLargo);
	new PlanificadorCorto(t,1,disco,cajaCorto);
        //pruebas de graficas
        
          pruebas();
          nprocesos=10;
          
               Grafica cpu1 = new Grafica(
                "Grafico CPU1", CPU1,nprocesos);
        cpu1.pack();
        RefineryUtilities.centerFrameOnScreen(cpu1);
        cpu1.setVisible(true);
        
     Grafica cpu2 = new Grafica(
              "Grafico CPU2",CPU2, nprocesos);
     cpu1.pack();
     RefineryUtilities.centerFrameOnScreen(cpu2);
    
     cpu2.setVisible(true);
        
            //graficas();
    }

    
}


        
=======
	cola_CPU1 = new Runqueue();
	cola_CPU2 = new Runqueue();


	CPU cpu = new CPU(1,cajaCorto,retardo); 
	Runqueue runqueue = new Runqueue();

	/*Colas de procesos que han salido de disco*/
	Listos listosIO1= new Listos();
	Listos listosIO2= new Listos();
	Disco disco = new Disco(t,retardo,listosIO1,listosIO2);

	new PlanificadorLargo(t,1,cajaLargo,cola_CPU1,cola_CPU2,est_procesos);
	new PlanificadorCorto(t,1,disco,cajaCorto,cpu,cola_CPU1,retardo,listosIO1,nprocesos);

	//new PlanificadorCorto(t,2,disco,cajaCorto, cpu,cola_CPU2,retardo,listosIO2);
	
	try {
	    //graficas();
	}	
	catch (ArrayIndexOutOfBoundsException  e){}
    }

}   
>>>>>>> 38ab1edabe9d4ac0870969175459779323e6a14b
