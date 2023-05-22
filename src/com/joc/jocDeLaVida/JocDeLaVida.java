package com.joc.jocDeLaVida;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JocDeLaVida {

	public static void main(String[] args) {
		// Contenidor d'alt nivell: finestra principal
		JFrame vida = new JFrame("Joc de la vida");
		vida.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Panell de contingut
		Container panell = vida.getContentPane();
		panell.setLayout(new GridBagLayout());

		//Botons de tamany
		JButton b1 = new JButton("Xicotet");
		JButton b2 = new JButton("Mitjà");
		JButton b3 = new JButton("Gran");
		//b1.setBorder(BorderFactory.createLineBorder(Color.black));

		// Crear las restricciones de celda para los botones
		        GridBagConstraints constraints = new GridBagConstraints();
		        constraints.gridx = 0;
		        constraints.gridy = 0;
		        constraints.insets.set(10, 10, 10, 10); // Agregar margen a los botones
		        constraints.anchor = GridBagConstraints.CENTER; // Centrar los botones horizontalmente

		        // Agregar el primer botón
		        panell.add(b1, constraints);

		        // Incrementar la posición en el eje X
		        constraints.gridx++;

		        // Agregar el segundo botón
		        panell.add(b2, constraints);

		        // Incrementar la posición en el eje X
		        constraints.gridx++;

		        // Agregar el tercer botón
		        panell.add(b3, constraints);

		      //JPanel
		JPanel pantalla = new JPanel();
		JPanel barra = new JPanel();
		pantalla.setBackground(Color.gray);
		barra.setBackground(Color.gray);

		//Botones barra
		JButton atras = new JButton("TAMANY");
		JButton ajuda = new JButton("AJUDA");
		JButton start = new JButton("START");
		JButton next = new JButton("NEXT");
		JButton reset = new JButton("RESET");

		//Anyadir a JPanel
		barra.add(atras);
		barra.add(ajuda);
		barra.add(start);
		barra.add(next);
		barra.add(reset);

		        b1.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panell.setLayout(new BorderLayout());
		pantalla.setLayout(new GridLayout(30, 30));

		//Eliminar panells
		panell.removeAll();
		pantalla.removeAll();

		JPanel[][] cuadros = new JPanel[30][30];
		//JPanels per a la pantalla
		for(int i = 0; i < 30; i++) {
			for(int j = 0; j < 30; j++) {
				cuadros[i][j] = new JPanel();
				cuadros[i][j].setBackground(Color.gray);
				cuadros[i][j].setBorder(BorderFactory.createLineBorder(Color.white));
				pantalla.add(cuadros[i][j]);
			}
		}
		panell.add(barra, BorderLayout.SOUTH);

		//Repintar panells
		panell.repaint();
		panell.revalidate();
		pantalla.repaint();
		pantalla.revalidate();

		vida.setSize(400, 400);
		panell.add(pantalla);
		}
		        
		        });
		        b2.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panell.setLayout(new BorderLayout());
		pantalla.setLayout(new GridLayout(60, 60));

		//Eliminar panells
		panell.removeAll();
		pantalla.removeAll();

		JPanel[][] cuadros = new JPanel[60][60];
		//JPanels per a la pantalla
		for(int i = 0; i < 60; i++) {
			for(int j = 0; j < 60; j++) {
				cuadros[i][j] = new JPanel();
				cuadros[i][j].setBackground(Color.gray);
				cuadros[i][j].setBorder(BorderFactory.createLineBorder(Color.white));
				pantalla.add(cuadros[i][j]);
			}
		}
		panell.add(barra, BorderLayout.SOUTH);

		//Repintar panells
		panell.repaint();
		panell.revalidate();
		pantalla.repaint();
		pantalla.revalidate();

		vida.setSize(800, 800);
		panell.add(pantalla);
		}
		        
		        });
		b3.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panell.setLayout(new BorderLayout());
		pantalla.setLayout(new GridLayout(60, 90));

		//Eliminar panells
		panell.removeAll();
		pantalla.removeAll();

		JPanel[][] cuadros = new JPanel[60][90];
		//JPanels per a la pantalla
		for(int i = 0; i < 60; i++) {
			for(int j = 0; j < 90; j++) {
				cuadros[i][j] = new JPanel();
				cuadros[i][j].setBackground(Color.gray);
				cuadros[i][j].setBorder(BorderFactory.createLineBorder(Color.white));
				pantalla.add(cuadros[i][j]);
			}
		}
		
		panell.add(barra, BorderLayout.SOUTH);

		//Repintar panells
		panell.repaint();
		panell.revalidate();
		pantalla.repaint();
		pantalla.revalidate();

		vida.setSize(1200, 800);
		panell.add(pantalla);
		}
		        
		        });
		        atras.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		panell.setLayout(new GridBagLayout());
		panell.removeAll();

		// Agregar el primer botón
		       panell.add(b1, constraints);

		       // Incrementar la posición en el eje X
		       constraints.gridx++;

		       // Agregar el segundo botón
		       panell.add(b2, constraints);

		       // Incrementar la posición en el eje X
		       constraints.gridx++;

		       // Agregar el tercer botón
		       panell.add(b3, constraints);
		       
		panell.repaint();
		panell.revalidate();
		vida.setSize(700, 600);
		}
		        
		        });
		        ajuda.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JOptionPane.showMessageDialog(vida, // contenidor d'alt nivell
		"Regles:\n"
		+"• Tota cèl·lula viva amb menys de dos veïnes vives mor (de solitud).\n"
		+"• Tota cèl·lula viva amb més de tres veïnes vives mor (d’excés de concentració).\n"
		+"• Tota cèl·lula viva amb dos o tres veïnes vives, segueix viva per a la següent\n"
		+ "   generació.\n"
		+"• Tota cèl·lula morta amb exactament tres veïnes vives torna a la vida.", // text
		"Ajuda", // títol del diàleg
		JOptionPane.INFORMATION_MESSAGE);
		}
		        
		        });
		// Donar aspecte a la finestra i mostrar
		// calculadora.pack();
		vida.setSize(700, 600);
		vida.setResizable(false);
		// vida.setAlwaysOnTop(true);
		vida.setVisible(true);
	}
}