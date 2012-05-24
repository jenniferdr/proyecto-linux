import java.util.ArrayList;

public class Disco implements Runnable{
    private ArrayList<Proceso> colaBloqueados;
    private int cicloIO;
    private Tiempo t;

    /*Referencia al proceso de la cola de bloqueados que 
    * tiene acceso al disco en cada instante*/
    private Proceso acceso_disco;
    
    public Disco(Tiempo t){
	this.t = t;
	colaBloqueados = new ArrayList<Proceso>();
	cicloIO = 10;
	acceso_disco = null;
	new Thread(this,"Disco").start();
    }
    
    public void run(){
	while (true){
	    if (!(colaBloqueados.isEmpty())){
		/* Se asigna el disco al primer proceso y se saca de la cola de espera*/
		acceso_disco = colaBloqueados.get(0);
		colaBloqueados.remove(0);
		
		/* Se hace bussy waiting a que termine el ciclo */
		int aux = t.getTiempo();
		do {
		    aux = t.getTiempo() - aux;
		} while(aux != cicloIO);
		
		/*El proceso esta listo para salir, queda de parte del planificador
		 * de corto plazo darse cuenta */
		acceso_disco = null;
	    }
	}
    }	
	
    /* Se pregunta si un proceso termino su ciclo de I/O. Esto
     * lo realizara el planificador de corto plazo para devolver un
     * a la cola de listos. Retorna true si el proceso estaba 
     * listo para salir*/
    public boolean sacar_proceso(Proceso proceso){
	if (!(acceso_disco.equals(proceso))){
		if (colaBloqueados.contains(proceso))
		    return false;
		else 
		    return true;
	    }
	else
	    return false;
    }
    
    public void insertar_proceso(Proceso proceso){
	colaBloqueados.add(proceso);
    }
	
}