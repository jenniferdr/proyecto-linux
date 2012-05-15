/**
 *
 *
 */

import java.util.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class proceso {
    
    private int id;
    private int tiempo_llegada;
    private int tiempo_espera;
    private int rafagas_cpu[];
    private int prioridad;
    private String estado; // No estoy seguro si ponerlo aqui o representarlo  segun la cola en la que este //

    private proceso(int id, int tiempo_llegada, int tiempo_espera, 
		    int[] rafagas_cpu, int prioridad, String estado) {

	this.id = id;
	this.tiempo_llegada = tiempo_llegada;
	this.tiempo_espera = tiempo_espera;
	this.rafagas_cpu = rafagas_cpu;
	this.prioridad = prioridad;
	this.estado = estado;
    }


    public static void main (String args[]) {
	// Sintaxis: java proceso <nombre_ArchivoXML>
	
	if(args.length!= 1){
	    System.err.println("Uso: java proceso <nombre_ArchivoXML>");
	    System.exit(-1);
	}

	DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
       
	try {
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    File entrada =  new File(args[0]);
	    Document doc = db.parse(entrada);
	    // Nombre del tag principal
	    //Element root = doc.getDocumentElement();

	    // Lista de procesos 
	    NodeList listaProc = doc.getElementsByTagName("proceso");
	    System.out.println("Cantidad de procesos:" + listaProc.getLength());
	    
	    // Recorrer los procesos 
	    for(int i=0; i<listaProc.getLength(); i++){
		Node proceso= listaProc.item(i);
		if(proceso.getNodeType()== Node.ELEMENT_NODE){
		    Element proc= (Element) proceso;
		    NodeList rafagas= proc.getChildNodes();
		    //NodeList lista = elemento.getElementsByTagName(tag).item(0).getChildNodes();
		    //Node valor = (Node) lista.item(0);
		    //return valor.getNodeValue();
		    //System.out.println(getTagValue("proceso",proc));
		}
		
	    }
	    //Element ele = (Element) nList.item(1);

	    //String pr =  getTextValue(ele,"cpu");
	    //System.out.println(test.getNodeName());
			      
	   
	} catch (Exception e) {
	    System.err.println("Error al abrir el archivo");
	    //e.printStackTrace();
   
	}
    }       

}

