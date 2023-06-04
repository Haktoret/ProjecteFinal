package com.joc.buscamines;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.projecte.paneles.PanelInicio;
import com.projecte.paneles.PanelLogin;

public class ventana extends JFrame implements ActionListener, MouseListener {

    private boolean cerrado = true;
    private static boolean vacio = false;
    private static String dificultadRanking="principiante";
    

	public static boolean isVacio() {
		return vacio;
	}

	public static void setVacio(boolean vacio) {
		ventana.vacio = vacio;
	}

	private static class User {
		private String nombre;
		private int tiempo;

		public User(String nombre, int tiempo) {
			this.nombre = nombre;
			this.tiempo = tiempo;
		}

		public String getNombre() {
			return nombre;
		}

		public int getTiempo() {
			return tiempo;
		}

	}

	// *****************************************************
	static botones[][] boton;
	JButton carita = new JButton();
	JLabel tiempo = new JLabel();
	JLabel minas = new JLabel("10");
	JMenuBar barra = new JMenuBar();
	JMenuItem instrucciones = new JMenuItem("Como jugar");
	JMenuItem salir = new JMenuItem("Salir");
	JMenuItem nuevo = new JMenuItem("Nuevo");
	JMenuItem reiniciar = new JMenuItem("Reiniciar");
	JMenuItem ranking = new JMenuItem("Ranking");
	JMenuItem principiante = new JMenuItem("Principiante"); // 9 *9
	JMenuItem intermedio = new JMenuItem("Intermedio"); // 16 * 16
	JMenuItem experto = new JMenuItem("Experto"); // 30 * 30
	private JLabel casillasNoDescubiertasLabel = new JLabel("71");
	JMenu file = new JMenu("juego");
	JMenu ayuda = new JMenu("ayuda");

	cronometro c = new cronometro(tiempo);
	tablero cuadricula = new tablero(375, 375);
	String n = "principiante";
	int columna = 9, fila = 9, can_minas = 10;
	ArrayList<Integer> pos_minas = new ArrayList<>();
	Icon pre;
	boolean fin, ganador;

	int posiciones1[] = { 1, 1, 0, -1, -1, -1, 0, 1 };
	int posiciones2[] = { 0, 1, 1, 1, 0, -1, -1, -1 };

	int posiciones3[] = { 1, 0, -1, -1, 0, 0, 1, 1 };
	int posiciones4[] = { 0, 1, 0, 0, -1, -1, 0, 0 };
	int casillasNoDescubiertas;
	public boolean isFocusable() {
		return true;
	}

	void final_juego() {

		for (int i = 0; i < columna; i++) {
			for (int j = 0; j < fila; j++) {
				if (boton[i][j].usado) {
					continue;
				}
				if (boton[i][j].mina) {
					if (boton[i][j].bandera) {
						boton[i][j].cambiarimagen("/imagenes/mina.png");
					} else {
						boton[i][j].cambiarimagen("/imagenes/mina.png");
					}
					boton[i][j].setBorderPainted(false);
					boton[i][j].setContentAreaFilled(false);
					continue;
				}
				if (boton[i][j].minas_adyacentes > 0) {
					cambiar(i, j);
					continue;
				}

				boton[i][j].setVisible(false);
				boton[i][j].setEnabled(false);
			}
		}
		if (!fin) {
			fin = true;
		}
	}

	boolean ganador() {
		if (!minas.getText().equals("0")) {
			return false;
		}
		if (ganador) {
			return false;
		}
		for (int i = 0; i < columna; i++) {
			for (int j = 0; j < fila; j++) {
				if (boton[i][j].usado || !boton[i][j].isVisible() || boton[i][j].bandera) {
					continue;
				}
				return false;
			}
		}

		c.stop();

		return true;
	}

	void cara_reinicio(boolean x) {
		String imagen;
		if (x) {
			imagen = "/imagenes/carita_feliz.png";
		} else {
			imagen = "/imagenes/carita_triste.png";
		}
		ImageIcon icon1 = new ImageIcon(getClass().getResource(imagen));
		Icon icono1 = new ImageIcon(
				icon1.getImage().getScaledInstance(carita.getWidth(), carita.getHeight(), Image.SCALE_DEFAULT));
		carita.setText(null);
		carita.setIcon(icono1);
	}

	void crearminas() {
		pos_minas.clear();
		int mina;
		for (int i = 0; i < can_minas; i++) {
			mina = (int) (Math.random() * (columna * fila));
			if (pos_minas.contains(mina)) {
				i--;
			} else {
				pos_minas.add(mina);
				boton[mina / columna][mina % fila].mina = true;
			}
		}
		for (int i = 0; i < can_minas; i++) {
			int minaPos = pos_minas.get(i);
			int minaX = minaPos / columna;
			int minaY = minaPos % fila;
			System.out.println("Mina " + (i + 1) + ": x=" + minaX + ", y=" + minaY);
		}
	}

	void reiniciar() {
		casillasNoDescubiertas = columna * fila - can_minas;
		for (int i = 0; i < columna; i++) {
			for (int j = 0; j < fila; j++) {
				boton[i][j].setVisible(true);
				boton[i][j].setBorderPainted(true);
				boton[i][j].setContentAreaFilled(true);
				boton[i][j].mina = false;
				boton[i][j].setIcon(pre);
				boton[i][j].setEnabled(true);
				boton[i][j].descubierto = false;
				boton[i][j].bandera = false;
				boton[i][j].usado = false;
			}
		}
		crearminas();
		numeros();
		c.stop();
		cara_reinicio(true);
		tiempo.setText("0");
		if (columna > 19) {
			minas.setText("80");
		} else if (columna > 15) {
			minas.setText("40");
		} else {
			minas.setText("10");
		}

		fin = false;
		ganador = false;
	}

	void nivel() {
		crear_botones();
		if (columna > 19) {
			setSize(555, 670);
			cuadricula.tablero.setSize(525, 525);
			barra.setBounds(0, 0, 555, 25);
			carita.setLocation(250, 35);
			tiempo.setLocation(440, 35);
			minas.setLocation(50, 35);
			casillasNoDescubiertasLabel = new JLabel("820");
			casillasNoDescubiertasLabel.setSize(tiempo.getSize());
			casillasNoDescubiertasLabel.setLocation(100, 35);
			add(casillasNoDescubiertasLabel);
		} else if (columna > 15) {
			setSize(505, 620);
			cuadricula.tablero.setSize(475, 475);
			barra.setBounds(0, 0, 505, 25);
			carita.setLocation(225, 35);
			tiempo.setLocation(400, 35);
			minas.setLocation(50, 35);
			casillasNoDescubiertasLabel = new JLabel("216");
			casillasNoDescubiertasLabel.setSize(tiempo.getSize());
			casillasNoDescubiertasLabel.setLocation(100, 35);
			add(casillasNoDescubiertasLabel);
		} else {
			setSize(405, 520);
			cuadricula.tablero.setSize(375, 375);
			barra.setBounds(0, 0, 405, 25);
			carita.setLocation(180, 35);
			tiempo.setLocation(310, 35);
			minas.setLocation(20, 35);
			casillasNoDescubiertasLabel = new JLabel("71");
			casillasNoDescubiertasLabel.setSize(tiempo.getSize());
			casillasNoDescubiertasLabel.setLocation(100, 35);
			add(casillasNoDescubiertasLabel);
		}

		cuadricula.tablero.removeAll();
		reiniciar();
		cuadricula.crear(columna, fila);
		for (int i = 0; i < columna; i++) {
			for (int j = 0; j < fila; j++) {
				cuadricula.tablero.add(boton[i][j]);
			}
		}
	}

	void numeros() {
		for (int i = 0; i < columna; i++) {
			for (int j = 0; j < fila; j++) {
				int con = 0, aux1, aux2;
				aux1 = i;
				aux2 = j;
				for (int k = 0; k < 8; k++) {
					aux1 += posiciones3[k];
					aux2 += posiciones4[k];
					if (aux1 > -1 && aux1 < columna && aux2 > -1 && aux2 < fila) {
						if (boton[aux1][aux2].mina) {
							con++;
						}
					}
				}
				boton[i][j].minas_adyacentes = con;
			}
		}
	}

	void cambiar(int columna, int fila) {
		boton[columna][fila].cambiarimagen("/imagenes/numero" + boton[columna][fila].minas_adyacentes + ".png");
		boton[columna][fila].setBorderPainted(false);
		boton[columna][fila].setContentAreaFilled(false);
		boton[columna][fila].enable(false);
		boton[columna][fila].usado = true;
	}

	void numeros(int posicion_x, int posicion_y) {
		
		if (!(posicion_x > -1 && posicion_x < columna && posicion_y > -1 && posicion_y < fila)
				|| boton[posicion_x][posicion_y].descubierto) {
			return;
		}
		if (boton[posicion_x][posicion_y].mina) {
			return;
		}
		

		boton[posicion_x][posicion_y].descubierto = true;
		casillasNoDescubiertas--;
		casillasNoDescubiertasLabel.setText(String.valueOf(Integer.parseInt(casillasNoDescubiertasLabel.getText())-1));
		boton[posicion_x][posicion_y].usado = true;
//		casillasNoDescubiertasLabel.setText("" + casillasNoDescubiertas);
		if (boton[posicion_x][posicion_y].minas_adyacentes > 0) {
			cambiar(posicion_x, posicion_y);
			return;
		}
		boton[posicion_x][posicion_y].setVisible(false);
		
		
		for (int i = 0; i < 8; i++) {
			numeros(posicion_x + posiciones1[i], posicion_y + posiciones2[i]);
		}
	}

	// *****************************************************************************************
	

	void crear_botones() {
		boton = new botones[columna][fila];
		for (int i = 0; i < columna; i++) {
			for (int j = 0; j < fila; j++) {
				boton[i][j] = new botones(i);
				final int y2 = i;
				final int y3 = j;

				boton[i][j].addMouseListener(new java.awt.event.MouseListener() {
					public void mouseClicked(MouseEvent e) {
						if (boton[y2][y3].usado || ganador || fin) {
							return;
						}

						if (e.getButton() == MouseEvent.BUTTON3) {
							if (boton[y2][y3].bandera) {
								boton[y2][y3].setIcon(pre);
								boton[y2][y3].setEnabled(true);
								boton[y2][y3].bandera = false;
								minas.setText((Integer.parseInt(minas.getText()) + 1) + "");
							} else {
								boton[y2][y3].cambiarimagen("/imagenes/bandera.png");
								boton[y2][y3].bandera = true;
								minas.setText((Integer.parseInt(minas.getText()) - 1) + "");
							}
							if (ganador()) {
								ganador = true;
								JOptionPane.showMessageDialog(null, "Ganaste la partida!");
							}
						}
					}

					public void mousePressed(MouseEvent e) {
					}

					public void mouseReleased(MouseEvent e) {
					}

					public void mouseEntered(MouseEvent e) {
					}

					public void mouseExited(MouseEvent e) {
					}
				});

				boton[i][j].addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (ganador || fin) {
							return;
						}
						if (!ganador) {
							if (!c.isAlive()) {
								c = new cronometro(tiempo, 0);
								c.start();
							}
						}
						if (boton[y2][y3].mina) {
							boton[y2][y3].cambiarimagen("/imagenes/mina.png");
							boton[y2][y3].setBorderPainted(false);
							boton[y2][y3].setContentAreaFilled(false);
							cara_reinicio(false);
							c.stop();
							final_juego();
						} else {
							numeros(y2, y3);
							boton[y2][y3].usado = true;
							if (ganador()) {
								int tiempo = cronometro.getJ();
								String usuario = PanelLogin.nomUserLog();
								System.out.println(tiempo+","+usuario+","+dificultadRanking);
								inserirBD(tiempo, usuario, dificultadRanking);
								ganador = true;
								JOptionPane.showMessageDialog(null, "Ganaste la partida!");
							}
						}

					}
				});
			}
		}
	}

	public void inserirBD(int tiempo, String usuario, String nivel) {

		try {
		    Class.forName("com.mysql.cj.jdbc.Driver");

		    String urlBaseDades = "jdbc:mysql://ticsimarro.org:3306/1daw01_pro";

		    String usuari = "1daw01_pro";
		    String contrasenya = "oETQoCsk0r";
		    Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

		    Statement s = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

		    String consultaSQL = "INSERT INTO rankingBuscamines (nomusuari, temps, nivel) VALUES ('" + usuario + "', " + tiempo + ", '" + nivel + "')";
		    s.executeUpdate(consultaSQL);

		    c.close();
		} catch (ClassNotFoundException | SQLException e) {
		    e.printStackTrace();
		}
	}
	
	private void pedirTamanio(String usuario) {
    	String opcionSeleccionada = "";
        String[] options = {"Principiante", "Intermedio", "Experto", "Cargar"};

        int eleccion = JOptionPane.showOptionDialog(ventana.this, "Selecciona la dificultad", "Dificultad", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (eleccion == JOptionPane.CLOSED_OPTION) {
            vacio = true;
            PanelInicio.setContadorBusca(0);
            PanelInicio.ponerVvacio();
            dispose();

        } else if (eleccion == 0) {
            vacio = false;
            can_minas = 10;
            columna = fila = 9;
            try {
                crearBuscaminas();
            } catch (Exception e) {
            }
            n = "principiante";
        } else if (eleccion == 1) {
            vacio = false;
            can_minas = 40;
            columna = fila = 16;
            try {
                crearBuscaminas();
            } catch (Exception e) {
            }
            n = "intermedio";
        } else if (eleccion == 2) {
            vacio = false;
            can_minas = 80;
            columna = fila = 20;
            try {
                crearBuscaminas();
            } catch (Exception e) {
            }
            n = "experto";
        } else if (eleccion == 3) {
        	//Mostrar partides de les base de datos en ComboBox
            //carga = true;
        	
        		int idUser = 0;
                try {
                  Connection c2 = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
                ArrayList<String> opciones = new ArrayList<>();
                String sql3 = "SELECT id FROM usuario WHERE correo = ?";
                PreparedStatement statement3 = c2.prepareStatement(sql3);
    			statement3.setString(1, usuario);
    			ResultSet r2 = statement3.executeQuery();
    			while(r2.next()) {
    				idUser = r2.getInt("id");
    			}
    			
    			String sql4 = "SELECT nombre_partida FROM partidas WHERE id_usuario = ? AND juego = 'Pesca Mines'";
                PreparedStatement statement4 = c2.prepareStatement(sql4);
    			statement4.setInt(1, idUser);
    			ResultSet r3 = statement4.executeQuery();
    			while(r3.next()) {
    				opciones.add(r3.getString("nombre_partida"));
    			}
                
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Selecciona una opción:");
                JComboBox<String> comboBox = new JComboBox<>(opciones.toArray(new String[opciones.size()]));
                panel.add(label);
                panel.add(comboBox);

                int seleccion = JOptionPane.showOptionDialog(null, panel, "Cargar partida",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, opciones.get(0));

                if (seleccion == JOptionPane.CLOSED_OPTION) {
                    System.out.println("Diálogo cerrado");
                    dispose();
                    
                } else {
                    opcionSeleccionada = (String) comboBox.getSelectedItem();
                    System.out.println("Opción seleccionada: " + opcionSeleccionada);
                    
                }
                cerrado = false;
                
                 //Base de Datos   
                    
                    String sql2 = "SELECT * FROM partidas WHERE nombre_partida = ? AND juego='Pesca Mines'";
                    PreparedStatement statement2 = c2.prepareStatement(sql2);
                    statement2.setString(1, opcionSeleccionada);
                    ResultSet r = statement2.executeQuery();
                    
                    while(r.next()) {
                    	try {
                    		byte[] bytesMatriz = r.getBytes("referencia_juego");
                            ByteArrayInputStream bais = new ByteArrayInputStream(bytesMatriz);
                            
                            // Crear un ObjectInputStream para deserializar los bytes en una matriz
                            ObjectInputStream ois = new ObjectInputStream(bais);
                            boton = (botones[][]) ois.readObject();
                            
                            ois.close();
                            bais.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        } catch (ClassNotFoundException c1) {
                            System.out.println("La clase botones no fue encontrada");
                            c1.printStackTrace();
                            return;
                        }
    
                    	setSize(405, 520);

                		setLocationRelativeTo(null);
                		setLayout(null);
                		setResizable(false);
                		setTitle("Buscaminas");
                		addWindowListener(new java.awt.event.WindowAdapter() {

                		});
                		
                    	carita.setSize(40, 40);
                 		carita.setLocation(180, 35);
                 		cara_reinicio(true);
                 		String tiempoGuardado = String.valueOf(r.getInt("tiempo"));
                 		tiempo.setSize(75, 30);
                 		tiempo.setLocation(310, 35);
                 		tiempo.setFont(new Font(null, (int) LEFT_ALIGNMENT, 25));
                 		tiempo.setText(tiempoGuardado);
                 		minas.setLocation(20, 35);
                 		minas.setSize(75, 30);
                 		minas.setFont(new Font(null, (int) LEFT_ALIGNMENT, 25));
                 		
                 		columna = boton.length;
                 		fila = boton[0].length;
                 		System.out.println("Columna" +columna);
                 		System.out.println("fila" +fila);

                 		if (columna > 19) {
                			setSize(555, 670);
                			cuadricula.tablero.setSize(525, 525);
                			barra.setBounds(0, 0, 555, 25);
                			carita.setLocation(250, 35);
                			tiempo.setLocation(440, 35);
                			minas.setText("80");
                			minas.setLocation(50, 35);
                			casillasNoDescubiertasLabel.setText("820");
                			casillasNoDescubiertasLabel.setSize(tiempo.getSize());
                			casillasNoDescubiertasLabel.setLocation(100, 35);
                		} else if (columna > 15) {
                			setSize(505, 620);
                			cuadricula.tablero.setSize(475, 475);
                			barra.setBounds(0, 0, 505, 25);
                			carita.setLocation(225, 35);
                			tiempo.setLocation(400, 35);
                			minas.setText("40");
                			minas.setLocation(50, 35);
                			casillasNoDescubiertasLabel.setText("216");
                			casillasNoDescubiertasLabel.setSize(tiempo.getSize());
                			casillasNoDescubiertasLabel.setLocation(100, 35);
                		} else {
                			setSize(405, 520);
                			cuadricula.tablero.setSize(375, 375);
                			barra.setBounds(0, 0, 405, 25);
                			carita.setLocation(180, 35);
                			tiempo.setLocation(310, 35);
                			minas.setLocation(20, 35);
                			casillasNoDescubiertasLabel.setText("71");
                			casillasNoDescubiertasLabel.setSize(tiempo.getSize());
                			casillasNoDescubiertasLabel.setLocation(100, 35);
                		}
                 		
                 		//Comprovar numero de banderes i ficar les correctes
                 		for (int i = 0; i < columna; i++) {
                			for (int j = 0; j < fila; j++) {
                				if(boton[i][j].bandera) {
    								minas.setText((Integer.parseInt(minas.getText()) - 1) + "");
                				}
                			}
                		}
                 		//comprovar número de casilles descubertes
                		for (int i = 0; i < columna; i++) {
                			for (int j = 0; j < fila; j++) {
                				if (boton[i][j].usado || !boton[i][j].isVisible()) {
                					casillasNoDescubiertasLabel.setText((Integer.parseInt(casillasNoDescubiertasLabel.getText()) - 1) + "");
                				}
                				
                			}
                		}
                 		
                        // Restaurar el estado de la aplicación después de cargar la matriz
                    	for (int i = 0; i < columna; i++) {
                			for (int j = 0; j < fila; j++) {
                				final int y2 = i;
                				final int y3 = j;

                				boton[i][j].addMouseListener(new java.awt.event.MouseListener() {
                					public void mouseClicked(MouseEvent e) {
                						if (boton[y2][y3].usado || ganador || fin) {
                							casillasNoDescubiertasLabel.setText((Integer.parseInt(casillasNoDescubiertasLabel.getText()) - 1) + "");
                							return;
                						}
                						if (e.getButton() == MouseEvent.BUTTON3) {
                							if (boton[y2][y3].bandera) {
                								boton[y2][y3].setIcon(pre);
                								boton[y2][y3].setEnabled(true);
                								boton[y2][y3].bandera = false;
                								minas.setText((Integer.parseInt(minas.getText()) + 1) + "");
                							} else {
                								boton[y2][y3].cambiarimagen("/imagenes/bandera.png");
                								boton[y2][y3].bandera = true;
                								minas.setText((Integer.parseInt(minas.getText()) - 1) + "");
                							}
                							if (ganador()) {
                								int tiempo = cronometro.getJ();
                								String usuario = PanelLogin.nomUserLog();
                								System.out.println(tiempo+","+usuario+","+dificultadRanking);
                								inserirBD(tiempo, usuario, dificultadRanking);
                								ganador = true;
                								JOptionPane.showMessageDialog(null, "Ganaste la partida!");
                							}
                						}
                					}

                					public void mousePressed(MouseEvent e) {
                					}

                					public void mouseReleased(MouseEvent e) {
                					}

                					public void mouseEntered(MouseEvent e) {
                					}

                					public void mouseExited(MouseEvent e) {
                					}
                				});

                				boton[i][j].addActionListener(new java.awt.event.ActionListener() {
                					public void actionPerformed(ActionEvent e) {
                						if (ganador || fin) {
                							return;
                						}
                						if (!ganador) {
                							if (!c.isAlive()) {
                								c = new cronometro(tiempo, Integer.parseInt(tiempoGuardado));
                								c.start();
                							}
                						}
                						if (boton[y2][y3].mina) {
                							boton[y2][y3].cambiarimagen("/imagenes/mina.png");
                							boton[y2][y3].setBorderPainted(false);
                							boton[y2][y3].setContentAreaFilled(false);
                							cara_reinicio(false);
                							c.stop();
                							final_juego();
                						} else {
                							numeros(y2, y3);
                							boton[y2][y3].usado = true;
                							if (ganador()) {
                								int tiempo = cronometro.getJ();
                								String usuario = PanelLogin.nomUserLog();
                								System.out.println(tiempo+","+usuario+","+dificultadRanking);
                								inserirBD(tiempo, usuario, dificultadRanking);
                								ganador = true;
                								JOptionPane.showMessageDialog(null, "ganaste la partida!");
                							}
                						}

                					}
                				});
                			}
                		}
                        // ...
    
                        // Por ejemplo, podrías actualizar la interfaz gráfica
                        cuadricula.tablero.removeAll();
                    	//reiniciar();
                        cuadricula.crear(columna, fila);
                        for (int i = 0; i < columna; i++) {
                            for (int j = 0; j < fila; j++) {
                                cuadricula.tablero.add(boton[i][j]);
                            }
                        }	
                        
                			pre = boton[0][0].getDisabledIcon();	
                			file.add(nuevo);

                			file.add(ranking);
                			file.add(salir);
                			barra.add(file);
                			barra.setBounds(0, 0, 405, 25);
                			barra.add(ayuda);
                			ayuda.add(instrucciones);

                			principiante.addActionListener(this);
                			intermedio.addActionListener(this);
                			experto.addActionListener(this);
                			salir.addActionListener(this);
                			nuevo.addActionListener(this);
                			ranking.addActionListener(this);
                			instrucciones.addActionListener(this);
                			carita.addActionListener(this);
                			this.addMouseListener(this);

                			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icono.png"));
                			setIconImage(icon);

                			add(barra);
                			add(carita);
                			add(cuadricula.tablero);
                			add(minas);
                			add(tiempo);
                			add(casillasNoDescubiertasLabel);
                			setVisible(true);

                			
                    	
                    }
              
                } catch (Exception e) {
                    System.out.println("Error al establecer la conexión con la base de datos: " + e.getMessage());
                    e.printStackTrace();
                }	
            
     }
	}
	
	public ventana(String usuario) throws IOException {
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				  
				c.stop();
				guardarBuscaminas(usuario);
                PanelInicio.setContadorBusca(0);
                PanelInicio.ponerVvacio();
			} 
		});
		
		pedirTamanio(usuario);
		
		ranking.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
			    Class.forName("com.mysql.cj.jdbc.Driver");

			    String urlBaseDades = "jdbc:mysql://ticsimarro.org:3306/1daw01_pro";

			    String usuari = "1daw01_pro";
			    String contrasenya = "oETQoCsk0r";
			    Connection c = DriverManager.getConnection(urlBaseDades, usuari, contrasenya);

			    Statement s = c.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			    int contador = 0;
			    StringBuilder contenido = new StringBuilder();
			    
				if (n == "principiante") {
					
					String consultaSQL = "SELECT * FROM rankingBuscamines WHERE nivel = 'principiante' ORDER BY temps ASC";
				    ResultSet resultado = s.executeQuery(consultaSQL);
					    
				
					    while (resultado.next() && contador < 10) {
					        String nomusuari = resultado.getString("nomusuari");
					        int temps = resultado.getInt("temps");
					        String nivel = resultado.getString("nivel");

					        System.out.println("Usuario: " + nomusuari + ", Tiempo: " + temps + ", Nivel: " + nivel);
					        contenido.append(nomusuari).append(" : ").append(temps).append("\n");

					        contador++;
					    }
					    JOptionPane.showMessageDialog(null, contenido.toString(), "Ranking principiant", JOptionPane.INFORMATION_MESSAGE);
					    resultado.close();
					
					
				}else if (n == "intermedio") {
					
					String consultaSQL = "SELECT * FROM rankingBuscamines WHERE nivel = 'intermedio' ORDER BY temps ASC";
				    ResultSet resultado = s.executeQuery(consultaSQL);
					    
				  
					    while (resultado.next() && contador < 10) {
					        String nomusuari = resultado.getString("nomusuari");
					        int temps = resultado.getInt("temps");
					        String nivel = resultado.getString("nivel");

					        System.out.println("Usuario: " + nomusuari + ", Tiempo: " + temps + ", Nivel: " + nivel);
					        contenido.append(nomusuari).append(" : ").append(temps).append("\n");

					        contador++;
					    }
					    JOptionPane.showMessageDialog(null, contenido.toString(), "Ranking intermig", JOptionPane.INFORMATION_MESSAGE);
					    resultado.close();


				} else if (n == "experto") {
					String consultaSQL = "SELECT * FROM rankingBuscamines WHERE nivel = 'experto' ORDER BY temps ASC";
				    ResultSet resultado = s.executeQuery(consultaSQL);
					    
				  
					    while (resultado.next() && contador < 10) {
					        String nomusuari = resultado.getString("nomusuari");
					        int temps = resultado.getInt("temps");
					        String nivel = resultado.getString("nivel");

					        System.out.println("Usuario: " + nomusuari + ", Tiempo: " + temps + ", Nivel: " + nivel);
					        contenido.append(nomusuari).append(" : ").append(temps).append("\n");

					        contador++;
					    }
					    JOptionPane.showMessageDialog(null, contenido.toString(), "Ranking expert", JOptionPane.INFORMATION_MESSAGE);
					    resultado.close();
					
				} 
					    
					    s.close();
					    c.close();
} catch (ClassNotFoundException | SQLException e1) {
					    e1.printStackTrace();
					}
			}
		});
		
	}
	
	public void crearBuscaminas() {
		setSize(405, 520);

		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		setTitle("Buscaminas");
		addWindowListener(new java.awt.event.WindowAdapter() {

		});
		carita.setSize(40, 40);
		carita.setLocation(180, 35);
		cara_reinicio(true);

		tiempo.setSize(75, 30);
		tiempo.setLocation(310, 35);
		tiempo.setFont(new Font(null, (int) LEFT_ALIGNMENT, 25));
		tiempo.setText("0: 0");
		minas.setLocation(20, 35);
		minas.setSize(75, 30);
		minas.setFont(new Font(null, (int) LEFT_ALIGNMENT, 25));

		
		
			nivel();
			pre = boton[0][0].getDisabledIcon();	
			file.add(nuevo);

			file.add(ranking);
			file.add(salir);
			barra.add(file);
			barra.setBounds(0, 0, 405, 25);
			barra.add(ayuda);
			ayuda.add(instrucciones);


			principiante.addActionListener(this);
			intermedio.addActionListener(this);
			experto.addActionListener(this);
			salir.addActionListener(this);
			nuevo.addActionListener(this);
			ranking.addActionListener(this);
			instrucciones.addActionListener(this);
			carita.addActionListener(this);
			this.addMouseListener(this);

			Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/imagenes/icono.png"));
			setIconImage(icon);

			add(barra);
			add(carita);
			add(cuadricula.tablero);
			add(minas);
			add(tiempo);
			add(casillasNoDescubiertasLabel);
			setVisible(true);

			
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == salir) {
			System.exit(0);
			return;
		}

		if (evt.getSource() == instrucciones) {
			JOptionPane.showMessageDialog(null, "click izquierdo selecciona la casilla\n click derecho coloca bandera");
			return;
		}
		if (evt.getSource() == principiante) {
			can_minas = 10;
			columna = fila = 9;
			try {
				nivel();
			} catch (Exception e) {
			}
			n = "principiante";
			return;
		}
		if (evt.getSource() == intermedio) {
			can_minas = 40;
			columna = fila = 16;
			try {
				nivel();
			} catch (Exception e) {
			}
			n = "intermedio";
			return;
		}
		if (evt.getSource() == experto) {
			can_minas = 80;
			columna = fila = 20;
			try {
				nivel();
			} catch (Exception e) {
			}
			n = "experto";
			return;
		}

		if (evt.getSource() == carita || evt.getSource() == nuevo) {
			reiniciar();
		}
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		this.requestFocus();
	}

	public void mouseExited(MouseEvent e) {
	}
	
	public static void guardarBuscaminas(String usuario) {
		int opcion = JOptionPane.showConfirmDialog(null, "¿Deseas guardar los cambios en el Buscaminas?", "Guardar cambios",
				JOptionPane.YES_NO_OPTION);
		
		boolean mismaPartida = false;
		int repeticiones = 0;
		try {
		int id = 0;
		if (opcion == JOptionPane.YES_OPTION) {
			String nombrePartida = "";
			while (nombrePartida.isBlank()) {
				nombrePartida = JOptionPane.showInputDialog(null, "Ingrese el nombre de la partida:",
						"Nombre de partida", JOptionPane.PLAIN_MESSAGE);

			}
			Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro",
					"oETQoCsk0r");
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
				pepe.setString(1, "Pesca Mines");
				pepe.setString(2, nombrePartida);
				pepe.setInt(3, id);
				
				ResultSet r5 = pepe.executeQuery();
				while (r5.next()) {
					repeticiones = r5.getInt("pepas");

				}
				if (repeticiones > 0) {
					
					nombrePartida = JOptionPane.showInputDialog(null, "Ingrese el nombre de la partida, no debe ser repetido:",
							"Nombre de partida", JOptionPane.PLAIN_MESSAGE);
				}else {
					mismaPartida = true;
				}
			} while (mismaPartida == false);
			
				byte[] bytesMatriz = null;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(boton);
				bytesMatriz = baos.toByteArray();

				

				String sql = "SELECT id FROM usuario WHERE correo = ?";

				PreparedStatement s = c.prepareStatement(sql);
				s.setString(1, usuario);
				
				ResultSet r = s.executeQuery();
				while (r.next()) {
					id = r.getInt("id");
				}
				String sentencia = "INSERT INTO partidas (id_usuario, juego,referencia_juego, nombre_partida, tiempo) VALUES (?,?,?,?,?)";
				PreparedStatement s2 = c.prepareStatement(sentencia);

				s2.setInt(1, id);
				s2.setString(2, "Pesca Mines");
				s2.setBytes(3, bytesMatriz);
				s2.setString(4, nombrePartida);
				s2.setInt(5, cronometro.getJ());
				s2.executeUpdate();

			} 

			}catch (Exception e2) {
				System.out.println("Error al conectar con la base de datos: " + e2.getMessage());
			}
		
		}
}