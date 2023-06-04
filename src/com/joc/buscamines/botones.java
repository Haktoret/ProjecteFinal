package com.joc.buscamines;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class botones extends JButton {

    boolean mina, descubierto, bandera, usado;
    int minas_adyacentes;
    int index;

    void cambiarimagen(String ruta) {
        ImageIcon icon1 = new ImageIcon(getClass().getResource(ruta));
        Icon icono1 = new ImageIcon(icon1.getImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_DEFAULT));
        setText(null);
        setIcon(icono1);
    }

    public botones(int in) {
        mina = false;
        minas_adyacentes = 0;
        index = in;
        descubierto = false;
        bandera = false;
        usado = false;
    }
}
