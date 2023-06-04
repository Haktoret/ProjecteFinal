package com.joc.pixelART;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.projecte.paneles.PanelInicio;

public class PixelArt extends JFrame {
	private JPanel[][] cuadricula;
	private Color colorSeleccionado;
	private boolean pintando;
	private boolean borrando;
	private boolean carga = false;
	private static boolean vacio = false;
	private int cuadroMida;
	private int ventanaMida;
	private boolean cerrado = true;
	private int[][] colores = null;
	private JComboBox<String> partidasUsers;
	
	
	
	

	public static boolean isVacio() {
		return vacio;
	}

	public static void setVacio(boolean vacio) {
		PixelArt.vacio = vacio;
	}

	public PixelArt(String usuario) {
		
		pedirTamanio(usuario);
		if (cerrado) {
			inicializarCuadricula();
		}

		colorSeleccionado = Color.BLACK;
		pintando = false;
		borrando = false;

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				PanelInicio.setContadorPixel(0);
				cerrado = false;
				if(vacio == false) {
					cerrarPixelArt(usuario);

				}
				PanelInicio.ponerPvacio();

			}

		});
		
		if(vacio == false) {
			if (cerrado) {
				setTitle("Pixel Art");
				setLocationRelativeTo(null);
				setResizable(true);
				JPanel panelCuadricula = new JPanel(new GridLayout(cuadroMida, cuadroMida));
				panelCuadricula.setPreferredSize(new Dimension(cuadroMida * 20, cuadroMida * 20));
				panelCuadricula.setMaximumSize(panelCuadricula.getPreferredSize());

				for (int i = 0; i < cuadroMida; i++) {
					for (int j = 0; j < cuadroMida; j++) {
						cuadricula[i][j] = new JPanel();
						if (carga) {
							Color fondo = new Color(colores[i][j]);
							cuadricula[i][j].setBackground(fondo);
						} else {
							cuadricula[i][j].setBackground(Color.WHITE);
						}
						cuadricula[i][j].setBorder(BorderFactory.createLineBorder(Color.GRAY));
						cuadricula[i][j].addMouseListener(new MouseAdapter() {
							@Override
							public void mousePressed(MouseEvent e) {
								if (SwingUtilities.isLeftMouseButton(e)) {
									pintando = true;
									cambiarColor((JPanel) e.getSource());
								} else if (SwingUtilities.isRightMouseButton(e)) {
									borrando = true;
									borrarColor((JPanel) e.getSource());
								}
							}

							@Override
							public void mouseReleased(MouseEvent e) {
								pintando = false;
								borrando = false;
							}

							@Override
							public void mouseEntered(MouseEvent e) {
								if (pintando && ((JPanel) e.getSource()).getBackground() != colorSeleccionado) {
									cambiarColor((JPanel) e.getSource());
								} else if (borrando && ((JPanel) e.getSource()).getBackground() != Color.WHITE) {
									borrarColor((JPanel) e.getSource());
								}
							}
						});
						cuadricula[i][j].addMouseMotionListener(new MouseAdapter() {
							@Override
							public void mouseDragged(MouseEvent e) {
								if (pintando) {
									cambiarColor((JPanel) e.getSource());
								} else if (borrando) {
									borrarColor((JPanel) e.getSource());
								}
							}
						});
						panelCuadricula.add(cuadricula[i][j]);
					}
				}

				getContentPane().add(panelCuadricula, BorderLayout.CENTER);
				getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);
				pack();
				setVisible(true);
			}
		}else {
			PanelInicio.setContadorPixel(0);
			PanelInicio.ponerPvacio();
		}

		

		if (!cerrado) {
			PanelInicio.setContadorPixel(0);
			PanelInicio.ponerPvacio();
			dispose();
		}

	}

	private void cambiarColor(JPanel panel) {
		if (pintando) {
			panel.setBackground(colorSeleccionado);
		}
	}

	private void borrarColor(JPanel panel) {
		if (borrando) {
			panel.setBackground(Color.WHITE);
		}
	}

	private void pedirTamanio(String usuario) {
		PanelInicio.ponerPvacio();
		String opcionSeleccionada = "";
		String[] options = { "Pequeño", "Mediano", "Grande", "Cargar" };

		int eleccion = JOptionPane.showOptionDialog(PixelArt.this, "Selecciona el tamaño del lienzo", "Tamaño",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (eleccion == JOptionPane.CLOSED_OPTION) {
			//PanelInicio.ponerPvacio();
			vacio = true;
			PanelInicio.setContadorPixel(0);
			PanelInicio.contadorA0();
			dispose();
		} else if (eleccion == 0) {
			vacio = false;
			cuadroMida = 12;
			ventanaMida = 200;
		} else if (eleccion == 1) {
			vacio = false;
			cuadroMida = 24;
			ventanaMida = 300;
		} else if (eleccion == 2) {
			vacio = false;
			cuadroMida = 40;
			ventanaMida = 400;
		} else if (eleccion == 3) {
			carga = true;
			int idUser = 0;
			try {
				Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro",
						"oETQoCsk0r");
				ArrayList<String> opciones = new ArrayList<>();
				String sql3 = "SELECT id FROM usuario WHERE correo = ?";
				PreparedStatement statement3 = c.prepareStatement(sql3);
				statement3.setString(1, usuario);
				ResultSet r2 = statement3.executeQuery();
				while (r2.next()) {
					idUser = r2.getInt("id");
				}

				String sql4 = "SELECT nombre_partida FROM partidas WHERE id_usuario = ? and juego = ?";
				PreparedStatement statement4 = c.prepareStatement(sql4);
				statement4.setInt(1, idUser);
				statement4.setString(2, "Pixel Art");
				ResultSet r3 = statement4.executeQuery();
				int contador = 0;
				while (r3.next()) {
					opciones.add(r3.getString("nombre_partida"));
					contador ++;
				}
				
				if(contador > 0) {
					JPanel panel = new JPanel();
					JLabel label = new JLabel("Selecciona una opción:");
					JComboBox<String> comboBox = new JComboBox<>(opciones.toArray(new String[opciones.size()]));
					panel.add(label);
					panel.add(comboBox);

					int seleccion = JOptionPane.showOptionDialog(null, panel, "Mi Diálogo", JOptionPane.DEFAULT_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, null, opciones.get(0));

					if (seleccion == JOptionPane.CLOSED_OPTION) {
						System.out.println("Diálogo cerrado");
						dispose();
						cerrado = false;

					} else {
						opcionSeleccionada = (String) comboBox.getSelectedItem();
						System.out.println("Opción seleccionada: " + opcionSeleccionada);
					}

					String sql2 = "SELECT * FROM partidas WHERE nombre_partida = ? and id_usuario = ?";
					PreparedStatement statement2 = c.prepareStatement(sql2);
					statement2.setString(1, opcionSeleccionada);
					statement2.setInt(2, idUser);
					ResultSet r = statement2.executeQuery();

					while (r.next()) {
						cuadroMida = r.getInt("cuadroMida");
						ventanaMida = r.getInt("ventanaMida");
						try {
							byte[] bytesMatriz = r.getBytes("referencia_juego");
							ByteArrayInputStream bais = new ByteArrayInputStream(bytesMatriz);

							// Crear un ObjectInputStream para deserializar los bytes en una matriz
							ObjectInputStream ois = new ObjectInputStream(bais);
							colores = (int[][]) ois.readObject();

							System.out.println("Matriz deserializada correctamente.");
							for (int i = 0; i < 12; i++) {
								for (int j = 0; j < 12; j++) {
								}
							}
							JOptionPane.showMessageDialog(this, "Cuadrícula cargada exitosamente desde la base de datos.",
									"Cargar", JOptionPane.INFORMATION_MESSAGE);
						} catch (Exception e) {
							System.out.println("Error al deserializar la matriz: " + e.getMessage());
							e.printStackTrace();
						}

					}
				}else {
		            JOptionPane.showMessageDialog(null, "Lo siento, no hay partidas guardadas.");
		            vacio = true;
		            dispose();
		            
				}
			
				// Preparar la consulta SQL para obtener la imagen desde la base de datos
//                String sql = "SELECT referencia_juego FROM partidas WHERE id = ?";
//                PreparedStatement statement = c.prepareStatement(sql);
//                statement.setInt(1, 2);
//                ResultSet resultSet = statement.executeQuery();
//
//                if (resultSet.next()) {
//                	
//                 
//
//                }
			} catch (Exception e) {
				System.out.println("Error al establecer la conexión con la base de datos: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	private void pedirTamanios() {
		String[] options = { "Pequeño", "Mediano", "Grande" };

		int elecion = JOptionPane.showOptionDialog(PixelArt.this, "Selecciona el tamaño del lienzo", "Tamaño",
				JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

		if (elecion == 0) {
			cuadroMida = 12;
			ventanaMida = 200;
		} else if (elecion == 1) {
			cuadroMida = 24;
			ventanaMida = 300;
		} else if (elecion == 2) {
			cuadroMida = 40;
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
						if (SwingUtilities.isLeftMouseButton(e)) {
							pintando = true;
							cambiarColor((JPanel) e.getSource());
						} else if (SwingUtilities.isRightMouseButton(e)) {
							borrando = true;
							borrarColor((JPanel) e.getSource());
						}
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						pintando = false;
						borrando = false;
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						if (pintando && ((JPanel) e.getSource()).getBackground() != colorSeleccionado) {
							cambiarColor((JPanel) e.getSource());
						} else if (borrando && ((JPanel) e.getSource()).getBackground() != Color.WHITE) {
							borrarColor((JPanel) e.getSource());
						}
					}
				});
			}
		}
	}

	private void limpiarCuadricula() {
		for (int i = 0; i < cuadroMida; i++) {
			for (int j = 0; j < cuadroMida; j++) {
				cuadricula[i][j].setBackground(Color.WHITE);
			}
		}
	}

	private JPanel createButtonPanel() {
		JButton colorBoto = new JButton("Color");
		colorBoto.addActionListener(e -> {
			Color colorSeleccionat = JColorChooser.showDialog(PixelArt.this, "Selecciona un color", colorSeleccionado);
			if (colorSeleccionat != null) {
				colorSeleccionado = colorSeleccionat;
				pintando = false;
				borrando = false;
			}
		});

		JButton guardarBoto = new JButton("GuardarPNG");
		guardarBoto.addActionListener(e -> {
			try {
				guardarCuadricula();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(PixelArt.this, "Error al guardar la partida.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});


//        JButton closeButton = new JButton("Cerrar");
//        closeButton.addActionListener(e -> {
//            int option = JOptionPane.showConfirmDialog(PixelArt.this, "¿Deseas guardar la partida antes de cerrar?", "Guardar partida", JOptionPane.YES_NO_CANCEL_OPTION);
//            if (option == JOptionPane.YES_OPTION) {
//            	try {
//                	
//					guardarCuadricula();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//                System.exit(0);
//            } else if (option == JOptionPane.NO_OPTION) {
//                System.exit(0);
//            }
//        });
//       ;

		JButton instruccionesBoto = new JButton("Instrucciones");
		instruccionesBoto.addActionListener(e -> {
			JOptionPane.showMessageDialog(PixelArt.this,
					"Haz clic izquierdo para pintar.\nMantén el clic derecho para borrar.\nUtiliza el botón 'Color' para seleccionar un nuevo color.\nUtiliza el botón 'Borrar' para borrar todo.",
					"Instrucciones", JOptionPane.INFORMATION_MESSAGE);
		});

		JButton limpiarBoto = new JButton("Limpiar");
		limpiarBoto.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(PixelArt.this, "¿Estás seguro de que quieres borrar todo?",
					"Limpiar", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				limpiarCuadricula();
			}
		});

		JButton tamañoBoto = new JButton("Tamaño");
		tamañoBoto.addActionListener(e -> {
			int option = JOptionPane.showConfirmDialog(PixelArt.this,
					"¿Deseas guardar la partida antes de cambiar el tamaño?", "Guardar",
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				try {
					guardarCuadricula();
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(PixelArt.this, "Error al guardar la partida.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if (option == JOptionPane.CANCEL_OPTION || option == JOptionPane.CLOSED_OPTION) {
				return;
			}

			//pedirTamanios();
			getContentPane().removeAll();
			inicializarCuadricula();
			JPanel panelCuadricula = new JPanel(new GridLayout(cuadroMida, cuadroMida));
			panelCuadricula.setPreferredSize(new Dimension(cuadroMida * 20, cuadroMida * 20));
			panelCuadricula.setMaximumSize(panelCuadricula.getPreferredSize());

			for (int i = 0; i < cuadroMida; i++) {
				for (int j = 0; j < cuadroMida; j++) {
					panelCuadricula.add(cuadricula[i][j]);
				}
			}

			getContentPane().add(panelCuadricula, BorderLayout.CENTER);
			getContentPane().add(createButtonPanel(), BorderLayout.SOUTH);

			pack();
		});

		JPanel panelBotones = new JPanel();
		panelBotones.add(colorBoto);
		panelBotones.add(guardarBoto);
		panelBotones.add(instruccionesBoto);
		panelBotones.add(limpiarBoto);
		panelBotones.add(tamañoBoto);
		// panelBotones.add(closeButton);

		return panelBotones;
	}

	private void guardarCuadricula() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showSaveDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			int ancho = cuadroMida;
			int alto = cuadroMida;

			BufferedImage image = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = image.createGraphics();

			for (int i = 0; i < ancho; i++) {
				for (int j = 0; j < alto; j++) {
					Color color = cuadricula[i][j].getBackground();
					graphics.setColor(color);
					graphics.fillRect(i, j, 1, 1);
				}
			}

			graphics.dispose();
			ImageIO.write(image, "png", file);
			JOptionPane.showMessageDialog(this, "Cuadrícula guardada exitosamente.", "Guardar",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

//    private byte[] intToBytes(int value) {
//        return new byte[] {
//            (byte) (value >> 24),
//            (byte) (value >> 16),
//            (byte) (value >> 8),
//            (byte) value
//        };
//    }

	private void cargarCuadricula() throws IOException {
		JFileChooser fileChooser = new JFileChooser();
		int option = fileChooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			BufferedImage image = ImageIO.read(file);

			int ancho = image.getWidth();
			int alto = image.getHeight();

			if (ancho != cuadroMida || alto != cuadroMida) {
				JOptionPane.showMessageDialog(this,
						"El tamaño de la imagen no coincide con el tamaño actual de la cuadrícula.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			for (int i = 0; i < ancho; i++) {
				for (int j = 0; j < alto; j++) {
					Color color = new Color(image.getRGB(i, j));
					cuadricula[i][j].setBackground(color);
				}
			}

			JOptionPane.showMessageDialog(this, "Cuadrícula cargada exitosamente.", "Cargar",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void cerrarPixelArt(String usuario) {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas guardar los cambios en el PixelArt?", "Guardar cambios",
				JOptionPane.YES_NO_OPTION);
		boolean mismaPartida = false;
		int repeticiones = 0;
		try {
			int id = 0;
			Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro",
					"1daw01_pro", "oETQoCsk0r");
			if (opcion == JOptionPane.YES_OPTION) {
				String nombrePartida = "";
				while (nombrePartida.isBlank()) {
					nombrePartida = JOptionPane.showInputDialog(null, "Ingrese el nombre de la partida:",
							"Nombre de partida", JOptionPane.PLAIN_MESSAGE);

				}
				do {
					String sql = "SELECT id FROM usuario WHERE correo = ?";

					PreparedStatement s = c.prepareStatement(sql);
					s.setString(1, usuario);
					
					ResultSet r = s.executeQuery();
					while (r.next()) {
						id = r.getInt("id");
					}
					
					String sql2 = "SELECT COUNT(*) as pepas FROM partidas WHERE juego = ? and nombre_partida = ? and id_usuario = ?";
					PreparedStatement pepe = c.prepareStatement(sql2);
					pepe.setString(1, "Pixel Art");
					pepe.setString(2, nombrePartida);
					pepe.setInt(3, id);
					
					ResultSet r5 = pepe.executeQuery();
					while (r5.next()) {
						repeticiones = r5.getInt("pepas");

					}
					if (repeticiones > 0) {
						
						nombrePartida = JOptionPane.showInputDialog(null, "Ingrese el nombre de la partida:",
								"Nombre de partida", JOptionPane.PLAIN_MESSAGE);
					}else {
						mismaPartida = true;
					}
				} while (mismaPartida == false);
				

				int[][] colores = new int[cuadroMida][cuadroMida];
				if (nombrePartida != null && !nombrePartida.isBlank()) {
					dispose();

					for (int i = 0; i < cuadroMida; i++) {
						for (int j = 0; j < cuadroMida; j++) {
							colores[i][j] = cuadricula[i][j].getBackground().getRGB();

						}
					}
					try {
						byte[] bytesMatriz = null;
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(baos);
						oos.writeObject(colores);
						bytesMatriz = baos.toByteArray();

						
						String sentencia = "INSERT INTO partidas (id_usuario, juego,referencia_juego, nombre_partida,cuadroMida,ventanaMida) VALUES (?,?,?,?,?,?)";
						PreparedStatement s2 = c.prepareStatement(sentencia);

						s2.setInt(1, id);
						s2.setString(2, "Pixel Art");
						s2.setBytes(3, bytesMatriz);
						s2.setString(4, nombrePartida);
						s2.setInt(5, cuadroMida);
						s2.setInt(6, ventanaMida);
						s2.executeUpdate();

					} catch (Exception e1) {
						System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
					}

				} else {
					JOptionPane.showMessageDialog(null, "Debes ingresar un nombre de partida válido.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				System.out.println("No se guardaron los cambios.");
			}
		} catch (Exception e6) {

		}
	}

//    private void cargarCuadriculaBDD() throws IOException {
//    	try {
//         Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
//
//        // Preparar la consulta SQL para obtener la imagen desde la base de datos
//        String sql = "SELECT referencia_juego FROM partidas WHERE id = ?";
//        PreparedStatement statement = c.prepareStatement(sql);
//        // Aquí debes especificar el criterio para seleccionar la imagen, como el ID de la fila correspondiente
//        statement.setInt(1, 3);
//        ResultSet resultSet = statement.executeQuery();
//
//        if (resultSet.next()) {
//            // Obtener los datos de la imagen como un arreglo de bytes
//            byte[] imageData = resultSet.getBytes("referencia_juego");
//
//            // Crear un objeto BufferedImage a partir de los datos de la imagen
//            ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
//            BufferedImage image = ImageIO.read(bais);
//
//            // Obtener el ancho y el alto de la imagen
//            int ancho = image.getWidth();
//            int alto = image.getHeight();
//
//            
//            // Redimensionar la cuadrícula según el tamaño de la imagen
//            
//            // Asignar los colores de la imagen a los paneles de la cuadrícula
//            for (int i = 0; i < ancho; i++) {
//                for (int j = 0; j < alto; j++) {
//                    int rgb = image.getRGB(i, j);
//                    Color color = new Color(rgb);
//                    cuadricula[i][j] = new JPanel(); // Crear el panel correspondiente
//                    cuadricula[i][j].setBackground(color);
//                    // Aquí puedes configurar cualquier otra propiedad del panel.
//                }
//            }
//
//            JOptionPane.showMessageDialog(this, "Cuadrícula cargada exitosamente desde la base de datos.", "Cargar", JOptionPane.INFORMATION_MESSAGE);
//        } else {
//            JOptionPane.showMessageDialog(this, "No se encontró la imagen en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    	}catch(Exception e) {
//    		
//    	}
//    }

}
