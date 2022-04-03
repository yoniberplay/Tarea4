package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Vista.VentanaEmergente;



//Creacion del objeto a usar en todas las conexiones usando el patron de diseno SINGLETON
public class Conexion {
	
	private static Conexion obj = new Conexion();
	private Connection conexion;
	
	private Conexion() {
		try {
			conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tarea4", "root", "");
			System.out.println("Conectado a la base de datos.");
		} catch (SQLException e) {
			VentanaEmergente.AvisoEmergente("Error al conectarse a la Base de datos.");
		}
	}
	
	public static Conexion getobj() {
		
		return obj;
		
	}
	
	public Connection getConexion() {
		return conexion;
	}

}
