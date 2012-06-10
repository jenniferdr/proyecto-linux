import java.util.Stack;

public class Caja{
    Stack<String> mensajes;
   
    @SuppressWarnings("unchecked")
    public Caja(){
	mensajes = new Stack();
    }

    public void push(String mensaje){
	mensajes.add(mensaje);
    }

    public String pop(){
	return mensajes.pop();
    }
    
    public boolean empty(){
	return mensajes.empty();
    }
   
}