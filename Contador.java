/* 
 *
 */

public class Contador implements Runnable {
    
    Tiempo t;
    
    public Contador(Tiempo t) {
	this.t = t;
	new Thread(this,"contadorTiempo").start();
    }

    public void run(){
	while(true) {
	    try {
		Thread.sleep(2000);
		t.incTiempo();
	    } catch(InterruptedException e) {}
	}
    }
}
	  
