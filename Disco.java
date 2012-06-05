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
		
		/* Se duerme a esperar a que termine el ciclo */
		int tiempo = t.getTiempo();
		while(t.getTiempo() < tiempo + cicloIO){
		    try{
			Thread.currentThread().sleep(200*cicloIO);
			//Nota: Esto es inconveniente porque hace necesario saber 
			//que tan rapido avanza la simulacion. 
		    }
		    catch(InterruptedException ie){}
		}		
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
    public boolean termino_io(Proceso proceso){
	if (!(acceso_disco.equals(proceso))){
	    if (colaBloqueados.contains(proceso))
		return false;
	    else 
		return true;
	}
	else
	    return false;
    }
    
    /* Procedimiento para despertar un proceso una vez que termina I/O */
    private void wake_up(){
	return;
    }
    
    public void sacar_proceso(Proceso proc){
	return;
    }
    
    public void insertar_proceso(Proceso proceso){
	colaBloqueados.add(proceso);
    }
	
}