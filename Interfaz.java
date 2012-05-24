import java.util.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.swing.*;
import java.awt.*;

public class Interfaz {

    public static Caja cajaLargo;
    public static Caja cajaCorto;
    public static Caja cajaDisco;
    
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
		System.out.println("Proceso "+ii);
		System.out.println("    Tiempo llegada: "+est_procesos[ii].getTiempo_llegada());


		}
	    

	} catch (Exception e) {
	    //System.err.println("Error al abrir el archivo");
	    e.printStackTrace();
	    
	}
    }

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

	c.fill = GridBagConstraints.NONE;
	c.gridx = 0; 
	c.gridy = 1;
	c.gridwidth = 1; 
	c.gridheight = 1;
	c.anchor = GridBagConstraints.WEST;
	
	JLabel mensajito = new JLabel("");
	container.add(mensajito,c);
	while (true){
	    while(cajaCorto.empty()){
		try{
		    Thread.currentThread().sleep(100);
		}
		catch(InterruptedException ie){}
	    }
	    f.setVisible(true);
	    container.remove(mensajito);
	    mensajito = new JLabel(cajaCorto.pop());
	    container.add(mensajito,c);
	}
	// container.add(new JLabel("Hola!"),c);
	// c.gridx = 2;
	// c.fill = GridBagConstraints.NONE;
	// c.anchor = GridBagConstraints.CENTER;
	// container.add(new JLabel("Caja2"),c);
	
	// c.gridx = 4;
	// c.fill = GridBagConstraints.NONE;
	// c.gridwidth = GridBagConstraints.RELATIVE;
	// c.anchor = GridBagConstraints.EAST;
	// container.add(new JLabel("Caja3"),c);
	
    }
    
    public static void main (String args[]){
	
	try {
	    
	    if(args.length!= 1){
		System.err.println("Uso: java proceso <nombre_ArchivoXML>");
		System.exit(-1);
	    }
	    
	    parserXML(args[0]);
	    
	} catch (Exception e) {
	    System.err.println("Error al abrir el archivo");
	    //e.printStackTrace();
	    
	}

	// Prueba de tiempo.

	Tiempo t = new Tiempo();
	new Contador(t);

	cajaLargo = new Caja();	
	cajaCorto = new Caja();	
	cajaDisco = new Caja();

	Disco disco = new Disco(t);
	new PlanificadorLargo(t,1);
	new PlanificadorCorto(t,1,disco,cajaCorto);

	graficas();
	while(true) {
	    int i = t.getTiempo();
	    while(t.getTiempo() < i + 1){
		try{
		    Thread.currentThread().sleep(100);
	    	}
	    	catch(InterruptedException ie){}
	    }
	    
	    System.out.println("Desde Interfaz Tiempo es "+t.getTiempo());
	}	
    }   
}       
