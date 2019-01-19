package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexiones {
	
	private static String driver = "com.mysql.cj.jdbc.Driver";
	private static String user = "root";
	private static String pass = "root";
	private static String url = 
			"jdbc:mysql://localhost:8889/actividad_8?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";

	
	static {
		try {
			Class.forName(driver);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection establecerConexion() {
		Connection cn = null;
		try {
			cn = DriverManager.getConnection(url, user, pass);
			System.out.print("conexión establecida");
			
		} catch (SQLException ex) {
			ex.printStackTrace();
			System.out.println("No se puede establecer la conexión");
		}
		return cn;
	}
	
	public static void finalizarConexion(Connection conn) {
		try {
			conn.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
