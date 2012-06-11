/**
 *  Clase proceso
 *       
 */

import java.util.*;
import java.io.*;
import java.lang.Math;

public class Proceso {
    
    private int id;
    private int tiempo_llegada;
    private int tiempo_espera;
    private LinkedList<Integer> rafagas_cpu;
    private int rafaga;
    private static final int static_prio=0;
    private int prio;
    /* Tiempo que el proceso ha dormido menos el que ha consumido en CPU*/
    private int sleep_avg;
    private static final int MAX_SLEEP_AVG=70;
    private static final int MAX_BONUS=10;
    private int quantum;

    private int quantumRestante;

    // Estado: No estoy seguro si ponerlo aqui o representarlo  segun la cola en la que este //
    private String estado; 
    private int prioridad;
    private int enCPU;
    
    // Variables para estadisticas de tiempo
    private int tiempo_inicio;
    private int tiempo_final;
    private int espera_IOini;
    private int espera_IOacu;
    private int espera_CPUini;
    private int espera_CPUacu;
    private int espera_total;
    private int tiempo_total;

    
    public Proceso() {
	this.id = -1;
	this.tiempo_llegada = -1;
	this.tiempo_espera = -1;
	this.prio=0;
	this.quantum=100;
	this.rafagas_cpu = null;
	this.prioridad = 0;
	this.estado = null;
	this.enCPU = -1;


	this.tiempo_inicio = -1;
	this.tiempo_total = 0;
	this.tiempo_final =  -1;
	this.espera_IOini =  -1;
	this.espera_IOacu =  0;
	this.espera_CPUini = -1;
	this.espera_CPUacu = 0;
	this.espera_total =  0;
    }

    public Proceso(int id, int tiempo_llegada, int tiempo_espera, 
		    LinkedList<Integer> rafagas_cpu, int prioridad, String estado) {

	this.id = id;
	this.tiempo_llegada = tiempo_llegada;
	this.tiempo_espera = tiempo_espera;
	this.rafagas_cpu = rafagas_cpu;
	this.prio = prioridad;
	this.estado = estado;
	this.prio=0;
	this.prioridad = 0;
	this.enCPU = -1;

	
	this.tiempo_inicio = -1;
	this.tiempo_total = 0;
	this.tiempo_final =  -1;
	this.espera_IOini =  -1;
	this.espera_IOacu =  0;
	this.espera_CPUini = -1;
	this.espera_CPUacu = 0;
	this.espera_total =  0;
	
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

    public void setRafaga(int rafaga) {
	this.rafaga = rafaga;
    }

    /*Precondicion: No es la ultima rafaga*/
    public int getRafaga(){
	if(this.rafaga==0){
	    Integer next = rafagas_cpu.pollFirst();
	    if(next == null){
		//Se terminaron las rafagas!
	    }
	    else {
		this.rafaga = next;
	    }
	}
	return this.rafaga;
    }

    public boolean quedanRafagas(){
	return (rafagas_cpu.size()!=0);
    }

    public void setPrioridad(int prioridad) {
	this.prio = prio;
    }
    
    public int getPrioridad() {
	return prio;
    }

    public void setSleep_avg(int sleepAvg) {
	this.sleep_avg =(sleepAvg<=MAX_SLEEP_AVG)? sleepAvg :MAX_SLEEP_AVG;
	if(this.sleep_avg<0)this.sleep_avg=0;
    }

    public int getSleep_avg() {
	return this.sleep_avg;
    }

    public int getQuantum() {
	return this.quantum;
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

    public int getEnCPU(){
	return this.enCPU;
    }

    public void setEnCPU(int enCPU) {
	this.enCPU = enCPU;
    }

    public void setTiempo_inicio(int tiempoInicio) {
	this.tiempo_inicio = tiempoInicio;
    }

    public int getTiempo_inicio(){
	return this.tiempo_inicio;
    }
    
    public void setTiempo_final(int tiempoFinal) {
	this.tiempo_final = tiempoFinal;
    }
    
    public int getTiempo_final(){
	return this.tiempo_final;
    }

    public void setTiempo_total(int tiempoTotal) {
	this.tiempo_total = tiempoTotal;
    }
    
    public int getTiempo_total(){
	return this.tiempo_total;
    }

    
    public void setEspera_IOini(int espera_IOini) {
	this.espera_IOini = espera_IOini;
    }

    public int getEspera_IOini(){
	return this.espera_IOini;
    }
    
    public void setEspera_IOacu(int espera_IOacu) {
	this.espera_IOacu = espera_IOacu;
    }

    public int getEspera_IOacu(){
	return this.espera_IOacu;
    }

    public void setEspera_CPUini(int espera_CPUini) {
	this.espera_CPUini = espera_CPUini;
    }

    public int getEspera_CPUini(){
	return this.espera_CPUini;
    }
    
    public void setEspera_CPUacu(int espera_CPUacu) {
	this.espera_CPUacu = espera_CPUacu;
    }

    public int getEspera_CPUacu(){
	return this.espera_CPUacu;
    }

    public void setEspera_total(int espera_total) {
	this.espera_total = espera_total;
    }

    public int getEspera_total(){
	return this.espera_total;
    }


    public int getQuantumRestante(){
	return this.quantumRestante;
    }

    public void setQuantumRestante(int q){
	this.quantumRestante = q;
    }
    
}
