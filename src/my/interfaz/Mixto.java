/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.interfaz;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.Semaphore;
import javax.swing.JTextField;

/**
 *
 * @author rodolfo
 */
public class Mixto extends Thread{
    
        
        //Semaforos que indican cuando consumir Neumaticos
        private Semaphore semFullNeu; // Semaforo que previene el underflow (0)
        private Semaphore semBufferNeu; // Semaforo que previene el overflow (n)
        private Semaphore semExclusionNeu; // Semaforo que garantiza la exclusion mutua (1)
        
        //Semaforos que indican cuando consumir Bujes
        private Semaphore semFullBuje; // Semaforo que previene el underflow (0)
        private Semaphore semBufferBuje; // Semaforo que previene el overflow (n)
        private Semaphore semExclusionBuje; // Semaforo que garantiza la exclusion mutua (1)
        
        //Semaforos que indican cuando introducir Ruedas en el Buffer
        private Semaphore semFullRueda; // Semaforo que previene el underflow (0)
        private Semaphore semBufferRueda; // Semaforo que previene el overflow (n)
        private Semaphore semExclusionRueda; // Semaforo que garantiza la exclusion mutua (1)
        
        private Almacen alm,aNeu,aBuje;
        private int duracion,cantRueda=0;
        private String pieza;
        private JTextField text;
        private JTextField buje;
        private JTextField neumatico;

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }
        private String cont;
        private boolean condicion = true,pausa=true;

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }


    public Mixto(JTextField buje,JTextField neumatico, JTextField text,Semaphore semFullNeu, Semaphore semBufferNeu, Semaphore semExclusionNeu, Semaphore semFullBuje, Semaphore semBufferBuje, Semaphore semExclusionBuje, Semaphore semFullRueda, Semaphore semBufferRueda, Semaphore semExclusionRueda, Almacen alm, Almacen aNeu, Almacen aBuje, int duracion,String pieza) {
        this.neumatico = neumatico;
        this.buje = buje;
        this.text = text;
        this.semFullNeu = semFullNeu;
        this.semBufferNeu = semBufferNeu;
        this.semExclusionNeu = semExclusionNeu;
        this.semFullBuje = semFullBuje;
        this.semBufferBuje = semBufferBuje;
        this.semExclusionBuje = semExclusionBuje;
        this.semFullRueda = semFullRueda;
        this.semBufferRueda = semBufferRueda;
        this.semExclusionRueda = semExclusionRueda;
        this.alm = alm;
        this.aNeu = aNeu;
        this.aBuje = aBuje;
        this.duracion = duracion;
        this.pieza = pieza;
    }
        
     public void producir(String pieza){
        
        switch(pieza.toLowerCase()){
          
                case "rueda":
                            cantRueda = alm.cantidadAlmacenada();
                            cont=String.valueOf(cantRueda);
                            text.setText(cont);
                        break;                        
                
                default:
                        System.out.println("Error en la pieza, ingrese de nuevo");              
        }   
    }
    
    
    
    @Override
        public void run(){
        
            while(condicion){
                
                try{
                    
                    //condicion para que el hilo entre en pausa
                    if(pausa==false){
                        Thread.sleep(500);
                    }
                    else{
                        
                        //se actualiza la interfaz grafica
                    int act = aBuje.cantidadAlmacenada();
                    int act2 = aNeu.cantidadAlmacenada();
                    int act3 = alm.cantidadAlmacenada();
                    neumatico.setText(String.valueOf(act));
                    buje.setText(String.valueOf(act2));
                    text.setText(String.valueOf(act3));
                    
                    //CONSUMIR 1 NEUMATICO
                    semFullNeu.acquire();
                    semExclusionNeu.acquire();
                    //Seccion Critica donde se accede al buffer compartido
                    aNeu.liberar();
                    //Fin de Seccion Critica
                    semExclusionNeu.release();
                    semBufferNeu.release();
                    
                    //CONSUMIR 1 BUJE
                    semFullBuje.acquire();
                    semExclusionBuje.acquire();
                    //Seccion Critica donde se accede al buffer compartido
                    aBuje.liberar();
                    //Fin de Seccion Critica
                    semExclusionBuje.release();
                    semBufferBuje.release();
                    
                    //PRODUCIR 1 RUEDA
                    semBufferRueda.acquire();  
                    //Seccion critica donde se accede al buffer compartido
                    semExclusionRueda.acquire();
                    producir(pieza);
                    alm.almacenar();
                    semExclusionRueda.release();
                    //Fin de seccion critica
                    semFullRueda.release();   
                    alm.mostrar();
                   
                    
                    
                    Thread.sleep(duracion);
                    }
                } catch (InterruptedException ex) {
            Logger.getLogger(Productor.class.getName()).log(Level.SEVERE, null, ex);
                }
      
            }
            
            
            
            
        }
}
