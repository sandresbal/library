package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import beans.Tema;

public class TemaDAO implements IntGenericoCRUD<Tema, Integer>{
	
	private String sql;
	private Connection cn;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public TemaDAO(Connection c) {
		this.cn = c;
	}

	@Override
	public int insert(Tema entidad) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	@Override
	public List<Tema> findAll() {
		sql ="select * from temas";
		List<Tema> lista_temas = new ArrayList<>();
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				lista_temas.add(cargarDatos());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista_temas;
	}

	@Override
	public Tema findById(Integer key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Tema entidad) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Integer key) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	public Tema cargarDatos() throws SQLException{
		Tema t = new Tema();
		t.setIdTema(rs.getInt(1));
		t.setTema(rs.getString(2));
		return t;
	}
	
	
	/*public int idFromTheme(String s) {
		int idTheme = 0;
		Tema t = new Tema();
		sql = "select idTema from temas where tema like ?";
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, s);
			rs = ps.executeQuery();
			while (rs.next()) {
				idTheme = rs.getInt("idTema");
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idTheme;
	}*/
	
}
