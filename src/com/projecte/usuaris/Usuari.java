package com.projecte.usuaris;

public class Usuari {
	private String nombre;
	private String apellidos;
	private static String correo;
	private String poblacio;
	private byte[] imagenByte;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public static String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		Usuari.correo = correo;
	}
	public String getPoblacio() {
		return poblacio;
	}
	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}
	public byte[] getImagenByte() {
		return imagenByte;
	}
	public void setImagenByte(byte[] imagenByte) {
		this.imagenByte = imagenByte;
	}
	
	public Usuari(String nombre, String apellidos, String correo, String poblacio, byte[] imagenByte) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		Usuari.correo = correo;
		this.poblacio = poblacio;
		this.imagenByte = imagenByte;
	}
	
	
	
}
