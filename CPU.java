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

    public void usar_cpu(Tiempo t){
	System.out.println(""+t.getTiempo());
	boolean hayCambioProc= prev != null && proceso_actual!=null 
	    &&  !(prev.equals(proceso_actual));
	
	/*Imprimir solo si hay cambio de proceso*/
	if ( hayCambioProc || prev!=proceso_actual){
	    String mensaje = ("Proceso usando CPU:  " + 
			      ((proceso_actual == null) ? "Idle task" :
			       proceso_actual.getId()) + "            ");
	    //caja.push(mensaje);
	    if(proceso_actual!=null)
	    System.out.println("Proceso actual: "+proceso_actual.getId());
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