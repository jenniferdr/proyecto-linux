import java.util.ArrayList;

public class PlanificadorCorto implements Runnable{
    Tiempo t;
    int retardo;
    int id;

    CPU cpu;

    /*Cola de listos que maneja cada planificador*/
    Runqueue runqueue;

    /*Referencia al Disco donde debe enviar a los procesos cuando ellos lo solicitan*/
    static Disco disco;
    
    /* Por los momentos se colocara el quantum como un atributo que no cambia, 
     * para la siguiente entrega se realizaran los algoritmos que lo calculan para 
     * cada proceso */
    int quantum=50;
    
    //Caja para pasar mensajes a la interfaz
    Caja caja;

    Disco_check disco_check;
    boolean NEED_RESCHED;
    
    public PlanificadorCorto(Tiempo tiempo, int id, Disco disco, Caja caja,CPU cpu,
			     Runqueue runqueue,int retardo,Listos colaListosIO){ 
	this.id = id;
	this.runqueue = runqueue;
	this.cpu = cpu;
	this.caja = caja;
	this.disco = disco;
	this.t=tiempo;
	this.retardo=retardo;
	this.disco_check = new Disco_check(colaListosIO);
	this.NEED_RESCHED= false;
	new Thread(this,"PlanificadorCorto").start();
    }
    
    /* Esto realmente no se hace en linux. En linux las llamadas al sistema 
     * que invocan al planificador las realizan los mismos procedimientos usuarios. 
     * En el simulador el metodo run se encarga de hacer lo que se supone que haria 
     * un proceso ejecutandose en el CPU */
    public void run(){
	Proceso actual = schedule();
	
	while(true) {
	    // Sacar la rafaga del proceso
	    int rafaga=(actual!=null)?actual.getRafaga():retardo/2; 
	    int tiempo_inicio = t.getTiempo();
	    int tiempo_limiteQ = tiempo_inicio + quantum;
	    int tiempo_limiteR= tiempo_inicio + rafaga;

	    while(t.getTiempo() < tiempo_limiteQ && t.getTiempo()<tiempo_limiteR){
		cpu.usar_cpu(t);
		if(NEED_RESCHED)break;
	    }
	    int tiempo_final= t.getTiempo();
	    // Si no es la idle task..
	    if(actual!=null){
		System.out.print("Se ejecuto el proceso "+actual.getId()+
				 " desde:"+tiempo_inicio+" hasta: ");

		 // Se agota el quantum y casualmente tambien termina su rafaga actual
		if(t.getTiempo()>=tiempo_limiteQ && t.getTiempo()>=tiempo_limiteR ){
		    if(actual.quedanRafagas()){
			/* Como no ha terminado su ejecucion significa que requiere
			   leer/escribir de disco y se encola alla */
			this.runqueue.nr_iowait++;
			llamada_sys_bloq(actual);
		    }else{
			// Si no quedan mas rafagas debe morir
			// Se pueden modificar aqui sus datos antes de morir
		    }
		    actual.setQuantumRestante(0);
		    actual.setRafaga(0);
		    actual.setSleep_avg(actual.getSleep_avg()-(tiempo_inicio
							       -tiempo_limiteQ));
		    System.out.println(""+tiempo_limiteQ);
		  /*Se agota el quantum pero no su rafaga actual */ 
		}else if(t.getTiempo()>=tiempo_limiteQ){
		    // Refill al quantum 
		    actual.setQuantumRestante(quantum);
		    actual.setRafaga(actual.getRafaga()-(tiempo_inicio
							 -tiempo_limiteR));
		    // Se penaliza por estarse ejecutado tanto en cpu
		    actual.setSleep_avg(actual.getSleep_avg()-(tiempo_inicio
							       -tiempo_limiteQ));
		    actual.effective_prio();
		    // Mandarlo a los expirados 
		    runqueue.insertarExp(actual,actual.getPrioridad());
		    System.out.println(""+tiempo_limiteQ);
		 /*Se agota su rafaga actual pero no su quantum*/
		}else if(t.getTiempo()>=tiempo_limiteR){
		    if(actual.quedanRafagas()){
			/* Como no ha terminado su ejecucion significa que requiere
			   leer/escribir de disco y se encola alla */
			this.runqueue.nr_iowait++;
			llamada_sys_bloq(actual);
		    }else{
			// Si no quedan mas rafagas debe morir
			// Se pueden modificar aqui sus datos antes de morir
		    }
		    actual.setRafaga(0);
		    actual.setQuantumRestante(actual.getQuantumRestante()
					      -(tiempo_inicio-tiempo_limiteQ));
		    actual.setSleep_avg(actual.getSleep_avg()-(tiempo_inicio
							       -tiempo_limiteQ));
		    System.out.println(""+tiempo_limiteR);
		 /*Solo fue vilmente expropiado*/
		}else{
		     actual.setRafaga(actual.getRafaga()-(tiempo_inicio
							  -tiempo_final));
		     actual.setQuantumRestante(actual.getQuantumRestante()
					       -(tiempo_inicio-tiempo_final));
		     // Se penaliza por el tiempo en cpu
		     actual.setSleep_avg(actual.getSleep_avg()-(tiempo_inicio
								-tiempo_limiteQ));
		    actual.effective_prio();
		    runqueue.insertar(actual,actual.getPrioridad());
		    System.out.println(""+tiempo_final);
		}
	    }

	    // Pedir otro proceso para llevar a CPU
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
	//try{
	    //Thread.currentThread().sleep(100);
	    cpu.set_proc(nuevo);
	    //}
	    //catch(InterruptedException ie){}
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
	// if (!(disco.termino_io(proc))){
	//     schedule();
	//     return;
	// }
	// //setear el proceso como listo
	// runqueue.insertar(proc);
    }

    /*Hilo para sacar procesos del Disco*/
    private class Disco_check implements Runnable{
	private Listos listos;
	
	public Disco_check(Listos colaListosDisco){
	    this.listos= colaListosDisco;
	    new Thread(this,"PlanificadorCortoDiscoCheck").start();	    
	}
	
	public void run(){
	    while (true){
		Proceso p=listos.get();
		/*Premiar al proceso por haber dormido*/
		p.setSleep_avg(p.getSleep_avg() +2);
		p.effective_prio();
		// Colocar proceso en la RunQueue
		// (Si se le acabo su quantum igual se coloca alli)
		runqueue.insertar(p,p.getPrioridad());
		runqueue.nr_iowait--;
		/*Avisar que este proceso puede expropiar al CPU*/
		NEED_RESCHED=true;
		System.out.println("Saque al proceso" + p.getId());
	    }
	}
    }       
}
