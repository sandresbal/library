package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.Venta;

public class VentaDAO implements IntGenericoCRUD<Venta, String>{
	
	private Connection conn;
	private String sql;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public VentaDAO(Connection cn) {
		this.conn = cn;
	}

	@Override
	public int insert(Venta entidad) {
		int filasCambiadas = -1;
		sql = "insert into ventas (idCliente, idLibro, fecha) values(?, ?, ?)";
		Date fecha = new Date();
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, entidad.getIdCliente());
			ps.setInt(2, entidad.getIdLibro());
			ps.setDate(3, (java.sql.Date) fecha);
			filasCambiadas = ps.executeUpdate();
			
		}catch(SQLException ex) {
			ex.printStackTrace();		
		}
		return filasCambiadas;
	}

	@Override
	public List<Venta> findAll() {
		sql = "select * from ventas";
		List<Venta> lista_de_ventas = new ArrayList<Venta>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				lista_de_ventas.add(cargarDatos());
			}
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return lista_de_ventas;
	}

	@Override
	public Venta findById(String clave) {
		Venta v = null;
		sql = "select * from ventas where idVenta= ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, clave);
			rs = ps.executeQuery();
			if (rs.next()) {
				v = cargarDatos();
			}
			
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return v;
	}

	@Override
	public int update(Venta entidad) {
		int filasCambiadas = -1;
		sql = "update venta set idCliente =?, idLibro=?, fecha= ? where idVenta = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, entidad.getIdCliente());
			ps.setInt(2, entidad.getIdLibro());
			ps.setDate(3, entidad.getFecha());
			ps.setInt(4, entidad.getIdVenta());
			filasCambiadas = ps.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
			}
		return filasCambiadas;
	}

	@Override
	public int delete(String clave) {
		int i = -1;
		sql = "delete from ventas where idVenta= ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, clave);
			i = ps.executeUpdate();
		}catch(SQLException ex) {
			ex.printStackTrace();
		}
		return i;
	}

	
	public Venta cargarDatos() throws SQLException{
		Venta ven = new Venta();
		ven.setIdVenta(rs.getInt(1));
		ven.setIdCliente(rs.getInt(2));
		ven.setIdLibro(rs.getInt(3));
		ven.setFecha(rs.getDate(4));
		return ven;
	}

}
