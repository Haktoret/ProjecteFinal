package com.projecte.paneles;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.joc.buscamines.ventana;
import com.joc.jocDeLaVida.JocDeLaVida;
import com.joc.pixelART.PixelArt;
import com.projectefinal.menuprincipal.menu;

public class PanelInicio extends JPanel{
	
	
	private static PixelArt p;
	private static JocDeLaVida j;
	private static ventana v;
	
	
	private static int contadorPixel = 0;
	private static int contadorBusca = 0;
	private static int contadorJoc = 0;
	private static int contadorPixe2 = 0;
	private static boolean salir = false;
	
	
	
	
	public static int getContadorPixe2() {
		return contadorPixe2;
	}
	public static void setContadorPixe2(int contadorPixe2) {
		PanelInicio.contadorPixe2 = contadorPixe2;
	}
	public static int getContadorPixel() {
		return contadorPixel;
	}
	public static void setContadorPixel(int contadorPixel) {
		PanelInicio.contadorPixel = contadorPixel;
	}
	public static int getContadorBusca() {
		return contadorBusca;
	}
	public static void setContadorBusca(int contadorBusca) {
		PanelInicio.contadorBusca = contadorBusca;
	}
	public int getContadorJoc() {
		return contadorJoc;
	}
	public static void setContadorJoc(int contadorJoc) {
		PanelInicio.contadorJoc = contadorJoc;
	}
	
	
	
	
	public static PixelArt getP() {
		return p;
	}
	public static void setP(PixelArt p) {
		PanelInicio.p = p;
	}
	public PanelInicio(String nomUser) {
		
		this.setLayout(new BorderLayout());
		JPanel pSaludo = new JPanel();
		JLabel saludo = new JLabel();
		saludo.setText("Hola: "+nomUser);
		Font fuente = new Font("Arial", Font.BOLD, 30);
		saludo.setFont(fuente);
		pSaludo.add(saludo);
		//pagina.add(pSaludo, BorderLayout.NORTH);
		
		JPanel imagenes = new JPanel();
		imagenes.setLayout(new GridLayout(0,3));
		JLabel buscaMinas = new JLabel();
		String rutaMinas = "buscaminas.png";
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
		
        
		this.add(pSaludo, BorderLayout.NORTH);
		this.add(imagenes, BorderLayout.CENTER);
		this.add(botones2, BorderLayout.SOUTH);
		
		pixelArt.addMouseListener(new MouseAdapter() {

			
			
			public void mousePressed(MouseEvent e) {
				if(salir) {
					setContadorPixel(0);
					
				}
				
				//System.out.println(getContadorPixel());
				if(getContadorPixel() == 0) {
					salir = false;
					setContadorPixel(1);
					p = new PixelArt(nomUser);
					
					
				}
				
			}
			
		});
		
		juegoVida.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				if(getContadorJoc() == 0) {
					j = new JocDeLaVida();
					setContadorJoc(1);
				}
			}
			
		});
		
		buscaMinas.addMouseListener(new MouseAdapter() {


			
			public void mousePressed(MouseEvent e) {
				
				if(getContadorBusca() == 0) {
					try {
						setContadorBusca(1);
						v = new ventana(nomUser);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
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
		            statement.setString(1, nomUser);
		            ResultSet resultSet = statement.executeQuery();
		            
		            if(resultSet.next()) {
		            	byte[] imageBytes = resultSet.getBytes("imagen");
		            	ImageIcon imageIcon = new ImageIcon(imageBytes);
		            	String nom = resultSet.getString("nombre");
		            	String apellidos = resultSet.getString("apellidos");
		            	String correo = resultSet.getString("correo");
		            	String poblacio = resultSet.getString("poblacion");
		            	menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelInicio.this);
						mRegistro.getContentPane().removeAll();
						VerPerfil p = new VerPerfil(nom, apellidos, correo, poblacio, imageBytes);
						mRegistro.getContentPane().add(p);
						mRegistro.getContentPane().revalidate();
						mRegistro.getContentPane().repaint();
						mRegistro.setSize(700,900);
		            }
		            
		            
		           
		            
		        } catch (Exception e1) {
		            System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
		        } 
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
		            	if(r.getString("correo").equals(nomUser)) {
		            		idElim = r.getInt("id");
		            		break;
		            	}
		            }
		            s.executeUpdate("DELETE FROM password WHERE id_usuario = " + idElim);
		            s.executeUpdate("DELETE FROM usuario WHERE id = " + idElim);

		            
		            
		            menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelInicio.this);
		            PanelLogin mostrarLogin = new PanelLogin();
		            mRegistro.getContentPane().removeAll();
		            mRegistro.getContentPane().add(mostrarLogin);
		            mRegistro.getContentPane().revalidate();
		            mRegistro.getContentPane().repaint();
		            
		            
		        } catch (Exception e1) {
		            System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
		        } 
			}
			
		});
		boton1.addActionListener(new ActionListener() {
			
						@Override
						public void actionPerformed(ActionEvent e) {
							
								if(p != null) {
									if(p.isVacio()) {
										setContadorPixel(0);
										
									}else {
										p.cerrarPixelArt(nomUser);
										setContadorPixel(0);

									}
									
									p.dispose();
									//ponerPvacio();
								}
								if(j != null) {
									j.dispose();
								}
								
								if(v != null) {
									if(v.isVacio()) {
										setContadorBusca(0);

									}else {
										v.guardarBuscaminas(nomUser);
										setContadorBusca(0);
									}
									
									v.dispose();
								}
							
							
							menu mRegistro = (menu) SwingUtilities.getWindowAncestor(PanelInicio.this);
				            PanelLogin mostrarLogin = new PanelLogin();
				            mRegistro.getContentPane().removeAll();
				            mRegistro.getContentPane().add(mostrarLogin);
				            mRegistro.getContentPane().revalidate();
				            mRegistro.getContentPane().repaint();
						}
						
					});
		
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
	
	public static void ponerPvacio() {
		PanelInicio.p = null;
	}
	
	public static void ponerVvacio() {
		PanelInicio.v = null;
	}
	
	public static void contadorA0() {
		PanelInicio.contadorPixel = 0;
		salir = true;
	}
}
