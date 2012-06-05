/**
 * Clase que contiene las colas de procesos 
 */

import java.util.ArrayList;

public class Runqueue{

    public Prio_array_t[] arrays;
    public Prio_array_t active;
    public Prio_array_t expired;
    public int activo;

    // Num de procesos en la cola
    public long nr_running;

    // Carga que representa el num de procesos para el cpu
    public long cpu_load;

    // Num de cambios de contexto (para estadisticas)
    public  long nr_switches;

    /* Tiempo que ha pasado desde el ultimo swap 
       entre los arreglos de prioridades. */
    public long expired_timestamp;

    // Proceso en ejecucion
    public Proceso curr;

    // Numero de procesos esperando por I/O
    public int nr_iowait;

    // Utilizado para verificar la carga 
    public int active_balance; 

    /* Identificador del cpu al que se le deben enviar los procesos
       cuando esta runqueue esta siendo balanceada*/ 
    public int push_cpu;

    public Runqueue(){
	activo = 0;
	arrays = new Prio_array_t[2];
	arrays[0] = new Prio_array_t();
	arrays[1] = new Prio_array_t();
    }
    
    public Proceso escoger_proceso(){
	return (Proceso) null;
    }
    
}

@SuppressWarnings("unchecked")
class Prio_array_t{
    public byte bitmask;
    public ArrayList<Proceso>[] prio_array;

    // Cantidad de procesos en este prio_array
    public int nr_active;

    public Prio_array_t(){
	prio_array= new ArrayList[140];
	for (int i=0 ; i < 140 ; i++)
	    prio_array[i] = new ArrayList();
    }
}