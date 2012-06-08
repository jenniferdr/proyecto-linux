public class CPU {
    Proceso proceso_actual;
    Proceso prev;
    int id;
    Caja caja;
    
    public CPU(int id, Caja caja){
	this.id = id;
	this.caja = caja;
	this.prev = null;
	this.proceso_actual = null;
	caja.push("Proceso usando CPU: Idle task");
    }

    public void set_proc(Proceso proc){
	prev = proceso_actual;
	proceso_actual = proc;
    }

    public Proceso get_proc(){
	return proceso_actual;
    }

    public void usar_cpu(){

	if (prev != null && !(prev.equals(proceso_actual))){
	    String mensaje = ("Proceso usando CPU:  " + ((proceso_actual == null) ? "Idle task" : proceso_actual.getId()) + "            ");
	    caja.push(mensaje);
	    prev = proceso_actual;
	}
	try{
	    Thread.currentThread().sleep(200);
	    //Nota: Esto es inconveniente porque hace necesario saber 
	    //que tan rapido avanza la simulacion. 
	}
	catch(InterruptedException ie){}
	
    }
}