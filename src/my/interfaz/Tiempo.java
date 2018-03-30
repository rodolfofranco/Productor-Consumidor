/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.interfaz;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextField;

/**
 *
 * @author rodolfo
 */
public class Tiempo extends Thread {
    
    
    private JTextField text;
    private int time;
    private boolean pausa=true;
    private int duracion;
    
    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public Tiempo(JTextField text, int time) {
        this.text = text;
        this.time = time;
        this.duracion = time;
    }
    
    @Override
    public void run(){
        
        while(true){
            try{
                
                //condicion para que entre en pausa
                if(pausa==false || time==0){
                    Thread.sleep(500);
                }
                else{
                
                String tiempo = String.valueOf(time/duracion) + " dias" ;
                text.setText(tiempo);
                Thread.sleep(duracion);
                time = time + duracion;
                }
            }catch(InterruptedException ex) {
            Logger.getLogger(Tiempo.class.getName()).log(Level.SEVERE, null, ex);
               
            }
        }
        
    }
}
