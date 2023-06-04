package com.projectefinal.menuprincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
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

import com.joc.buscamines.ventana;
import com.joc.pixelART.PixelArt;
import com.projecte.paneles.PanelInicio;
import com.projecte.paneles.PanelLogin;


public class menu extends JFrame {
	private PixelArt p = PanelInicio.getP();
	private Container pagina = this.getContentPane();

	public menu() throws HeadlessException {
		pagina.setLayout(new BorderLayout());
		crearTablas();
		mostrarLogin();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(700, 550);
		this.setVisible(true);
	}

	public void mostrarLogin() {
		PanelLogin mostrarLogin = new PanelLogin();
		pagina.add(mostrarLogin, BorderLayout.CENTER);
	}

	public void crearTablas() {
		try {
			// Cargar el driver de MySQL
			// Class.forName("org.mysql.jdbc.Driver");

			// Establecer la conexión con la base de datos
			Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro",
					"oETQoCsk0r");

			Statement s = c.createStatement();
			s.execute(
					"CREATE TABLE IF NOT EXISTS usuario (id INT PRIMARY KEY AUTO_INCREMENT, nombre VARCHAR(20) NOT NULL, apellidos VARCHAR(50) NOT NULL, correo VARCHAR(100) NOT NULL, poblacion VARCHAR(25) NOT NULL, imagen LONGBLOB NOT NULL)");

			s.execute(
					"CREATE TABLE IF NOT EXISTS password (id INT PRIMARY KEY AUTO_INCREMENT, id_usuario INT NOT NULL REFERENCES usuario(id), hash LONGTEXT NOT NULL, salt LONGTEXT NOT NULL)");

            s.execute("CREATE TABLE IF NOT EXISTS partidas (id INT PRIMARY KEY AUTO_INCREMENT, id_usuario INT NOT NULL REFERENCES usuario(id), juego VARCHAR(20) NOT NULL, referencia_juego LONGBLOB, nombre_partida VARCHAR(25) NOT NULL, tiempo INT(10), cuadroMida INT(20), ventanaMida INT(20))");

		} catch (Exception e1) {
			System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
		}
	}

}
