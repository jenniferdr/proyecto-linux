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
	
       DocumentBuilderFactory dbf =  DocumentBuilderFactory.newInstance();
       
       try {
	   DocumentBuilder db = dbf.newDocumentBuilder();
	   File entrada =  new File("/home/giuseppe/Desktop/proyecto-linux/Entradas/entrada1.xml");
	   Document doc = db.parse(entrada);
	   Element test = doc.getDocumentElement();

	   NodeList nList = doc.getElementsByTagName("proceso");


	   Element ele = (Element) nList.item(1);

	   String pr =  getTextValue(ele,"cpu");
	   System.out.println(pr);
			      



	   

	   
       } catch (Exception e) {
		e.printStackTrace();
   
       }
   }       

}

