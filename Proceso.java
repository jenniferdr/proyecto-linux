/**
 *  Clase proceso
 *       
 */

import java.util.*;
import java.io.*;


public class Proceso {
    
    private int id;
    private int tiempo_llegada;
    private int tiempo_espera;
    private LinkedList<Integer> rafagas_cpu;
    private int prioridad;
    // Estado: No estoy seguro si ponerlo aqui o representarlo  segun la cola en la que este //
    private String estado; 

    public Proceso() {
	this.id = -1;
	this.tiempo_llegada = -1;
	this.tiempo_espera = -1; 
	this.rafagas_cpu = null;
	this.prioridad = -1;
	this.estado = null;

    }

    public Proceso(int id, int tiempo_llegada, int tiempo_espera, 
		    LinkedList<Integer> rafagas_cpu, int prioridad, String estado) {

	this.id = id;
	this.tiempo_llegada = tiempo_llegada;
	this.tiempo_espera = tiempo_espera;
	this.rafagas_cpu = rafagas_cpu;
	this.prioridad = prioridad;
	this.estado = estado;
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
	this.prioridad = prioridad;
    }
    
    public int getPrioridad() {
	return prioridad;
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

