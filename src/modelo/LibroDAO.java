package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import beans.Libro;

public class LibroDAO implements IntGenericoCRUD<Libro, Integer> {
	
	private Connection cn;
	private String sql;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public LibroDAO(Connection cnn) {
		this.cn = cnn;
	}

	@Override
	public int insert(Libro entidad) {
		int filasCambiadas = -1;
		sql = "insert into libros values (?, ?, ?. ?. ?, ?)";
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, entidad.getIdLibro());
			ps.setString(2, entidad.getTitulo());
			ps.setString(3, entidad.getAutor());
			ps.setDouble(4, entidad.getPrecio());
			ps.setInt(4, entidad.getPaginas());
			ps.setInt(5, entidad.getIdTema());
			filasCambiadas = ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return filasCambiadas;
	}

	@Override
	public List<Libro> findAll() {
		List<Libro> lista_libros = new ArrayList<Libro>();
		sql = "select * from libros";
		try {
			ps = cn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				lista_libros.add(cargarDatos());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return lista_libros;
	}

	@Override
	public Libro findById(Integer key) {
		Libro l = new Libro();
		sql = "select * from libros where isbn= ?";
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, key);
			rs = ps.executeQuery();
			if (rs.next()) {
				l = cargarDatos();
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	@Override
	public int update(Libro entidad) {
		int filasCambiadas = -1;
		sql = "update libros set titulo =?, autor = ?, precio =?, paginas =?, idTema =? where isbn =?";
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, entidad.getTitulo());
			ps.setString(2, entidad.getAutor());
			ps.setDouble(3, entidad.getPrecio());
			ps.setInt(4, entidad.getPaginas());
			ps.setInt(5, entidad.getIdTema());
			filasCambiadas = ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return filasCambiadas;
	}

	@Override
	public int delete(Integer key) {
		int filasCambiadas = -1;
		sql = "delete from libros where isbn=?";
		try {
			ps = cn.prepareStatement(sql);
			ps.setInt(1, key);
			filasCambiadas = ps.executeUpdate();
			
		}catch (SQLException ex) {
			ex.printStackTrace();
		}
		return filasCambiadas;
	}

	public Libro cargarDatos() throws SQLException {
		Libro libro = new Libro();
		libro.setIdLibro(rs.getInt(1));
		libro.setTitulo(rs.getString(2));
		libro.setAutor(rs.getString(3));
		libro.setPrecio(rs.getDouble(4));
		libro.setPaginas(rs.getInt(5));
		libro.setIdTema(rs.getInt(6));
		return libro;
	}
	
	public Libro cargarDatos2() throws SQLException {
		Libro libro = new Libro();
		libro.setTitulo(rs.getString(1));
		libro.setAutor(rs.getString(2));
		libro.setPrecio(rs.getDouble(3));
		return libro;
	}
	
	public ArrayList<Libro> findByTheme(String s) {
		ArrayList<Libro> lista_libros = new ArrayList<>();
		sql = "select titulo, autor, precio from libros join temas on libros.idTema = temas.idTema where tema like ?";
		try {
			ps = cn.prepareStatement(sql);
			ps.setString(1, s);
			rs = ps.executeQuery();
			while (rs.next()) {
				lista_libros.add(cargarDatos2());
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Tamaño del arraylist: "+ lista_libros.size());
		System.out.println("hemos encontrado: ");
		Iterator<Libro> it = lista_libros.iterator();
		while (it.hasNext()){
			Libro l = it.next();
			System.out.println(l.getTitulo());

		}
		return lista_libros;
	}
}
