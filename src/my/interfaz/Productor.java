/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.interfaz;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;


/**
 *
 * @author rodolfo
 */
public class Productor extends Thread {
        private Semaphore semFull; // Semaforo que previene el underflow (0)
        private Semaphore semBuffer; // Semaforo que previene el overflow (n)
        private Semaphore semExclusion; // Semaforo que garantiza la exclusion mutua (1)
        private String pieza;
        private Almacen alm;
        private int duracion;
        private JTextField text; 
        private String cont;
        private boolean condicion = true,pausa = true;

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }

    public int getCantNeu() {
        return cantNeu;
    }

    public int getCantRueda() {
        return cantRueda;
    }

    public int getCantBuje() {
        return cantBuje;
    }

    public int getCantMarco() {
        return cantMarco;
    }
        private int cantNeu = 0;
        private int cantRueda = 0;
        private int cantBuje = 0;
        private int cantMarco = 0;

    public Productor(JTextField text,Almacen alm,Semaphore semBuffer, Semaphore semFull, Semaphore semExclusion,String pieza,int duracion) {
        this.text = text;
        this.semBuffer = semBuffer;
        this.semFull = semFull;
        this.semExclusion = semExclusion;
        this.pieza = pieza;
        this.alm = alm;
        this.duracion = duracion;
    }

    
    public  void producir(String pieza){
        
        switch(pieza.toLowerCase()){
                
                
                case "neumatico":
                             //Se cuenta la cantidad almacenada actualmente en el buffer y luego se actualiza la interfaz grafica
                             //correspondiente
                            cantNeu = alm.cantidadAlmacenada();
                            cont=String.valueOf(cantNeu);
                            text.setText(cont);
                            break;
                            
                                           
                case "buje":
                            //Se cuenta la cantidad almacenada actualmente en el buffer y luego se actualiza la interfaz grafica 
                            //correspondiente
                            cantBuje = alm.cantidadAlmacenada();
                            cont=String.valueOf(cantBuje);
                            text.setText(cont);
                            break;
//                            
        
                case "marco":  
                             //Se cuenta la cantidad almacenada actualmente en el buffer y luego se actualiza la interfaz grafica
                             //correspondiente
                            cantMarco = alm.cantidadAlmacenada();
                            cont=String.valueOf(cantMarco);
                            text.setText(cont);
                            break;
//                          
                       
                        
                case "rueda":
                            cantRueda = alm.cantidadAlmacenada();
                            cont=String.valueOf(cantRueda);
                            text.setText(cont);
                            break;
//                            
                default:
                        System.out.println("Error en la pieza, ingrese de nuevo");  
                        break;
                     
        }   
  
    }
    

    
        
        @Override
        public void run(){    
            while(condicion){
                try {
                    
                    //condicion para que el hilo entre en pausa
                    if(pausa==false){
                        Thread.sleep(500);
                    }
                    else{ 
                    semBuffer.acquire();  
                    //Seccion critica donde se accede al buffer
                    semExclusion.acquire();
                    producir(pieza);
                    alm.almacenar();
                    semExclusion.release();
                    //Fin de seccion critica
                    semFull.release();   
                    alm.mostrar();
                    Thread.sleep(duracion);
                    }
                    
        } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
                
            }       
        }
    
        
    
}
