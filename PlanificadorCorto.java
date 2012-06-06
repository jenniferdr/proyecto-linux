public class PlanificadorCorto implements Runnable{
    Tiempo t;
    int id;

    CPU cpu;

    /*Cola de listos que maneja cada planificador*/
    Runqueue runqueue;

    /*Referencia al Disco donde debe enviar a los procesos cuando ellos lo solicitan*/
    static Disco disco;
    
    /* Por los momentos se colocara el quantum como un atributo que no cambia, 
     * para la siguiente entrega se realizaran los algoritmos que lo calculan para 
     * cada proceso */
    int quantum = 10;
    
    //Caja para pasar mensajes
    Caja caja;
    
    public PlanificadorCorto(Tiempo tiempo, int id, Disco disco, Caja caja, CPU cpu, Runqueue runqueue){ 
	this.id = id;
	this.runqueue = runqueue;
	this.cpu = cpu;
	this.caja = caja;
	this.disco = disco;
	this.t=tiempo;
	new Thread(this,"PlanificadorCorto").start();
    }
    
    /* Esto realmente no se hace en linux. En linux las llamadas al sistema 
     * que invocan al planificador las realizan los mismos procedimientos usuarios. 
     * En el simulador el metodo run se encarga de hacer lo que se supone que haria 
     * un proceso ejecutandose en el CPU */
    public void run(){
	Proceso actual = schedule();
	
	while(true) {
	    Proceso actual=schedule();
	    int tiempo = t.getTiempo();
	    while(t.getTiempo() < tiempo + quantum)
		cpu.usar_cpu();
	    llamada_sys_bloq(actual);
	}
    }

    
    /* Procedimiento que simula las llamadas del sistema bloqueantes que
     * ofrece el sistema de operacion. Envia un proceso a la cola de bloqueados, 
     * en nuestro simulador se envia a la cola de bloqueados del disco 
     * directamente */
    public void llamada_sys_bloq(Proceso proc){
	disco.insertar_proceso(proc);
	//setear el proceso como bloqueado
	if (!(disco.termino_io(proc))){
	    schedule();
	    return;
	}
	//setear el proceso como listo
	runqueue.insertar(proc);
    }
    
    /* Procedimiento que se le ofrece a las llamadas del systema para ceder el CPU.
     * Metodo del planificador corto que escoge un nuevo proceso para asignarle 
     * el procesador. En el simulador schedule devuelve el proceso que asigno al CPU,
     * para poder manejarlo despues en run. Esto no se hace en verdad, lo que pasa
     * es que nuestro simulador necesita hacer la llamada bloqueante en nombre del 
     * proceso que se planifico. Para esto devolvemos el proceso*/

    public Proceso schedule() {
	Proceso prev = cpu.get_proc();
	Proceso nuevo = runqueue.escoger_proceso(); 
	
	if (nuevo == null){
	    balance_carga();
	    nuevo = runqueue.escoger_proceso(); 
	}
	else if (!(prev.equals(nuevo)))
	    cambio_contexto(prev,nuevo);

	return nuevo;
    } 

    private void cambio_contexto(Proceso prev, Proceso nuevo){
	try{
	    Thread.currentThread().sleep(100);
	    cpu.set_proc(nuevo);
	}
	catch(InterruptedException ie){}
    }

    /*Algoritmo de balance de carga*/
    private void balance_carga(){
	return;
    }
    
}


/* Ignoren esto:
   private class Llamada_sys_bloq() implements Runnable{
   Proceso proc;
   Tiempo t;
   Disco disco;
	
   public Llamada_sys_bloq(Proceso proc, Disco disco, Tiempo t){
   this.disco = disco;
   this.t = t;
   this.proc = proc;
   new Thread(this,"Llamada_sys_bloq").start();
   }

   public void run(){
   while(true){
   if (disco.termino_io(proc))
   break;
   schedule();
   //wait mistico por el disco
		
   }
   disco.wake_up(proc);
   }
   }




*/