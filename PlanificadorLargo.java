public class PlanificadorLargo implements Runnable{
    Tiempo t;
    int id;
    
    PlanificadorLargo(Tiempo tiempo,int id){
	this.t=tiempo;
	this.id=id;
	new Thread(this,"PlanificadorLargo").start();
    }

    public void run(){

	int i =-100000000;
	while(true) {
	    
	    if (i==-100000000) {
		i = -100000000;
		if(t.getTiempo()%2 == 0) {
		System.out.println("Desde Planif Tiempo es par "+t.getTiempo());
		}
	    }
	    i++;
	}
    }
}