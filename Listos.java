/**
 * Clase que representa la lista de procesos que han salido de Disco 
 * pero que aun no han sido llevados a su RunQueue
 */

import java.util.ArrayList;

public class Listos{
    private ArrayList<Proceso> colaListos;

    public Listos(){
	this.colaListos= new ArrayList<Proceso>();
    }

    public synchronized Proceso get(){
	if(colaListos.isEmpty()){
	    try { wait(); }
	    catch (InterruptedException e) { }
	}
	Proceso procesoListo= colaListos.get(0);
	colaListos.remove(0);
	return procesoListo;
    }

    public synchronized void put(Proceso proc){
	colaListos.add(proc);
	notify();
    }
    
}