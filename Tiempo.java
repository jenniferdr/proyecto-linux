/*  Clase Tiempo
 *  Guarda la variable tiempo, que es la que consultaran
 *  las otras clases para saber en que unidad de tiempo  
 *  se encuentran.
 */


public class Tiempo {
    private int tiempo = 0;
    
    public Tiempo() {
	this.tiempo = 0;
    }

    public void incTiempo() {
	this.tiempo++;
    }

    public int getTiempo(){
	return this.tiempo;
    }

}


	
      
	
