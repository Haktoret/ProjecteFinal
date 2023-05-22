package com.joc.buscamines;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Buscamines extends JFrame {
	private JPanel[][] cuadricula;

	public Buscamines() {
		pedirTamanio();
		inicializarCuadricula();

		setTitle("BuscaMines");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);

		JPanel panelCuadricula = new JPanel(new GridLayout(cuadroMida, cuadroMida));
		panelCuadricula.setPreferredSize(new Dimension(cuadroMida * 20, cuadroMida * 20));
		panelCuadricula.setMaximumSize(panelCuadricula.getPreferredSize());

		for (int i = 0; i < cuadroMida; i++) {
			for (int j = 0; j < cuadroMida; j++) {
				cuadricula[i][j] = new JPanel();
				cuadricula[i][j].setBackground(Color.WHITE);
				cuadricula[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				cuadricula[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						JPanel panel = (JPanel) e.getSource();
						if (SwingUtilities.isLeftMouseButton(e)) {
							if (panel.getComponentCount() > 0) {
								panel.removeAll();
								panel.revalidate();
							} else {
								panel.setBackground(Color.GRAY);
								panel.removeMouseListener(this);
							}
						} else if (SwingUtilities.isRightMouseButton(e)) {
							if (panel.getComponentCount() > 0) {
								panel.removeAll();
								panel.revalidate();
							} else {
								try {
									BufferedImage image = ImageIO.read(new File("bandera.png"));
									Image scaledImage = image.getScaledInstance(panel.getWidth(), panel.getHeight(),
											Image.SCALE_SMOOTH);
									panel.removeAll();
									panel.add(new JLabel(new ImageIcon(scaledImage)));
									panel.revalidate();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				});
				panelCuadricula.add(cuadricula[i][j]);
			}
		}

		getContentPane().add(panelCuadricula, BorderLayout.CENTER);

		pack();
		setVisible(true);
	}

	private int cuadroMida;
	private int ventanaMida;

	private void pedirTamanio() {
		String[] options = { "Fácil", "Media", "Difícil" };
		int choice = JOptionPane.showOptionDialog(Buscamines.this, "Selecciona el nivel de dificultad", "Tamaño",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		if (choice == 0) {
			cuadroMida = 8;
			ventanaMida = 200;
		} else if (choice == 1) {
			cuadroMida = 16;
			ventanaMida = 300;
		} else if (choice == 2) {
			cuadroMida = 32;
			ventanaMida = 400;
		}
	}

	private void inicializarCuadricula() {
		cuadricula = new JPanel[cuadroMida][cuadroMida];

		for (int i = 0; i < cuadroMida; i++) {
			for (int j = 0; j < cuadroMida; j++) {
				cuadricula[i][j] = new JPanel();
				cuadricula[i][j].setBackground(Color.WHITE);
				cuadricula[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
				cuadricula[i][j].addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						JPanel panel = (JPanel) e.getSource();
						if (SwingUtilities.isLeftMouseButton(e)) {
							if (panel.getComponentCount() > 0) {
								panel.removeAll();
								panel.revalidate();
							} else {
								panel.setBackground(Color.GRAY);
								panel.removeMouseListener(this);
							}
						} else if (SwingUtilities.isRightMouseButton(e)) {
							if (panel.getComponentCount() > 0) {
								panel.removeAll();
								panel.revalidate();
							} else {
								try {
									BufferedImage image = ImageIO.read(new File("bandera.png"));
									Image scaledImage = image.getScaledInstance(panel.getWidth(), panel.getHeight(),
											Image.SCALE_SMOOTH);
									panel.removeAll();
									panel.add(new JLabel(new ImageIcon(scaledImage)));
									panel.revalidate();
								} catch (IOException ex) {
									ex.printStackTrace();
								}
							}
						}
					}
				});
			}
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(Buscamines::new);
	}
}
