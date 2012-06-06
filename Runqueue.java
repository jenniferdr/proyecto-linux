/**
 * Clase que contiene las colas de procesos 
 */

import java.util.ArrayList;
import java.math.BigInteger;
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
	return arrays[activo].escoger_proceso();
    }
    
}

@SuppressWarnings("unchecked")
class Prio_array_t{
    public ArrayList<Proceso>[] prio_array;

    // Cantidad de procesos en este prio_array
    public int nr_active;

    public Prio_array_t(){
	prio_array= new ArrayList[140];
	for (int i=0 ; i < 140 ; i++)
	    prio_array[i] = new ArrayList();
    }

    public Proceso escoger_proceso(){
	int pos;
	for (pos = 0; pos < prio_array.length(); pos++)
	    if (!(prio_array[pos].isEmpty()))
		break;
	if (pos == prio_array.length())
	    return (Proceso) null;
	Proceso proc = prio_array[pos].get(0);
	prio_array[pos].remove(0);
	nr_active--;
	return proc;
    }
    
    public void insertar(Proceso proc){
	prio_array[0].add(proc);
	nr_active++;
    }

    public void insertar(Proceso proc, int pos){
	prio_array[pos].add(proc);
	nr_active++;
    }
}