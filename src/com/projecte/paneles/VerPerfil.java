package com.projecte.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.projectefinal.menuprincipal.menu;

public class VerPerfil extends JPanel{
public VerPerfil(String nom, String apellidos, String correo, String poblacio, byte[] imageBytes) {
		
		this.setLayout(new BorderLayout());
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		JPanel pVerImagen = new JPanel();
		pVerImagen.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel verPerfil = new JPanel();
		verPerfil.setLayout(new GridLayout(6,0));
		Font fuenteVer = new Font("Roboto",Font.BOLD, 20);
		JLabel verNom = new JLabel("",SwingConstants.CENTER);
		verNom.setForeground(Color.WHITE);
		verNom.setBackground(Color.GRAY);
		verNom.setOpaque(true);
		verNom.setBorder(border);
		verNom.setFont(fuenteVer);
		JLabel verApellidos = new JLabel("", SwingConstants.CENTER);
		verApellidos.setForeground(Color.WHITE);
		verApellidos.setBackground(Color.GRAY);
		verApellidos.setOpaque(true);
		verApellidos.setBorder(border);
		verApellidos.setFont(fuenteVer);
		JLabel verPoblacion = new JLabel("", SwingConstants.CENTER);
		verPoblacion.setForeground(Color.WHITE);
		verPoblacion.setBackground(Color.GRAY);
		verPoblacion.setOpaque(true);
		verPoblacion.setBorder(border);
		verPoblacion.setFont(fuenteVer);
		JLabel verImagen = new JLabel("", SwingConstants.CENTER);
		
		JLabel verCorreo = new JLabel("", SwingConstants.CENTER);
		verCorreo.setForeground(Color.WHITE);
		verCorreo.setBackground(Color.GRAY);
		verCorreo.setOpaque(true);
		verCorreo.setBorder(border);
		verCorreo.setFont(fuenteVer);
		JPanel paraBoton = new JPanel();
		paraBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton atras = new JButton("Volver atras");
		paraBoton.add(atras);
		
		verNom.setText("NOMBRE: "+nom);
    	verApellidos.setText("APELLIDO: "+apellidos);
    	verCorreo.setText("CORREO: "+correo);
    	verPoblacion.setText("POBLACION: "+poblacio);
    	redimensionarImagenBDD(imageBytes, verImagen);

		
		pVerImagen.add(verImagen);
		verPerfil.add(pVerImagen);
		verPerfil.add(verNom);
		verPerfil.add(verApellidos);
		verPerfil.add(verPoblacion);
		verPerfil.add(verCorreo);
		//verPerfil.add(verImagen);
		verPerfil.add(paraBoton);
		
		this.add(verPerfil);
		
		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				verNom.setText("");
            	verApellidos.setText("");
            	verCorreo.setText("");
            	verPoblacion.setText("");
            	verImagen.setIcon(null);
				menu mRegistro = (menu) SwingUtilities.getWindowAncestor(VerPerfil.this);
				
				mRegistro.getContentPane().removeAll();
				PanelInicio inici = new PanelInicio(correo);
				mRegistro.getContentPane().add(inici);
				mRegistro.getContentPane().revalidate();
				mRegistro.getContentPane().repaint();
				mRegistro.setSize(700, 550);
			}
			
		});
		
		
		
		
	}
	
	public void redimensionarImagenBDD(byte[] imageBytes, JLabel etiquetaImagen) {
		ImageIcon icona = new ImageIcon(imageBytes);

		// La convertim a imatge
		Image imatge = icona.getImage();

		// Obtenim las medidas de la imagen original
		int anchoOriginal = icona.getIconWidth();
		int alturaOriginal = icona.getIconHeight();

		// Redimensionamos al nuevo ancho manteniendo la proporción
		
		int nuevaAltura = (alturaOriginal * 100) / anchoOriginal;

		// redimensionem per a la mida de la finestra, per exemple a 500 x 400
		Image novaImage = imatge.getScaledInstance(100, nuevaAltura, java.awt.Image.SCALE_SMOOTH);

		// Reconvertim a Icon, per poder-la ficar en JLabel
		ImageIcon nouIcon = new ImageIcon(novaImage);
		etiquetaImagen.setIcon(nouIcon);
	}
}
