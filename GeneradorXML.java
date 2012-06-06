import java.util.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;



public class GeneradorXML {

    public static void main(String[]args)throws IOException {
	try {
	    BufferedReader lectura = new BufferedReader(new InputStreamReader(System.in));
	   
	    String nombre_Arch;
	    System.out.println("Introduzca el nombre que tendra el archivo XML:");
	    nombre_Arch = lectura.readLine();

	    int numProc;
	    System.out.println("Introduzca el # de procesos que desea cargar:");
	    numProc = Integer.parseInt(lectura.readLine());
	    System.out.println("su numero es "+numProc);
	    
	    System.out.println(" Se genera el XML, para ello se debe pedir las cotas max de ciertos");
	    System.out.println(" parametros para generar numeros aleatorios, de 1 hasta la cota max que");
	    System.out.println(" se introduzca.\n\n");
	   
	    int max_TiempoLLegada; 
	    System.out.println("Introduzca la cota para el tiempo de llegada:");
	    max_TiempoLLegada = Integer.parseInt(lectura.readLine());

	    int max_Ciclos_por_proc;
	    System.out.println("Introduzca la cota para el nro de rafagas por proceso:");
	    max_Ciclos_por_proc = Integer.parseInt(lectura.readLine());
	    
	    int ciclos_CPU;
	    System.out.println("Introduzca la cota para la cantidad de ciclos por rafagas:");
	    ciclos_CPU = Integer.parseInt(lectura.readLine());
	    
	    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
 
	    Document doc = docBuilder.newDocument();
	    Element rootElement =  doc.createElement("Simulador_Planificacion");
	    doc.appendChild(rootElement);

	    // Crear cada proceso
	    Random randomGenerator  = new Random();
	    
	    for(int i=0; i< numProc; i++) {
		Element proceso = doc.createElement("proceso");
		rootElement.appendChild(proceso);
		
		Attr attr = doc.createAttribute("tiempo_llegada");
		int t_llegada = randomGenerator.nextInt(max_TiempoLLegada)+1;
		String t_llegada_S = String.valueOf(t_llegada);
			
		attr.setValue(t_llegada_S);
		proceso.setAttributeNode(attr);
		

		int ciclos_por_Proc = randomGenerator.nextInt(max_Ciclos_por_proc)+1;

		    
		for(int j=0; j<ciclos_por_Proc; j++) {
		    Element cpu = doc.createElement("cpu");
		    proceso.appendChild(cpu);
		    
		    Attr attr_cpu = doc.createAttribute("ciclos");
		    int ciclo = randomGenerator.nextInt(ciclos_CPU)+1;
		    
		    String t_ciclo = String.valueOf(ciclo);
		    attr_cpu.setValue(t_ciclo);
		    
		    cpu.setAttributeNode(attr_cpu);
		}



	    }
	    
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    DOMSource source = new DOMSource(doc);
	    StreamResult result = new StreamResult(new File("./Entradas/"+nombre_Arch));
	    
	    transformer.transform(source, result);
 
	    System.out.println("File saved!");


	} catch (NumberFormatException e) {
	    System.out.println("Debe ser un entero mayor a cero ");
	
	} catch (ParserConfigurationException pce) {
	    pce.printStackTrace();
	} catch (TransformerException tfe) {
	    tfe.printStackTrace();
	}
	
    }
}