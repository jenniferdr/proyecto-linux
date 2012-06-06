public class CPU {
    Proceso proceso_actual;
    int id;
    Caja caja;
    
    public CPU(int id, Caja caja){
	this.id = id;
	this.caja = caja;
	this.proceso_actual = null;
    }

    public void set_proc(Proceso proc){
	this.proceso_actual = proc;
    }

    public Proceso get_proc(){
	return this.proceso_actual;
    }

    public void usar_cpu(){
	String mensaje = ("Proceso usando CPU:" + ((proceso_actual == null) ? "Idle task" : proceso_actual.getId()));
	caja.push(mensaje);
	try{
	    Thread.currentThread().sleep(200);
	    //Nota: Esto es inconveniente porque hace necesario saber 
	    //que tan rapido avanza la simulacion. 
	}
	catch(InterruptedException ie){}
	
    }
}