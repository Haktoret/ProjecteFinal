package com.projecte.paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.projecte.usuaris.Usuari;
import com.projectefinal.encriptaciones.encriptacion;
import com.projectefinal.menuprincipal.menu;

public class PanelRegistre extends JPanel{
	private static File fitxerGuardar = null;
	private PanelLogin mostrarLogin = new PanelLogin();
	
	public PanelRegistre() {
		this.setLayout(new BorderLayout());
		JPanel todo = new JPanel();
		todo.setLayout(new GridLayout(9, 3));

		JLabel vacio = new JLabel("");
		JLabel titulo = new JLabel("Formulari dades", SwingConstants.CENTER);
		JLabel mensaje = new JLabel("", SwingConstants.RIGHT);

		JPanel panel0 = new JPanel();
		panel0.setLayout(new GridLayout(0, 3));
		panel0.add(vacio);
		panel0.add(titulo);
		panel0.add(mensaje);
		todo.add(panel0);

		JLabel etiquetaNombre = new JLabel("Nom: ", SwingConstants.CENTER);
		JTextField fieldNombre = new JTextField(20);
		JLabel vacio1 = new JLabel("");

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(0, 3));
		panel1.add(etiquetaNombre);
		panel1.add(fieldNombre);
		panel1.add(vacio1);
		todo.add(panel1);

		JLabel etiquetaApellido = new JLabel("Cognom: ", SwingConstants.CENTER);
		JTextField fieldApellido = new JTextField(20);
		JLabel vacio2 = new JLabel("");

		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(0, 3));
		panel2.add(etiquetaApellido);
		panel2.add(fieldApellido);
		panel2.add(vacio2);
		todo.add(panel2);

		JLabel etiquetaCorreo = new JLabel("Correu: ", SwingConstants.CENTER);
		JTextField fieldCorreo = new JTextField(20);
		JLabel vacio3 = new JLabel("");

		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(0, 3));
		panel3.add(etiquetaCorreo);
		panel3.add(fieldCorreo);
		panel3.add(vacio3);
		todo.add(panel3);

		JLabel etiquetaPoblacio = new JLabel("Poblacio: ", SwingConstants.CENTER);
		JTextField fieldPoblacio = new JTextField(20);
		JLabel vacio4 = new JLabel("");

		JPanel panel4 = new JPanel();
		panel4.setLayout(new GridLayout(0, 3));
		panel4.add(etiquetaPoblacio);
		panel4.add(fieldPoblacio);
		panel4.add(vacio4);
		todo.add(panel4);

		JButton elegir = new JButton("Tria imagen: ");
		JLabel etiquetaImagen = new JLabel("", SwingConstants.CENTER);
		JLabel vacio5 = new JLabel("");

		JPanel panel5 = new JPanel();
		panel5.setLayout(new GridLayout(0, 3));
		panel5.add(elegir);
		panel5.add(etiquetaImagen);
		panel5.add(vacio5);
		todo.add(panel5);

		// JLabel etiquetaImagen = new JLabel("", SwingConstants.CENTER);

		JLabel contrasena = new JLabel("Contraseña", SwingConstants.CENTER);
		JPasswordField campoContra = new JPasswordField();
		JLabel vacio6 = new JLabel("");

		JPanel panel6 = new JPanel();
		panel6.setLayout(new GridLayout(0, 3));
		panel6.add(contrasena);
		panel6.add(campoContra);
		panel6.add(vacio6);
		todo.add(panel6);

		JLabel contrasena2 = new JLabel("Repite contraseña", SwingConstants.CENTER);
		JPasswordField campoContra2 = new JPasswordField();
		JLabel vacio7 = new JLabel("");

		JPanel panel8 = new JPanel();
		panel8.setLayout(new GridLayout(0, 3));
		panel8.add(contrasena2);
		panel8.add(campoContra2);
		panel8.add(vacio7);
		todo.add(panel8);

		JButton arrere = new JButton("Arrere");
		JButton reset = new JButton("Reset");
		JButton guardar = new JButton("Enviar");

		JPanel panel7 = new JPanel();
		panel7.setLayout(new GridLayout(0, 3));
		panel7.add(arrere);
		panel7.add(reset);
		panel7.add(guardar);
		todo.add(panel7);
		this.add(todo);

		elegir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				File directoriInicial = new File(".");
				JFileChooser selector = new JFileChooser(directoriInicial);
				selector = new JFileChooser(".");
				selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
				FileNameExtensionFilter filtreTxt = new FileNameExtensionFilter("Imagenes", "jpg", "png", "jpg");
				selector.setFileFilter(filtreTxt);
				int seleccioUsuari = selector.showSaveDialog(frame);

				if (seleccioUsuari == JFileChooser.APPROVE_OPTION) {
					fitxerGuardar = selector.getSelectedFile();
					etiquetaImagen.setText(fitxerGuardar.getName());
				} else if (seleccioUsuari == JFileChooser.CANCEL_OPTION) {
					System.out.println("Cancel·lat");
				} else {
					System.out.println("Error");
				}

				frame.dispose();
			}

		});
		guardar.addActionListener(new ActionListener() {

			int contador = 0;

			@Override
			public void actionPerformed(ActionEvent e) {

				if (fieldNombre.getText().trim().isEmpty()) {
					vacio1.setText("No pot estar buit");
					vacio1.setHorizontalAlignment(SwingConstants.CENTER);
					vacio1.setForeground(Color.red);
				} else if (fieldNombre.getText().trim().length() < 3) {
					vacio1.setHorizontalAlignment(SwingConstants.CENTER);
					vacio1.setForeground(Color.red);
					vacio1.setText("Al menos pon 3 palabras");
				} else {
					vacio1.setText("");
					contador++;
				}
				if (fieldApellido.getText().trim().isEmpty()) {
					vacio2.setText("No pot estar buit");
					vacio2.setHorizontalAlignment(SwingConstants.CENTER);
					vacio2.setForeground(Color.red);
				} else if (fieldApellido.getText().trim().length() < 5) {
					vacio2.setText("Formato de apellido incorrecto");
					vacio2.setHorizontalAlignment(SwingConstants.CENTER);
					vacio2.setForeground(Color.red);
				} else {
					contador++;
					vacio2.setText("");
				}
				boolean compCorreo = false;
				String correo = fieldCorreo.getText().trim();
				if (fieldCorreo.getText().trim().isEmpty()) {
					vacio3.setText("No pot estar buit");
					vacio3.setHorizontalAlignment(SwingConstants.CENTER);
					vacio3.setForeground(Color.red);
				} else if (!Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$").matcher(correo).matches()) {
					vacio3.setText("Formato de correo incorrecto");
					vacio3.setHorizontalAlignment(SwingConstants.CENTER);
					vacio3.setForeground(Color.red);

				} else {
					try {
						// Cargar el driver de MySQL
						// Class.forName("org.mysql.jdbc.Driver");

						// Establecer la conexión con la base de datos
						Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro",
								"1daw01_pro", "oETQoCsk0r");

						Statement s = c.createStatement();

						ResultSet r = s.executeQuery("SELECT * FROM usuario");
						while (r.next()) {
							if (r.getString("correo").equals(correo)) {
								compCorreo = true;
							}
						}
					} catch (Exception e2) {
						System.out.println("Error al conectar con la base de datos: " + e2.getMessage());
					}
					if (compCorreo) {
						vacio3.setText("Este correo ya esta registrado");
						vacio3.setHorizontalAlignment(SwingConstants.CENTER);
						vacio3.setForeground(Color.red);
					} else {
						contador++;
						vacio3.setText("");
					}

				}

				if (fieldPoblacio.getText().trim().isEmpty()) {
					vacio4.setText("No pot estar buit");
					vacio4.setHorizontalAlignment(SwingConstants.CENTER);
					vacio4.setForeground(Color.red);
				} else if (fieldPoblacio.getText().trim().length() < 3) {
					vacio4.setText("Al menos 3 letras");
					vacio4.setHorizontalAlignment(SwingConstants.CENTER);
					vacio4.setForeground(Color.red);
				} else {
					contador++;
					vacio4.setText("");
				}
				boolean contra = false;
				boolean contra2 = false;

				if (campoContra.getText().trim().isEmpty()) {
					vacio6.setText("No pot estar buit");
					vacio6.setHorizontalAlignment(SwingConstants.CENTER);
					vacio6.setForeground(Color.red);
				} else {
					vacio6.setText("");
					contra = true;

				}

				if (campoContra2.getText().trim().isEmpty()) {
					vacio7.setText("No pot estar buit");
					vacio7.setHorizontalAlignment(SwingConstants.CENTER);
					vacio7.setForeground(Color.red);
				} else {

					vacio7.setText("");
					contra2 = true;

				}

				if (contra && contra2) {
					String contrasena1 = campoContra.getText().trim();
					String contrasena2 = campoContra2.getText().trim();
					if (contrasena1.equals(contrasena2)) {
						vacio6.setText("");
						vacio7.setText("");
						contador++;
					} else {
						vacio7.setText("Contraseñas no coinciden");
						vacio7.setHorizontalAlignment(SwingConstants.CENTER);
						vacio7.setForeground(Color.red);
					}
				}
				if (fitxerGuardar == null) {
					vacio5.setText("Tienes que elegir una imagen");
					vacio5.setHorizontalAlignment(SwingConstants.CENTER);
					vacio5.setForeground(Color.red);
				} else {
					vacio5.setText("");
					contador++;
				}

				if (contador != 6) {
					mensaje.setText("Error en les dades");
					mensaje.setHorizontalAlignment(SwingConstants.CENTER);
					mensaje.setForeground(Color.red);
				}

				if (contador == 6) {
					String nombre = fieldNombre.getText();
					String Apellido = fieldApellido.getText();
					String Correo = fieldCorreo.getText();
					String contrasenaCorrecta = campoContra.getText();
					String Poblacio = fieldPoblacio.getText();
					byte[] imagenByte = imagenaBytes(fitxerGuardar);

					// String Cicle = fieldCicle.getText();
					try {
						// Cargar el driver de MySQL
						// Class.forName("org.mysql.jdbc.Driver");

						// Establecer la conexión con la base de datos
						Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro",
								"1daw01_pro", "oETQoCsk0r");

						String sentencia = "INSERT INTO usuario (nombre,apellidos,correo,poblacion,imagen) VALUES (?,?,?,?,?)";
						PreparedStatement s = c.prepareStatement(sentencia);
						Usuari user = new Usuari(nombre, Apellido, correo, Poblacio, imagenByte);
						s.setString(1, user.getNombre());
						s.setString(2, user.getApellidos());
						s.setString(3, user.getCorreo());
						s.setString(4, user.getPoblacio());
						s.setBytes(5, user.getImagenByte());

						s.executeUpdate();

						String salt = encriptacion.generarSalt();
						String hash = encriptacion.generarHash(contrasenaCorrecta, salt);
						int id = 0;
						ResultSet r2 = s.executeQuery("SELECT * FROM usuario");
						while (r2.next()) {
							id = r2.getInt("id");
						}
						encriptacion.ponerBDD(id, hash, salt);

					} catch (Exception e2) {
						System.out.println("Error al conectar con la base de datos: " + e2.getMessage());
					}

					fieldNombre.setText(null);
					fieldApellido.setText(null);
					fieldCorreo.setText(null);
					campoContra.setText(null);
					campoContra2.setText(null);
					fieldPoblacio.setText(null);
					// fieldCicle.setText(null);
					vacio5.setText(null);

					mensaje.setText("Guardat Correctament");
					mensaje.setHorizontalAlignment(SwingConstants.CENTER);
					mensaje.setForeground(Color.green);
					menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelRegistre.this);
					mRegistro.getContentPane().removeAll();

					fitxerGuardar = null;
					contador = 0;
					JOptionPane.showMessageDialog(null, "Registro completado");
					PanelLogin mostrarLogin = new PanelLogin();
					mRegistro.getContentPane().add(mostrarLogin, BorderLayout.CENTER);
					mRegistro.getContentPane().revalidate();
					mRegistro.getContentPane().repaint();

				}
				contador = 0;
			}

		});
		arrere.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelRegistre.this);
				mRegistro.getContentPane().removeAll();
				PanelLogin mostrarLogin = new PanelLogin();
				mRegistro.getContentPane().add(mostrarLogin, BorderLayout.CENTER);
				mRegistro.getContentPane().revalidate();
				mRegistro.getContentPane().repaint();
			}

		});
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fitxerGuardar = null;
				fieldNombre.setText(null);
				fieldApellido.setText(null);
				fieldCorreo.setText(null);
				fieldPoblacio.setText(null);
				etiquetaImagen.setText("");
				vacio1.setText("");
				vacio2.setText("");
				vacio3.setText("");
				vacio4.setText("");
				vacio5.setText("");
				vacio6.setText("");
				vacio7.setText("");
				campoContra.setText("");
				campoContra2.setText("");
				mensaje.setText("");
			}

		});

	}

	public byte[] imagenaBytes(File imageFile) {
		byte[] imageBytes = null;
		try {
			FileInputStream fis = new FileInputStream(imageFile);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];

			int bytesRead;
			while ((bytesRead = fis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			imageBytes = bos.toByteArray();
			fis.close();
			bos.close();
			return imageBytes;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageBytes;

	}
}
