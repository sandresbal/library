package test;

import java.sql.Connection;

import modelo.Conexiones;

public class TestConexion {

	public static void main(String[] args) {
		Connection conn = Conexiones.establecerConexion();
		Conexiones.finalizarConexion(conn);
		System.out.println("proceso terminado");

	}

}
