/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.interfaz;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 *
 * @author rodolfo
 */
public class Archivo {

     
    int[] vec;
    
    public Archivo()
    {
        vec = new int[19];
    }
    
    public int[] Leer(){
        
        Scanner scanner =null;
        
        try
        {
                FileReader fichero = new FileReader("datos.txt");
                BufferedReader reader = new BufferedReader(fichero);

                String line;
                int j = 0;

                while ( ( line = reader.readLine() ) != null )
                {
                    StringTokenizer sTokenizer = new StringTokenizer(line, ":");

                    while( sTokenizer.hasMoreElements() )
                    {
                        sTokenizer.nextElement();
                        int num = Integer.parseInt(sTokenizer.nextToken().toString());
                        vec[j] = num;
                        j++;
                    }
                }
                }
            catch (Exception ex) {
                System.out.println("Mensaje 1 : "+ex.getMessage());
            }finally {
                // Cerramos el fichero
                try {
                    if (scanner != null)
                        scanner.close();
                    } catch (Exception ex2) {
                            System.out.println("Mensaje 2: "+ex2.getMessage());
                    }
            }
        return vec;
}

}
