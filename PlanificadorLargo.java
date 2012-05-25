public class PlanificadorLargo implements Runnable{
    Tiempo t;
    int id;
    Caja caja;
    
    PlanificadorLargo(Tiempo tiempo,int id, Caja caja){
	this.t=tiempo;
	this.id=id;
	this.caja = caja;
	new Thread(this,"PlanificadorLargo").start();
    }

    public void run(){
	int quantum = 23; 
	int tiempo;
	while(true){
	    tiempo = t.getTiempo();
	    while(t.getTiempo() < tiempo + quantum){
		try{
		    Thread.currentThread().sleep(100);
		}
		catch(InterruptedException ie){}
	    }
	    
	    String mensaje = "Planificador largo: tiempo: "+ String.valueOf(t.getTiempo());
	    caja.push(mensaje);
	    
	}
    }
}