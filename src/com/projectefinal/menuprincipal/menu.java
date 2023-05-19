package com.projectefinal.menuprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.projectefinal.login.encriptacion;

public class menu extends JFrame {
	private static String nombreUserLog = "";
	private static File fitxerGuardar = null;

	public static void main(String[] args) {
		menu m = new menu();
		m.inici();
	}

	public void inici() {
		crearTablas();
		Container pagina = this.getContentPane();
		JPanel conjunto = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

		JLabel etiqueta_vacia1 = new JLabel();
		JLabel etiqueta_vacia2 = new JLabel();
		JLabel etiqueta_vacia3 = new JLabel();
		JLabel etiqueta_vacia4 = new JLabel();

		etiqueta_vacia1.setPreferredSize(new Dimension(200, 200));
		etiqueta_vacia2.setPreferredSize(new Dimension(200, 200));
		etiqueta_vacia3.setPreferredSize(new Dimension(200, 200));
		etiqueta_vacia4.setPreferredSize(new Dimension(200, 200));

		pagina.add(etiqueta_vacia1, BorderLayout.NORTH);
		pagina.add(conjunto, BorderLayout.CENTER);
		pagina.add(etiqueta_vacia3, BorderLayout.WEST);
		pagina.add(etiqueta_vacia2, BorderLayout.SOUTH);
		pagina.add(etiqueta_vacia4, BorderLayout.EAST);

		JFrame frame = new JFrame("Exercici 01: Formulari Dades");
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
		
		
		
		
		
		
		JPanel pSaludo = new JPanel();
		JLabel saludo = new JLabel();
		saludo.setText("");
		Font fuente = new Font("Arial", Font.BOLD, 30);
		saludo.setFont(fuente);
		pSaludo.add(saludo);
		//pagina.add(pSaludo, BorderLayout.NORTH);
		
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

		//pagina.add(imagenes, BorderLayout.CENTER);
		
		JPanel botones2 = new JPanel();
		botones2.setLayout(new GridLayout(0,3));
		JButton boton1 = new JButton("LogOut");
		JButton boton2 = new JButton("Ver perfil");
		JButton boton3 = new JButton("Borrar cuenta");
		
		botones2.add(boton1);
		botones2.add(boton2);
		botones2.add(boton3);
		
        Border border = BorderFactory.createLineBorder(Color.black, 1);
		
		JPanel pVerImagen = new JPanel();
		pVerImagen.setLayout(new FlowLayout(FlowLayout.CENTER));
		JPanel verPerfil = new JPanel();
		verPerfil.setLayout(new GridLayout(6,0));
		Font fuenteVer = new Font("Roboto",Font.BOLD, 20);
		JLabel verNom = new JLabel("",SwingConstants.CENTER);
		verNom.setBackground(Color.cyan);
		verNom.setOpaque(true);
		verNom.setBorder(border);
		verNom.setFont(fuenteVer);
		JLabel verApellidos = new JLabel("", SwingConstants.CENTER);
		verApellidos.setBackground(Color.cyan);
		verApellidos.setOpaque(true);
		verApellidos.setBorder(border);
		verApellidos.setFont(fuenteVer);
		JLabel verPoblacion = new JLabel("", SwingConstants.CENTER);
		verPoblacion.setBackground(Color.cyan);
		verPoblacion.setOpaque(true);
		verPoblacion.setBorder(border);
		verPoblacion.setFont(fuenteVer);
		JLabel verImagen = new JLabel("", SwingConstants.CENTER);
		
		JLabel verCorreo = new JLabel("", SwingConstants.CENTER);
		verCorreo.setBackground(Color.cyan);
		verCorreo.setOpaque(true);
		verCorreo.setBorder(border);
		verCorreo.setFont(fuenteVer);
		JPanel paraBoton = new JPanel();
		paraBoton.setLayout(new FlowLayout(FlowLayout.CENTER));
		JButton atras = new JButton("Volver atras");
		paraBoton.add(atras);
		
		pVerImagen.add(verImagen);
		verPerfil.add(pVerImagen);
		verPerfil.add(verNom);
		verPerfil.add(verApellidos);
		verPerfil.add(verPoblacion);
		verPerfil.add(verCorreo);
		//verPerfil.add(verImagen);
		verPerfil.add(paraBoton);
		
		
		atras.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				verNom.setText("");
            	verApellidos.setText("");
            	verCorreo.setText("");
            	verPoblacion.setText("");
            	verImagen.setIcon(null);
				saludo.setText("Hola: " + nombreUserLog);
				pagina.removeAll();
				pagina.add(pSaludo, BorderLayout.NORTH);
				pagina.add(imagenes, BorderLayout.CENTER);
				pagina.add(botones2, BorderLayout.SOUTH);
				pagina.revalidate();
				pagina.repaint();
				setSize(700, 550);
			}
			
		});
		
		boton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
		            // Cargar el driver de MySQL
		           //Class.forName("org.mysql.jdbc.Driver");

		            // Establecer la conexión con la base de datos
		            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
		            int idElim = 0;
		            String sql = "SELECT * FROM usuario WHERE correo = ?";
		            PreparedStatement statement = c.prepareStatement(sql);
		            statement.setString(1, nombreUserLog);
		            ResultSet resultSet = statement.executeQuery();
		            
		            if(resultSet.next()) {
		            	byte[] imageBytes = resultSet.getBytes("imagen");
		            	ImageIcon imageIcon = new ImageIcon(imageBytes);
		            	verNom.setText("NOMBRE: "+resultSet.getString("nombre"));
		            	verApellidos.setText("APELLIDO: "+resultSet.getString("apellidos"));
		            	verCorreo.setText("CORREO: "+resultSet.getString("correo"));
		            	verPoblacion.setText("POBLACION: "+resultSet.getString("poblacion"));
		            	redimensionarImagenBDD(imageBytes, verImagen);
		                // Paso 3: Crear un objeto JLabel y establecer el ImageIcon como su icono
		                pagina.removeAll();
		                pagina.add(verPerfil);
		                pagina.revalidate();
		                pagina.repaint();
		                setSize(700,900);
		            }
		            
		            
		           
		            
		        } catch (Exception e1) {
		            System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
		        } 
			}
			
		});
		
		//pagina.add(botones2, BorderLayout.SOUTH);
		
		boton1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pagina.removeAll();
				pagina.add(etiqueta_vacia1, BorderLayout.NORTH);
				pagina.add(conjunto, BorderLayout.CENTER);
				pagina.add(etiqueta_vacia3, BorderLayout.WEST);
				pagina.add(etiqueta_vacia2, BorderLayout.SOUTH);
				pagina.add(etiqueta_vacia4, BorderLayout.EAST);
				fieldUser.setText("");
				fieldPass.setText("");
				pagina.revalidate();
				pagina.repaint();
			}
			
		});
		
		boton3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
		            // Cargar el driver de MySQL
		           //Class.forName("org.mysql.jdbc.Driver");

		            // Establecer la conexión con la base de datos
		            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
		            int idElim = 0;
		            Statement s = c.createStatement();
		            
		            ResultSet r = s.executeQuery("SELECT * FROM usuario");
		            while(r.next()) {
		            	if(r.getString("correo").equals(nombreUserLog)) {
		            		idElim = r.getInt("id");
		            		break;
		            	}
		            }
		            s.executeUpdate("DELETE FROM password WHERE id_usuario = " + idElim);
		            s.executeUpdate("DELETE FROM usuario WHERE id = " + idElim);

		            
		            
		            
		            pagina.removeAll();
					pagina.add(etiqueta_vacia1, BorderLayout.NORTH);
					pagina.add(conjunto, BorderLayout.CENTER);
					pagina.add(etiqueta_vacia3, BorderLayout.WEST);
					pagina.add(etiqueta_vacia2, BorderLayout.SOUTH);
					pagina.add(etiqueta_vacia4, BorderLayout.EAST);
					fieldUser.setText("");
					fieldPass.setText("");
					pagina.revalidate();
					pagina.repaint();
		            
		            
		        } catch (Exception e1) {
		            System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
		        } 
			}
			
		});

		elegir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				File directoriInicial = new File(".");
				// instanciem el selector de fitxers amb directori actual
				JFileChooser selector = new JFileChooser(directoriInicial);
				// també ho podem fer ficant la ruta directament al selector
				selector = new JFileChooser(".");
				// només es poden triar fitxers, no directoris
				selector.setFileSelectionMode(JFileChooser.FILES_ONLY);
				// definició dels filtres
				FileNameExtensionFilter filtreTxt = new FileNameExtensionFilter("Imagenes", "jpg", "png", "jpg");
				selector.setFileFilter(filtreTxt);
				// mostrem el diàleg de guardar fitxer
				int seleccioUsuari = selector.showSaveDialog(frame);

				// comprovem si l'acció s'ha realitzat correctament
				if (seleccioUsuari == JFileChooser.APPROVE_OPTION) {
					// s'ha fet clic en obrir, obtenim el fitxer
					fitxerGuardar = selector.getSelectedFile();
					// comprovem si realment existeix
					etiquetaImagen.setText(fitxerGuardar.getName());
				} else if (seleccioUsuari == JFileChooser.CANCEL_OPTION) {
					// S'ha fet clic en cancel·lar o premut la tecla Esc
					System.out.println("Cancel·lat");
				} else {
					// S'ha produït algun tipus d'error en obrir el fitxer
					// JFileChooser.ERROR_OPTION
					System.out.println("Error");
				}

				frame.dispose();
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
							saludo.setText("Hola: " + nombreUser);
							pagina.removeAll();
							pagina.add(pSaludo, BorderLayout.NORTH);
							pagina.add(imagenes, BorderLayout.CENTER);
							pagina.add(botones2, BorderLayout.SOUTH);
							pagina.revalidate();
							pagina.repaint();


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

		registro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pagina.removeAll();
				pagina.add(todo);
				mensaje.setText("");
				vacio5.setText("");
				pagina.revalidate();
				pagina.repaint();
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
					contador ++;
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
			           //Class.forName("org.mysql.jdbc.Driver");

			            // Establecer la conexión con la base de datos
			            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
			           
			            Statement s = c.createStatement();
			           
			            ResultSet r = s.executeQuery("SELECT * FROM usuario");
			            while(r.next()) {
			            	if(r.getString("correo").equals(correo)) {
			            		compCorreo = true;
			            	}
			            }
			        } catch (Exception e2) {
			            System.out.println("Error al conectar con la base de datos: " + e2.getMessage());
			        }
					if(compCorreo) {
						vacio3.setText("Este correo ya esta registrado");
						vacio3.setHorizontalAlignment(SwingConstants.CENTER);
						vacio3.setForeground(Color.red);
					}else {
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
				
				if(contra && contra2) {
					String contrasena1 = campoContra.getText().trim();
					String contrasena2 = campoContra2.getText().trim();
					if(contrasena1.equals(contrasena2)) {
						vacio6.setText("");
						vacio7.setText("");
						contador ++;
					}else {
						vacio7.setText("Contraseñas no coinciden");
						vacio7.setHorizontalAlignment(SwingConstants.CENTER);
						vacio7.setForeground(Color.red);
					}
				}
				if(fitxerGuardar == null){
					vacio5.setText("Tienes que elegir una imagen");
					vacio5.setHorizontalAlignment(SwingConstants.CENTER);
					vacio5.setForeground(Color.red);
				}else {
					vacio5.setText("");
					contador ++;
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
			           //Class.forName("org.mysql.jdbc.Driver");

			            // Establecer la conexión con la base de datos
			            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
			           
			            
			           
			            String sentencia = "INSERT INTO usuario (nombre,apellidos,correo,poblacion,imagen) VALUES (?,?,?,?,?)";
			            PreparedStatement s = c.prepareStatement(sentencia);
			            s.setString(1, nombre);
			            s.setString(2, Apellido);
			            s.setString(3, correo);
			            s.setString(4, Poblacio);
			            s.setBytes(5, imagenByte);
			            
			            s.executeUpdate();
			            
			            String salt = encriptacion.generarSalt();
			            String hash = encriptacion.generarHash(contrasenaCorrecta, salt);
			            int id = 0;
			            ResultSet r2 = s.executeQuery("SELECT * FROM usuario");
			            while(r2.next()) {
			            	id = r2.getInt("id");
			            }
			            encriptacion.ponerBDD(id,hash,salt);
			            
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
					
					pagina.removeAll();
					pagina.add(etiqueta_vacia1, BorderLayout.NORTH);
					pagina.add(conjunto, BorderLayout.CENTER);
					pagina.add(etiqueta_vacia3, BorderLayout.WEST);
					pagina.add(etiqueta_vacia2, BorderLayout.SOUTH);
					pagina.add(etiqueta_vacia4, BorderLayout.EAST);
					pagina.revalidate();
					pagina.repaint();
					fitxerGuardar = null;
					contador = 0;
					JOptionPane.showMessageDialog(null, "Registro completado");
					

				}
				contador = 0;
			}

		});

		arrere.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pagina.removeAll();
				pagina.add(etiqueta_vacia1, BorderLayout.NORTH);
				pagina.add(conjunto, BorderLayout.CENTER);
				pagina.add(etiqueta_vacia3, BorderLayout.WEST);
				pagina.add(etiqueta_vacia2, BorderLayout.SOUTH);
				pagina.add(etiqueta_vacia4, BorderLayout.EAST);
				pagina.revalidate();
				pagina.repaint();
			}

		});

		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fitxerGuardar = null;
				fieldNombre.setText(null);
				fieldApellido.setText(null);
				fieldCorreo.setText(null);
				// fieldNaiximent.setText(null);
				fieldPoblacio.setText(null);
				// fieldCicle.setText(null);
				etiquetaImagen.setText("");
				vacio1.setText("");
				vacio2.setText("");
				vacio3.setText("");
				vacio4.setText("");
				vacio5.setText("");
				// vacio6.setText("");
				mensaje.setText("");
			}

		});

		this.setResizable(false);
		this.setSize(700, 550);
		this.setVisible(true);
	}

	public static String nombreUserLog() {
		return nombreUserLog;
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
	
	public void crearTablas() {
		try {
            // Cargar el driver de MySQL
           //Class.forName("org.mysql.jdbc.Driver");

            // Establecer la conexión con la base de datos
            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
            
            Statement s = c.createStatement();
            s.execute("CREATE TABLE IF NOT EXISTS usuario (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(20) NOT NULL, apellidos VARCHAR(50) NOT NULL, correo VARCHAR(100) NOT NULL, poblacion VARCHAR(25) NOT NULL, imagen LONGBLOB NOT NULL)");

            s.execute("CREATE TABLE IF NOT EXISTS password (id INT PRIMARY KEY AUTO_INCREMENT, id_usuario INT NOT NULL REFERENCES usuario(id), hash LONGTEXT NOT NULL, salt LONGTEXT NOT NULL)");
            
            
            
        } catch (Exception e1) {
            System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
        } 
	}


}
