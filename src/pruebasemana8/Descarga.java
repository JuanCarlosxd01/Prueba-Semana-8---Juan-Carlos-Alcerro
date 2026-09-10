
package pruebasemana8;

import java.util.Random;
import javax.swing.*;

public class Descarga implements Runnable{
    
    private String nombre;
    private JProgressBar barra;
    private JTextArea bitacora;
    private DescargaPanel ventana;
    
    public volatile boolean cancelado = false;
    
    public Descarga(String nombre, JProgressBar barra, JTextArea bitacora, DescargaPanel ventana){
        this.nombre = nombre;
        this.barra = barra;
        this.bitacora = bitacora;
        this.ventana = ventana;
    }
    
    public void cancelar(){
        cancelado = true;
    }
    
    @Override
    public void run(){
        Random random = new Random();
        int progreso = 0;
        
        while(progreso < 100 && !cancelado){
            try{
                int velocidad = random.nextInt(500) + 200;
                Thread.sleep(velocidad);
            } catch(InterruptedException e){
                return;
            }
            
            if(cancelado){
                break;
            } 
            progreso += 1;
            if(progreso > 100){
                progreso = 100;
            }
            
            int valor = progreso;
            SwingUtilities.invokeLater(() ->{
                barra.setValue(valor);  
                bitacora.append(nombre + ": descarga al " + valor + "%\n");
            });    
        }
        if(cancelado){
                SwingUtilities.invokeLater(() ->{
                    bitacora.append(nombre + ": descarga cancelada\n");
                });
            }
        else{
            SwingUtilities.invokeLater(() ->{
            bitacora.append(nombre + ": descarga completada\n");
        });
        }
            ventana.descargaTerminada();
        
    }
    
}
