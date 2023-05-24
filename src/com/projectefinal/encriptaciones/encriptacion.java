package com.projectefinal.encriptaciones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class encriptacion {
	public static final int longitudSalt = 4;
	public static final int iteracions = 10000;
	public static final int longitudHash = 256;

	public static String generarSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[longitudSalt];
		random.nextBytes(salt);
		String saltCadena = "";
		for (int i = 0; i < salt.length; i++) {
		    saltCadena += String.format("%02x", salt[i]); // Interprte la conversion de Bytes a cadena
		}
		return saltCadena;
	}
	
	public static String generarHash(String contrasenya, String salt){
	    char[] contrasenyaChars = contrasenya.toCharArray();
	    byte[] saltBytes = salt.getBytes();

	    PBEKeySpec spec = new PBEKeySpec(contrasenyaChars, saltBytes, iteracions, longitudHash);
	    SecretKeyFactory skf;
	    byte[] hashBytes = null;
		try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			try {
				hashBytes = skf.generateSecret(spec).getEncoded();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return Base64.getEncoder().encodeToString(hashBytes);
	}
	
	public static void ponerBDD(int idUser, String hash ,String salt) {
		
		try {
            // Cargar el driver de MySQL
           //Class.forName("org.mysql.jdbc.Driver");

            // Establecer la conexión con la base de datos
            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
            String linea = "INSERT INTO password (id_usuario,hash,salt) VALUES (?,?,?)";
           // String linea = "INSERT INTO login (salt,user,password) VALUES (?,?)";

            PreparedStatement s = c.prepareStatement(linea);
            s.setInt(1, idUser);
            s.setString(2, hash);
            s.setString(3, salt);
            s.executeUpdate();
            
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        } 
		
		
		
		
	}
	public static boolean verificarContra(String nomUser, String contrasenya) throws FileNotFoundException, IOException {
	    
		try {
            // Cargar el driver de MySQL
           //Class.forName("org.mysql.jdbc.Driver");

            // Establecer la conexión con la base de datos
            Connection c = DriverManager.getConnection("jdbc:mysql://ticsimarro.org:3306/1daw01_pro", "1daw01_pro", "oETQoCsk0r");
            
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM usuario,password WHERE usuario.id = password.id_usuario");
            while(r.next()) {
            	if(r.getString("correo").equals(nomUser)) {
            		String testHash = generarHash(contrasenya, r.getString("salt"));
            		if(testHash.equals(r.getString("hash"))) {
            			
            			return true;
            		}
            	}
            }
            
            
        } catch (Exception e1) {
            System.out.println("Error al conectar con la base de datos: " + e1.getMessage());
        } 
	   
	    return false;
	}
}
