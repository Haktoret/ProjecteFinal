package com.joc.buscamines;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class cronometro extends Thread implements Runnable {

	 JLabel r;
	    private static int j;

	    public static int getJ() {
	        return j;
	    }

	    public static int getTiempoActual() {
	        return j;
	    }

	    public cronometro(JLabel r2) {
	        r = r2;
	    }
	    
	    public cronometro(JLabel r2, int tiempoInicial) {
	        r = r2;
	        j = tiempoInicial;
	    }

	    public void run() {
	        try {
	            for (j = getJ(); j < 1000; j++) {
	                r.setText("" + j);
	                Thread.sleep(1000);
	            }
	            r.setText("Infinito");
	        } catch (InterruptedException ex) {
	            Logger.getLogger(cronometro.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    }
}