/**
 *  Clase proceso
 *       
 */

import java.util.*;
import java.io.*;
import java.lang.Math;

/*Falta calcular el sleep_avg cada vez que se mueve un proceso y shed_tic*/

public class Proceso {
    
    private int id;
    private int tiempo_llegada;
    private int tiempo_espera;
    private LinkedList<Integer> rafagas_cpu;
    private static final int static_prio=0;
    private int prio;
    /* Tiempo que el proceso ha dormido menos el que ha consumido en CPU*/
    private int sleep_avg;
    private static final int MAX_SLEEP_AVG=70;
    private int MAX_BONUS=10;
    private int quantum;
    // Estado: No estoy seguro si ponerlo aqui o representarlo  segun la cola en la que este //
    private String estado; 
    private int prioridad;

    public Proceso() {
	this.id = -1;
	this.tiempo_llegada = -1;
	this.tiempo_espera = -1;
	this.prio=0;
	this.quantum=100;
	this.rafagas_cpu = null;
	this.prioridad = 0;
	this.estado = null;
    }

    public Proceso(int id, int tiempo_llegada, int tiempo_espera, 
		    LinkedList<Integer> rafagas_cpu, int prioridad, String estado) {

	this.id = id;
	this.tiempo_llegada = tiempo_llegada;
	this.tiempo_espera = tiempo_espera;
	this.rafagas_cpu = rafagas_cpu;
	this.prio = prioridad;
	this.estado = estado;
    }

    public void effective_prio(){
	/* Conversion o Mapping entre el sleep_avg(0-MAX_SLEEP_AVG) y el bonus (0-MAX_BONUS)*/
	/*El numero resultante estara entre 0 y 10*/
	int current_bonus= this.sleep_avg * MAX_BONUS/ MAX_SLEEP_AVG;
	/*Como el rango permitido para el bonus esta entre -5 y 5, se resta 5*/
	int bonus= current_bonus - MAX_BONUS /2;
	//this.prio= max(-20,min(this.static_prio - bonus,19));
	this.prio= this.static_prio-bonus;
	if(this.prio>19) this.prio=19;
	if(this.prio<-20) this.prio=-20;
	/* Mapeo del rango [-20,19] a [100,139]*/
	this.prio= this.prio +120;
    }

    public void setId(int id) {
	this.id = id;
    }

    public int getId() {
	return id;
    }
 
    public void setTiempo_llegada(int tiempo_llegada) {
	this.tiempo_llegada = tiempo_llegada;
    }

    public int getTiempo_llegada() {
	return tiempo_llegada;
    }
    
    public void setTiempo_espera(int tiempo_espera) {
	this.tiempo_espera = tiempo_espera;
    }

    public int getTiempo_espera() {
	return tiempo_espera;
    }

    public void setRafagas_cpu(LinkedList<Integer> rafagas_cpu) {
	this.rafagas_cpu = rafagas_cpu;
    }

    public LinkedList<Integer> getRafagas_cpu() {
	return rafagas_cpu;
    }

    public void setPrioridad(int prioridad) {
	this.prio = prio;
    }
    
    public int getPrioridad() {
	return prio;
    }

    public void setEstado(String estado) {
	this.estado = estado;
    }

    public String getEstado() {
	return estado;
    }

    public boolean equals(Proceso proceso) {
	if (this.id == proceso.id)
	    return true;
	else 
	    return false;
    }
    
}

