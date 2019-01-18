package beans;

import java.io.Serializable;
import java.util.Date;

public class Venta implements Serializable {
	
	private int idVenta, idCliente, idLibro;
	private Date fecha;
	
	public Venta () {
		super();
	}
	
	public Venta (int idV, int idC, int idL, Date f) {
		super();
		this.idVenta = idV;
		this.idCliente = idC;
		this.idLibro = idL;
		this.fecha = f;
	}

	public int getIdVenta() {
		return idVenta;
	}

	public void setIdVenta(int idVenta) {
		this.idVenta = idVenta;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdLibro() {
		return idLibro;
	}

	public void setIdLibro(int idLibro) {
		this.idLibro = idLibro;
	}

	public java.sql.Date getFecha() {
		return (java.sql.Date) fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + idCliente;
		result = prime * result + idLibro;
		result = prime * result + idVenta;
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
		Venta other = (Venta) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idCliente != other.idCliente)
			return false;
		if (idLibro != other.idLibro)
			return false;
		if (idVenta != other.idVenta)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Venta [idVenta=" + idVenta + ", idCliente=" + idCliente + ", idLibro=" + idLibro + ", fecha=" + fecha
				+ "]";
	}
	
	

}
