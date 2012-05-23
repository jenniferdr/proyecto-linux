/**
 * Clase que contiene las colas de procesos 
 */

import java.util.ArrayList;

public class Runqueue{

    public Prio_array_t[] arrays;
    public Proceso active;
    public Proceso expired;
    public int activo;

    // Num de procesos en la cola
    public long nr_running;
    // Carga que representa el num de procesos para el cpu
    public long cpu_load;
    // Num de cambios de contexto (para estadisticas)
    public long long nr_switches;
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
	prio2 = new Prio_array_t[2];
	prio2[0] = new Prio_array_t();
	prio2[1] = new Prio_array_t();
    }
}


class Prio_array_t{
    public byte bitmask;
    public ArrayList<Proceso>[] prio_array;

    // Cantidad de procesos en este prio_array
    public int nr_active;

    public Prio_array_t(){
	prio_array= new ArrayList<Proceso>[140];
	for (int i ; i < 140 ; i++)
	    prio_array[i] = new ArrayList<Proceso>();
    }
}