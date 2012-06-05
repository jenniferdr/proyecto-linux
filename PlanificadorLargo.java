public class PlanificadorLargo implements Runnable{
    Tiempo t;
    int id;
    
    PlanificadorLargo(Tiempo tiempo,int id){
	this.t=tiempo;
	this.id=id;
	new Thread(this,"PlanificadorLargo").start();
    }

    public void run(){
	int quantum = 5;
	while(true) {
	    int tiempo = t.getTiempo();
	    while(t.getTiempo() < tiempo + quantum){
		try{
		    Thread.currentThread().sleep(100);
	    	}
	    	catch(InterruptedException ie){}
	    }
	    System.out.println("El planificador largo anuncia que pasaron "
			       + quantum  +" tiempos");
	}
    }
}