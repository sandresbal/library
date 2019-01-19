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
		List<Tema> lista_temas = new ArrayList<Tema>();
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
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
	
	public String[] traerNombres() {
		sql = "select nombres from temas";
		Tema t =  new Tema();
		String[] temas = null;
		List<Tema> lista_temas = new ArrayList<Tema>();
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				t = traerNombre();
				lista_temas.add(t);
			}
			int longLista = lista_temas.size();
			temas = new String[longLista - 1];
			int longArray = temas.length;
			for (int i = longArray; i < longLista; i++){
				temas[i] = lista_temas.get(i).getTema();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temas;
	}
	
	public Tema traerNombre() throws SQLException {
		Tema t =  new Tema();
		t.setTema(rs.getString(2));
		return t;
	}
	
}
