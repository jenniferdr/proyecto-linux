import java.util.Stack;

public class Caja1{
    Stack<Integer> pid;
    Stack<Integer> ini;
    Stack<Integer> fin;
    @SuppressWarnings("unchecked")
    public Caja1(){
	pid = new Stack();
        ini = new Stack();
        fin = new Stack();
    }

    public void push(int p, int i, int f){
	pid.add(p);
        ini.add(i);
        fin.add(f);
        
    }

    public int popPid(){
	return pid.pop();
     
    }
   public int popIni(){
	return ini.pop();
     
    }
   public int popFin(){
	return fin.pop();
     
    }

    public boolean emptyPid(){
	return pid.empty();
    }
     public boolean emptyIni(){
	return ini.empty();
    }
      public boolean emptyFin(){
	return fin.empty();
    }
   
   
}