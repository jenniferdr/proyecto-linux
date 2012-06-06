/** 
 * Clase que incrementa el tiempo del reloj
 */

public class Contador implements Runnable {
    
    Tiempo t;
    int tasa;
    
    public Contador(Tiempo t,int tasa) {
	this.t = t;
	this.tasa=tasa;
	new Thread(this,"contadorTiempo").start();
    }

    public void run(){
	while(true) {
	    try {
		Thread.sleep(this.tasa);
		t.incTiempo();
	    } catch(InterruptedException e) {}
	}
    }
}
	  
