package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import beans.Cliente;
import beans.Venta;

public class ClienteDAO implements IntGenericoCRUD<Cliente, Integer> {

	private String sql;
	private PreparedStatement ps;
	private ResultSet rs;
	private Connection cn;
	
	public ClienteDAO (Connection c) {
		this.cn = c;
	}
	
	@Override
	public int insert(Cliente entidad) {
		sql = "insert into clientes (usuario, password, email, telefono) values (?, ?, ?, ?)";
		int filasCambiadas = -1;
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, entidad.getUsuario());
			ps.setString(2, entidad.getPassword());
			ps.setString(3, entidad.getEmail());
			ps.setInt(4, entidad.getTelefono());
			filasCambiadas = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filasCambiadas;
	}

	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente findById(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Cliente findByName(String srt) {
		Cliente v = null;
		sql = "select * from clientes where usuario = ?";
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, srt);
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
	public int update(Cliente entidad) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer key) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public boolean checkUser(Cliente c) {
		sql = "select * from clientes where usuario like ? AND password like ?";
		boolean b = true;
		String nombre = c.getUsuario();
		System.out.println(nombre);
		String pass_introducido = c.getPassword();
		System.out.println(pass_introducido);
		
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, pass_introducido);
			rs = ps.executeQuery();
			if (!rs.next()) {
				b = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public Cliente cargarDatos() throws SQLException{
		Cliente c = new Cliente();
		c.setIdCliente(rs.getInt(1));
		c.setUsuario(rs.getString(2));
		c.setPassword(rs.getString(3));
		c.setEmail(rs.getString(4));
		c.setTelefono(rs.getInt(5));
		return c;
	}
}
