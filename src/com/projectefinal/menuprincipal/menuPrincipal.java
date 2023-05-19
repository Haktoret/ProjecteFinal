package com.projectefinal.menuprincipal;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class menuPrincipal extends JFrame{
	
	public void menu(String nombreUser) {
		Container pagina = this.getContentPane();
		pagina.setLayout(new BorderLayout());
		
		JPanel pSaludo = new JPanel();
		JLabel saludo = new JLabel();
		saludo.setText("Hola: "+nombreUser);
		Font fuente = new Font("Arial", Font.BOLD, 30);
		saludo.setFont(fuente);
		pSaludo.add(saludo);
		pagina.add(pSaludo, BorderLayout.NORTH);
		
		JPanel imagenes = new JPanel();
		imagenes.setLayout(new GridLayout(0,3));
		JLabel buscaMinas = new JLabel();
		String rutaMinas = "buscaminas.jpeg";
		redimensionar(rutaMinas, buscaMinas);
		
		JLabel juegoVida = new JLabel();
		String rutaVida = "juegoVida.png";
		redimensionar(rutaVida, juegoVida);

		
		JLabel pixelArt = new JLabel();
		String rutaArt = "pixel.jpg";
		redimensionar(rutaArt, pixelArt);
		
		imagenes.add(pixelArt);
		imagenes.add(juegoVida);
		imagenes.add(buscaMinas);

		pagina.add(imagenes, BorderLayout.CENTER);
		
		JPanel botones = new JPanel();
		botones.setLayout(new GridLayout(0,3));
		JButton boton1 = new JButton("LogOut");
		JButton boton2 = new JButton("");
		JButton boton3 = new JButton("Borrar cuenta");
		
		botones.add(boton1);
		botones.add(boton2);
		botones.add(boton3);
		
		pagina.add(botones, BorderLayout.SOUTH);
		
		boton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
		            // Cargar el driver de MySQL
		           //Class.forName("org.mysql.jdbc.Driver");

		            // Establecer la conexión con la base de datos
		            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
		           
		            Statement s = c.createStatement();
		           
		            s.executeQuery("DELETE FROM login WHERE user = "+menu.nombreUserLog());
		        } catch (Exception e2) {
		            System.out.println("Error al conectar con la base de datos: " + e2.getMessage());
		        } 
			}
			
		});


		this.setVisible(true);
		this.setSize(800,500);
	}
	
	public void redimensionar(String imagen, JLabel jlabel) {
		ImageIcon icona = new ImageIcon(imagen);

		// La convertim a imatge
		Image imatge = icona.getImage();

		// Obtenim las medidas de la imagen original
		int anchoOriginal = icona.getIconWidth();
		int alturaOriginal = icona.getIconHeight();

		// Redimensionamos al nuevo ancho manteniendo la proporción
		
		int nuevaAltura = (alturaOriginal * 200) / anchoOriginal;

		// redimensionem per a la mida de la finestra, per exemple a 500 x 400
		Image novaImage = imatge.getScaledInstance(200, nuevaAltura, java.awt.Image.SCALE_SMOOTH);

		// Reconvertim a Icon, per poder-la ficar en JLabel
		ImageIcon nouIcon = new ImageIcon(novaImage);
		jlabel.setIcon(nouIcon);
		
	}

}
