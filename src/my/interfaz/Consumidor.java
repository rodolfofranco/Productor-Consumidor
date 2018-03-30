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
public class Consumidor extends Thread {
    
        // Semaforos que indican cuando consumir Marcos
        private Semaphore semFullMarco; // Semaforo que previene el underflow (0)
        private Semaphore semBufferMarco; // Semaforo que previene el overflow (n)
        private Semaphore semExclusionMarco; // Semaforo que garantiza la exclusion mutua (1)
        // Semaforos que indican cuando consumir Ruedas
        private Semaphore semFullRueda; // Semaforo que previene el underflow (0)
        private Semaphore semBufferRueda; // Semaforo que previene el overflow (n)
        private Semaphore semExclusionRueda; // Semaforo que garantiza la exclusion mutua (1)
        
        private String pieza;
        private int cantBicicleta=0;
        private int cantTriciclo=0;
        private Almacen aMarco,aRueda;
        private int duracion;
        String cont;
        JTextField text,rueda,marco;
        private boolean condicion = true,pausa=true;
        private int cantidadMaxima;
        private Tiempo vec[];

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public boolean isCondicion() {
        return condicion;
    }

    public void setCondicion(boolean condicion) {
        this.condicion = condicion;
    }
      

    public Consumidor (Tiempo vec[],int cantidadMaxima, JTextField rueda,JTextField marco,JTextField text,int duracion,Semaphore semFullRueda, Semaphore semBufferRueda, Semaphore semExclusionRueda,Semaphore semFullMarco, Semaphore semBufferMarco, Semaphore semExclusionMarco,String pieza,Almacen aMarco,Almacen aRueda) {
        this.vec = vec;
        this.cantidadMaxima = cantidadMaxima;
        this.marco = marco;
        this.rueda = rueda;
        this.duracion = duracion;
        this.text = text;
        // Semaforos que indican cuando consumir Marcos
        this.semFullMarco = semFullMarco;
        this.semBufferMarco = semBufferMarco;
        this.semExclusionMarco = semExclusionMarco;
        
        // Semaforos que indican cuando consumir Ruedas
        this.semFullRueda = semFullRueda;
        this.semBufferRueda = semBufferRueda;
        this.semExclusionRueda = semExclusionRueda;
        
        this.pieza = pieza;
        this.aMarco = aMarco;
        this.aRueda = aRueda;
    }

    public int getCantBicicleta() {
        return cantBicicleta;
    }

    public void setCantBicicleta(int cantBicicleta) {
        this.cantBicicleta = cantBicicleta;
    }

    public int getCantTriciclo() {
        return cantTriciclo;
    }

    public void setCantTriciclo(int cantTriciclo) {
        this.cantTriciclo = cantTriciclo;
    }
        
    public void consumir(String pieza){
        switch(pieza.toLowerCase()){
                
            case "bicicleta":
                cantBicicleta++;
                cont=String.valueOf(cantBicicleta);
                text.setText(cont);
                
                break;
                
            case "triciclo":
                cantTriciclo++;
                cont=String.valueOf(cantTriciclo);
                text.setText(cont);
                
                break;
                
            default:
                System.out.println("Error de pieza");
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
                    //condicion de parada para cuando se ensamblen las bicicletas requeridas
                    else if(cantBicicleta == cantidadMaxima){
                        pausa=false;
                        vec[0].setPausa(false);
                    }//condicion de parada para cuando se ensamblen los triciclos requeridos
                    else if(cantTriciclo == cantidadMaxima){
                        pausa=false;
                        vec[1].setPausa(false);
                    }
                    else{
                    if(pieza=="bicicleta"){
                        // Consumir MARCO
                        semFullMarco.acquire();
                        semExclusionMarco.acquire();
                        //Seccion Critica donde se accede al buffer compartido
                        aMarco.liberar();
                        //Fin de Seccion Critica
                        semExclusionMarco.release();
                        semBufferMarco.release();
                        // Consumir RUEDA
                        semFullRueda.acquire();
                        semExclusionRueda.acquire();
                        //Seccion Critica donde se accede al buffer compartido
                        aRueda.liberar();
                        aRueda.liberar();
                        //Fin de Seccion Critica
                        semExclusionRueda.release();
                        semBufferRueda.release();
                        consumir(pieza);
                        
                        
                        //se actualiza la interfaz grafica
                        int act = aRueda.cantidadAlmacenada();
                        int act2 = aMarco.cantidadAlmacenada();
                        rueda.setText(String.valueOf(act));
                        marco.setText(String.valueOf(act2));
                        
                        Thread.sleep(duracion);
                        
                    }
                    else if(pieza == "triciclo"){
                        // Consumir MARCO
                        semFullMarco.acquire();
                        semExclusionMarco.acquire();
                        //Seccion Critica donde se accede al buffer compartido
                        aMarco.liberar();
                        //Fin de Seccion Critica
                        semExclusionMarco.release();
                        semBufferMarco.release();
                        // Consumir RUEDA
                        semFullRueda.acquire();
                        semExclusionRueda.acquire();
                        //Seccion Critica donde se accede al buffer compartido
                        aRueda.liberar();
                        aRueda.liberar();
                        aRueda.liberar();
                        //Fin de Seccion Critica
                        semExclusionRueda.release();
                        semBufferRueda.release();
                        consumir(pieza);
                      
                        
                        //se actualiza la interfaz grafica
                        int act = aRueda.cantidadAlmacenada();
                        int act2 = aMarco.cantidadAlmacenada();
                        rueda.setText(String.valueOf(act));
                        marco.setText(String.valueOf(act2));
                        
                        Thread.sleep(duracion);
                        
                    }
                    else{
                        
                    }
                  }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Consumidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }      
        }     
}