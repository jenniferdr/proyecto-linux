class Tiempo{

    private int tiempo;
    Tiempo(){
	this.tiempo=0;
    }
    public void incTiempo(){
	this.tiempo++;
    }
    public int getTiempo(){
	return this.tiempo;
    }
}

class Planificador1 implements Runnable{
    Tiempo t;
    int id;
    
    Planificador1(Tiempo tiempo,int id){
	this.t=tiempo;
	this.id=id;
	new Thread(this,"planificador1").start();
    }

    public void run(){
	for(int i=0; i<10000 ; i++){
	    System.out.println("En planificador Tiempo: "+ id + " "+this.t.getTiempo());
	}
    }
}

class ContadorT implements Runnable{
    Tiempo t;

    ContadorT(Tiempo t){
	this.t= t;
	new Thread(this,"contadorTiempo").start();
    }

    public void run(){
	while(true){
	    try{
	    Thread.sleep(30);
	    t.incTiempo();
	    }catch(InterruptedException e){}
	} 
    }
}

class Prueba{
    public static void main(String args[]){
	Tiempo t= new Tiempo();
	new Planificador1(t,1);
	new Planificador1(t,2);
	new ContadorT(t);
    }
}