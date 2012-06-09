import java.util.ArrayList;

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
    int quantum = 100;
    
    //Caja para pasar mensajes a la interfaz
    Caja caja;

    Disco_check disco_check;
    
    public PlanificadorCorto(Tiempo tiempo, int id, Disco disco, Caja caja,
			     CPU cpu, Runqueue runqueue){ 
	this.id = id;
	this.runqueue = runqueue;
	this.cpu = cpu;
	this.caja = caja;
	this.disco = disco;
	this.t=tiempo;
	this.disco_check = new Disco_check();
	new Thread(this,"PlanificadorCorto").start();
    }
    
    /* Esto realmente no se hace en linux. En linux las llamadas al sistema 
     * que invocan al planificador las realizan los mismos procedimientos usuarios. 
     * En el simulador el metodo run se encarga de hacer lo que se supone que haria 
     * un proceso ejecutandose en el CPU */
    public void run(){
	Proceso actual = schedule();
	
	while(true) {
	    int tiempo_actual = t.getTiempo();
	    int tiempo_limite = tiempo_actual + quantum;
	    System.out.println("TIEMPO LIMITEEE:::  "+tiempo_limite);
	    while(t.getTiempo() < tiempo_limite)
		cpu.usar_cpu(t);

	    /*Cuando culmina su quantum ocurre "interrupción de reloj"*/
	    
	    /*llamada_sys_bloq(actual);
	      disco_check.insertar(actual);*/
	    System.out.println("CAMBIAR A OTRO PROCESOOO");
	    
	    actual = schedule();	    
	}
    }

     
    /* Procedimiento que se le ofrece a las llamadas del sistema para ceder el CPU.
     * Metodo del planificador corto que escoge un nuevo proceso para asignarle 
     * el procesador. En el simulador schedule devuelve el proceso que asigno al CPU,
     * para poder manejarlo despues en run. Esto no se hace en verdad, lo que pasa
     * es que nuestro simulador necesita hacer la llamada bloqueante en nombre del 
     * proceso que se planifico. Para esto devolvemos el proceso*/

    public Proceso schedule() {
	Proceso prev = this.cpu.get_proc();
	Proceso nuevo = this.runqueue.escoger_proceso(); 
	
	if (nuevo == null){
	    balance_carga();
	    nuevo = this.runqueue.escoger_proceso();
	    cambio_proceso(prev,nuevo);
	}
	else if (prev == null || !(prev.equals(nuevo)))
	    cambio_proceso(prev,nuevo);

	return nuevo;
    } 

    private void cambio_proceso(Proceso prev, Proceso nuevo){
	try{
	    Thread.currentThread().sleep(100);
	    cpu.set_proc(nuevo);
	}
	catch(InterruptedException ie){}
    }

    /*Algoritmo de balance de carga o rebalance_tick()*/
    private void balance_carga(){
	long old_load=this.runqueue.cpu_load;
	long this_load= 128*this.runqueue.nr_running;

	if(old_load<this_load) old_load++;
	this.runqueue.cpu_load= (this_load+old_load)/2;
	
	int intervalo=  this.runqueue.intervalo_balance; 
	if(intervalo==50){
	    //revisar las dos runqueues a ver cual tiene mas cpu_load
	    //if(this.runqueque==runqueueM) return 
	    // agarrar la de menor prioridad de la runqM y traerla 
	}
	this.runqueue.intervalo_balance= (intervalo++)%50;
	return;
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

    /*Hilo para sacar procesos del Disco*/
    private class Disco_check implements Runnable{
	private ArrayList<Proceso> pendientes;

	
	public Disco_check(){
	    pendientes = new ArrayList();
	    new Thread(this,"PlanificadorCortoDiscoCheck").start();	    
	}
	
	public void insertar(Proceso p){
	    pendientes.add(p);
	}
	
	public void run(){
	    while (true){
		while(pendientes.isEmpty()) {}
		Proceso p = ((pendientes.isEmpty())  ? null : pendientes.get(0));
		if (p != null){
		    while (!(disco.termino_io(p))){
			try{
			    System.out.println("Adios! Me voy a dormir");
			    wait();
			    System.out.println("Milagro, desperte! :o ");
			}
			catch(InterruptedException e){}
		    }
		    runqueue.insertar(p);
			System.out.println("Saque al proceso" + p.getId());			
		}
		else
		    pendientes.remove(0);
	    }
	}

	
    }       
}
