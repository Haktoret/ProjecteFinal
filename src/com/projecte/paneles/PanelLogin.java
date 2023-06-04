package com.projecte.paneles;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.projectefinal.encriptaciones.encriptacion;
import com.projectefinal.menuprincipal.menu;

public class PanelLogin extends JPanel{
	private JLabel etiqueta_vacia1 = new JLabel();
	private JLabel etiqueta_vacia2 = new JLabel();
	private JLabel etiqueta_vacia3 = new JLabel();
	private JLabel etiqueta_vacia4 = new JLabel();
	private static String nombreUserLog = "";

	
	public PanelLogin() {
		
		this.setLayout(new BorderLayout());
		JPanel conjunto = new JPanel();
		
		conjunto.setLayout(new BorderLayout());

		JPanel imagen = new JPanel();
		imagen.setLayout(new BorderLayout());
		JLabel imagenl = new JLabel();
		ImageIcon icono = new ImageIcon("login.png");
		imagenl.setIcon(icono);
		imagen.add(imagenl, BorderLayout.CENTER);

		JPanel textos = new JPanel();
		textos.setLayout(new GridLayout(2, 0));
		JLabel textoUser = new JLabel("Usuario:   ", SwingConstants.RIGHT);
		JLabel textoPass = new JLabel("Password:   ", SwingConstants.RIGHT);
		textos.add(textoUser, BorderLayout.NORTH);
		textos.add(textoPass, BorderLayout.CENTER);

		JPanel fields = new JPanel();
		fields.setLayout(new GridLayout(2, 0));
		JTextField fieldUser = new JTextField(10);
		JPasswordField fieldPass = new JPasswordField(10);
		fields.add(fieldUser, BorderLayout.NORTH);
		fields.add(fieldPass, BorderLayout.CENTER);

		JPanel botones = new JPanel();
		botones.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton login = new JButton("Login");
		JButton registro = new JButton("Registro");
		botones.add(login);
		botones.add(registro);

		conjunto.add(imagen, BorderLayout.WEST);
		conjunto.add(textos, BorderLayout.CENTER);
		conjunto.add(fields, BorderLayout.EAST);
		conjunto.add(botones, BorderLayout.SOUTH);

		

		etiqueta_vacia1.setPreferredSize(new Dimension(200, 200));
		etiqueta_vacia2.setPreferredSize(new Dimension(200, 200));
		etiqueta_vacia3.setPreferredSize(new Dimension(200, 200));
		etiqueta_vacia4.setPreferredSize(new Dimension(200, 200));
		
		
		this.add(etiqueta_vacia1, BorderLayout.NORTH);
		this.add(conjunto, BorderLayout.CENTER);
		this.add(etiqueta_vacia3, BorderLayout.WEST);
		this.add(etiqueta_vacia2, BorderLayout.SOUTH);
		this.add(etiqueta_vacia4, BorderLayout.EAST);
		
		registro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelLogin.this);
				mRegistro.getContentPane().removeAll();
				PanelRegistre r = new PanelRegistre();
				mRegistro.getContentPane().add(r);
				mRegistro.getContentPane().revalidate();
				mRegistro.getContentPane().repaint();
			}

		});
		
		login.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			
			if (textoUser.getText().trim().length() == 0 || textoPass.getText().trim().length() == 0) {
				JOptionPane.showMessageDialog(null, // contenidor d'alt nivell
						"Hay algun campo vacio", // text
						"Error", // títol del diàleg
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				try {
					String nombreUser = fieldUser.getText().trim();
					nombreUserLog = nombreUser;
					boolean verificacion = encriptacion.verificarContra(fieldUser.getText().trim(), fieldPass.getText());
					if (verificacion) {
						
						menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelLogin.this);
						mRegistro.getContentPane().removeAll();
						PanelInicio inici = new PanelInicio(nombreUser);
						mRegistro.add(inici);
						mRegistro.revalidate();
						mRegistro.repaint();


					} else {
						JOptionPane.showMessageDialog(null, // contenidor d'alt nivell
								"Error de credenciales", // text
								"Error", // títol del diàleg
								JOptionPane.INFORMATION_MESSAGE);
						fieldUser.setText("");
						fieldPass.setText("");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	});
		
		

	}
	
	public static String nomUserLog() {
		return nombreUserLog;
	}
}
