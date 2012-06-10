import java.util .*;

@SuppressWarnings("unchecked")
public class PlanificadorLargo implements Runnable{
    Tiempo t;
    int id;
    Caja caja;
    static Runqueue cola_A;
    static Runqueue cola_B;
    Proceso [] procesos;

 
    PlanificadorLargo(Tiempo tiempo,int id, Caja caja,Runqueue cola_A, Runqueue cola_B,Proceso [] procesos){
	this.t=tiempo;
	this.id=id;
	this.caja = caja;
	this.cola_A = cola_A;
	this.cola_B = cola_B;
	this.procesos = procesos;

	System.out.println(" Antes del .start");
	new Thread(this,"PlanificadorLargo").start();
    }

    public static int seleccionar_runqueue() {
	long procesos_colaA = cola_A.getNr_running();
	long procesos_colaB = cola_B.getNr_running();

	if (procesos_colaA <= procesos_colaB) {
	    return 1;
	} else {
	    return 0;
	}
    } 

    public static void agregar_Proceso(Proceso proc_A, Runqueue cola_G) {
	
	//int activo = cola_G.getActivo();
	//Prio_array_t [] array_Prios = cola_G.getArrays();

	// Hacer la vaina mistica con el bitmap
	/* 
	Prio_array_t active_E =  array_Prios[activo];
	ArrayList[] arreglo_prioridades = active_E.prio_array;
	ArrayList prio_cero = arreglo_prioridades[0];
	prio_cero.add(proc_A);
	*/
	cola_G.insertar(proc_A);
	long nr_proc = cola_G.getNr_running()+1;
	cola_G.setNr_running(nr_proc);
	
    }

    
    @SuppressWarnings("unchecked")    
    public void run(){

	/*	int quantum = 23; 
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
	*/

	int tiempo;
	int pos_Array_Proc = 0;
	int tiempo_llegada = 0;
	System.out.println(" # procesos: "+ procesos.length);
	boolean matarHilo=true;
	while(matarHilo) {
	    tiempo = t.getTiempo();
	    
	    Proceso porCrearse = procesos[pos_Array_Proc];
	    
	    tiempo_llegada = porCrearse.getTiempo_llegada();
	    
	    try {
	
		Thread.sleep(1000);
	       
	    } catch(InterruptedException e) {}
	    
	    if ( tiempo_llegada <= tiempo) {
		
		System.out.println("En el tiempo "+tiempo_llegada+" se crea el proc id: "+porCrearse.getId());
		System.out.println(" pos donde voy: "+ pos_Array_Proc);
		//	System.exit(1);
		if ( pos_Array_Proc < procesos.length){
		    int cola_Ganadora = seleccionar_runqueue();
		    
		    if (cola_Ganadora==1) {
			porCrearse.setEnCPU(1);
			agregar_Proceso(porCrearse,cola_A);
		    } else {
			porCrearse.setEnCPU(2);
			agregar_Proceso(porCrearse,cola_B);
		    }
		    pos_Array_Proc++;
		} 
		
		if (pos_Array_Proc == procesos.length){

		    // Esto es para imprimir las Runqueu puede comentarse hasta
		    	System.out.println(" ------------------ Imprimir Runqueue ----------------");
	
			System.out.println("Cola CPU1 tiene "+cola_A.getNr_running()+" procesos y Cola CPU2 tiene "+cola_B.getNr_running()+" procesos");
			
			System.out.println(" ------------------ CPU 1 ----------------");
			ArrayList imprim = (cola_A.getArrays()[0]).prio_array[0];
			
			for(int ite=0; ite < imprim.size();ite++){
			    Proceso pr = (Proceso)  imprim.get(ite);
			    System.out.println ("Soy el proc "+pr.getId()+" y estoy en cpu "+pr.getEnCPU());
			}

			System.out.println(" ------------------ CPU 2 ----------------");
			ArrayList imprim1 = (cola_B.getArrays())[0].prio_array[0];
			
			for(int ite1=0; ite1 < imprim1.size();ite1++){
			
			    Proceso pr1 = (Proceso)  imprim1.get(ite1);
			    System.out.println ("Soy el proc "+pr1.getId()+" y estoy en cpu "+pr1.getEnCPU());
			
			}

		    matarHilo=false;
		    }
	    }

	}
    }
}
