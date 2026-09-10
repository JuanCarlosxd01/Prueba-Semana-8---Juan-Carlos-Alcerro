
package pruebasemana8;

import java.awt.*;
import javax.swing.*;

public class DescargaPanel extends JFrame{
    
    private JProgressBar barra1;
    private JProgressBar barra2;
    private JProgressBar barra3;
    private JButton btnIniciar;
    private JButton btnCancelar;
    private JTextArea bitacora;
    private Descarga descarga1;
    private Descarga descarga2;
    private Descarga descarga3;
    
    private int terminadas = 0;
    
    public DescargaPanel(){
        setTitle("Descarga multiple");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout(10, 10));
        
        JPanel panelBarras = new JPanel();
        panelBarras.setLayout(new GridLayout(6, 1 , 5, 5));
        
        barra1 = new JProgressBar();
        barra2 = new JProgressBar();
        barra3 = new JProgressBar();
        
        panelBarras.add(new JLabel("Archivo1"));
        panelBarras.add(barra1);
        panelBarras.add(new JLabel("Archivo2"));
        panelBarras.add(barra2);
        panelBarras.add(new JLabel("Archivo3"));
        panelBarras.add(barra3);
        
        bitacora = new JTextArea();
        bitacora.setEditable(false);
        bitacora.setFocusable(false);
        
        JScrollPane scroll = new JScrollPane(bitacora);
        JPanel panelBotones = new JPanel();
        btnIniciar = new JButton("Iniciar descargas");
        btnCancelar = new JButton("Cancelar descargas");
        panelBotones.add(btnIniciar);
        panelBotones.add(btnCancelar);
        
        panelPrincipal.add(panelBarras, BorderLayout.NORTH);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        add(panelPrincipal);
        
        btnIniciar.addActionListener(e ->{
            iniciarDescarga();
        });
        
        btnCancelar.addActionListener(e ->{
            cancelarDescargas();
        });
        
        setVisible(true);
    }
    
    private void iniciarDescarga(){
        btnIniciar.setEnabled(false);
        barra1.setValue(0);
        barra2.setValue(0);
        barra3.setValue(0);
        bitacora.setText("");
        terminadas = 0;
        
        descarga1 = new Descarga("Archivo1", barra1, bitacora, this);
        descarga2 = new Descarga("Archivo2", barra2, bitacora, this);
        descarga3 = new Descarga("Archivo3", barra3, bitacora, this);
        
        Thread hilo1 = new Thread(descarga1);
        Thread hilo2 = new Thread(descarga2);
        Thread hilo3 = new Thread(descarga3);
        
        hilo1.start();
        hilo2.start();
        hilo3.start();
        
        bitacora.append("Descargas iniciadas...\n");
    }
    
    private void cancelarDescargas(){
        if(descarga1 != null){
            descarga1.cancelar();
        }
        if(descarga2 != null){
            descarga2.cancelar();
        }
        if(descarga3 != null){
            descarga3.cancelar();
        }
    }
    
    public synchronized void descargaTerminada(){
        terminadas++;
        
        if(terminadas == 3){
            SwingUtilities.invokeLater(() ->{
                btnIniciar.setEnabled(true);
            });
        }
    }
}
