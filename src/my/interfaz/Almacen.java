/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.interfaz;

/**
 *
 * @author rodolfo
 */
public class Almacen {
    
    private int vec[] ;
    private int tam;
    private final String nombre;

    
    public Almacen(int tam,String nombre) {
        this.nombre = nombre;
        this.tam = tam;
        vec = new int[tam];
        for(int i = 0 ; i < tam ; i++){
            vec[i] = 0;
        }
    }

    public int[] getVec() {
        return vec;
    }

    public void setVec(int[] vec) {
        this.vec = vec;
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }
    
    public void almacenar(){
        
        for(int i = 0 ; i < tam ; i++){
            if(vec[i] == 0){
                vec[i] = 1;
                break;
            }
        }
    }
    
    public int cantidadAlmacenada(){
        
        int cantidad=1;
        for(int i = 0; i < tam ; i++){
            cantidad = cantidad + vec[i];
        }
        return cantidad;
    }
    
    public void mostrar(){
        System.out.println("\n"+nombre);
        for(int i = 0 ; i < tam ; i++){
            System.out.print(" ["+vec[i]+"] ");
            }
    }
    
    public void liberar(){
    
        for(int i = 0 ; i < tam ; i++){
            if(vec[i] == 1){
                vec[i] = 0;
                break;
            }
        }
    }

    
}
