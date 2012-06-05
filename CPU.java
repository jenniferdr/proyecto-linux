public class CPU {
    Proceso proceso_actual;
    int id;
    
    public CPU(int id){
	this.id = id;
	this.proceso_actual = null;
    }

    public void set_proc(Proceso proc){
	this.proceso_actual = proc;
    }

    public void usar_cpu(){
	System.out.print("Proceso usando CPU:");
	System.out.println(proceso_actual.getId());
    }
}