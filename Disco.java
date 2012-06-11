import java.util.ArrayList;

public class Disco implements Runnable{
    private ArrayList<Proceso> colaBloqueados;
    private int cicloIO;
    private Tiempo t;
    /*Retardo, representa el intervalo para un tick de reloj*/
    private int retardo;
    private Listos colaListos1;
    private Listos colaListos2;

    /*Referencia al proceso de la cola de bloqueados que 
    * tiene acceso al disco en cada instante*/
    private Proceso acceso_disco;
    
    public Disco(Tiempo t,int retardo,Listos colaListos1,Listos colaListos2){
	this.t = t;
	colaBloqueados = new ArrayList<Proceso>();
	cicloIO = 2;
	acceso_disco = null;
	this.retardo=retardo;
	this.colaListos1= colaListos1;
	this.colaListos2= colaListos2;
	new Thread(this,"Disco").start();
    }
    
    public void run(){
	while (true){
	    if (!(colaBloqueados.isEmpty())){
		/* Se asigna el disco al primer proceso y se saca de 
		   la cola de espera*/
		acceso_disco = colaBloqueados.get(0);
		colaBloqueados.remove(0);

		/* Se duerme a esperar a que termine el ciclo */
		int tiempo = t.getTiempo();
		int tiempoDisco = tiempo - acceso_disco.getEspera_IOini();
		int acuEspera =  acceso_disco.getEspera_IOacu()+tiempoDisco;
		acceso_disco.setEspera_IOacu(acuEspera);
		

		while(t.getTiempo() < tiempo + cicloIO){
		    try{
			Thread.currentThread().sleep(this.retardo*cicloIO);
		    }
		    catch(InterruptedException ie){}
		}		

		/*El proceso vuelve a estado "listo", en su respectivo CPU*/
		switch(acceso_disco.getEnCPU()){
		case 1:colaListos1.put(acceso_disco);
		    break;
		case 2: colaListos2.put(acceso_disco);
		}
		/*Liberar el uso del Disco*/
		acceso_disco = null;
	    }
	    else{
		try{
		    Thread.currentThread().sleep(retardo/2);
		}
		catch(InterruptedException ie){}
	    }
	}
    }	
	
    /* Se pregunta si un proceso termino su ciclo de I/O. Esto
     * lo realizara el planificador de corto plazo para devolver un
     * a la cola de listos. Retorna true si el proceso estaba 
     * listo para salir*/
    public boolean termino_io(Proceso proceso){
	if (acceso_disco == null)
	    return true;
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
    
    public synchronized  void insertar_proceso(Proceso proceso){
	colaBloqueados.add(proceso);
    }	
}

