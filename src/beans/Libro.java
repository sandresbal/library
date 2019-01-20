package beans;

import java.io.Serializable;
import java.util.HashSet;

public class Libro implements Serializable {
	
	private int idLibro, paginas, idTema;
	private String titulo, autor;
	private Double precio;
	
	public Libro() {
		super();
	}
	
	public Libro(int id, String t, String a, Double pr, int pa, int idT) {
		this.idLibro = id;
		this.paginas = pa;
		this.idTema = idT;
		this.titulo = t;
		this.autor = a;
		this.precio = pr;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public int getPaginas() {
		return paginas;
	}

	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}

	public int getIdTema() {
		return idTema;
	}

	public void setIdTema(int idTema) {
		this.idTema = idTema;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((autor == null) ? 0 : autor.hashCode());
		result = prime * result + idLibro;
		result = prime * result + idTema;
		result = prime * result + paginas;
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (autor == null) {
			if (other.autor != null)
				return false;
		} else if (!autor.equals(other.autor))
			return false;
		if (idLibro != other.idLibro)
			return false;
		if (idTema != other.idTema)
			return false;
		if (paginas != other.paginas)
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Libro [idLibro=" + idLibro + ", paginas=" + paginas + ", idTema=" + idTema + ", titulo=" + titulo
				+ ", autor=" + autor + ", precio=" + precio + "]";
	}
	
	public boolean repetido(HashSet<Libro> hash, Libro l) {
		Boolean b = false;
		return b;
	}

}
