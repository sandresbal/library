package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import beans.Cliente;

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
		sql = "select * where usuario=? AND password = ?";
		boolean b = false;
		String nombre = c.getUsuario();
		String pass_introducido = c.getPassword();
		
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, nombre);
			ps.setString(2, pass_introducido);
			rs = ps.executeQuery();
			if (!rs.next()) {
				b = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}
}
