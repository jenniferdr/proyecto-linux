public class PlanificadorCorto implements Runnable{
    Tiempo t;
    int id;

    /*Cola de listos que maneja cada planificador*/
    Runqueue runqueue;

    /*Referencia al Disco donde debe enviar a los procesos cuando ellos lo solicitan*/
    Disco disco;
    
    /* Por los momentos se colocara el quantum como un atributo que no cambia, 
     * para la siguiente entrega se realizaran los algoritmos que lo calculan para 
     * cada proceso */
    int quantum = 10;
    
    //Caja para pasar mensajes
    Caja caja;
    
    public PlanificadorCorto(Tiempo tiempo, int id, Disco disco, Caja caja){ 
	this.id = id;
	this.caja = caja;
	this.disco = disco;
	this.t=tiempo;
	new Thread(this,"PlanificadorCorto").start();
    }
    
    public void run(){
	//Se saca un proceso de la cola de Runqueue
	int aux,tmp;
	while(true) {
	    /*Duerme hasta que expire el quantum*/	    
	    int tiempo = t.getTiempo();
	    while(t.getTiempo() < tiempo + quantum){
		try{
		    Thread.currentThread().sleep(100);
	    	}
	    	catch(InterruptedException ie){}
	    }
	    String mensaje = "Paso un quantum del planificador corto en el tiempo: "+ String.valueOf(t.getTiempo());
	    caja.push(mensaje);
	    //	    System.out.println("El Planificador de corto plazo anuncia que" + 
	    //		       " expiro un quantum. Tiempo ="+t.getTiempo());
	    //Aqui se envia ese proceso a Disco	    
	}
    }
    
}