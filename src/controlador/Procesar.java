package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cliente;
import beans.Libro;
import beans.Venta;
import modelo.Conexiones;
import modelo.LibroDAO;
import modelo.VentaDAO;

/**
 * Servlet implementation class Comprar
 */
@WebServlet("/Procesar")
public class Procesar extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Procesar() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession ses = request.getSession();
		String tema_seleccionado = (String) ses.getAttribute("tema");
		System.out.println("Procesar: el tema seleccionado es " + tema_seleccionado);
		String opcion = request.getParameter("opcion");
		System.out.println("La opcion elegida es" + opcion);
		int isbn_seleccionado;
		Connection cn = Conexiones.establecerConexion();
		LibroDAO libDAO = new LibroDAO(cn);
		RequestDispatcher rd;
		
		switch (opcion) {
		case "comprar":
			
			System.out.println("Procesar: estamos en opción comprar");
			//ArrayList<Libro> carrito_compra;
			HashSet<Libro> carrito_compra;
			HashSet<Integer> isbn_anadidos;
			isbn_seleccionado = Integer.parseInt(request.getParameter("isbn"));
			Libro lib = libDAO.findById(isbn_seleccionado);
			
			if (ses.getAttribute("carrito") == null) {

				ses.removeAttribute("anadidos");
				carrito_compra = new HashSet<Libro>();
				carrito_compra.add(lib);
				isbn_anadidos = new HashSet<Integer>();
				isbn_anadidos.add(isbn_seleccionado);
				System.out.println("Creamos carrito y lista de isbns, estaba vacío");
			} else {

				carrito_compra = (HashSet<Libro>) ses.getAttribute("carrito");
				carrito_compra.add(lib);
				isbn_anadidos = (HashSet<Integer>) ses.getAttribute("anadidos");
				int elementos = 1;
				boolean repe = false;
				for (int isbn: isbn_anadidos) {
					if (isbn_seleccionado == isbn) {
						request.setAttribute("repetido", "No puedes comprar dos unidades del mismo producto");
						repe = true;
						System.out.println("el valor de bool en for es " + repe);
					}
				}
				if (repe == false) {
					System.out.println("Debería añadirse el isbn" + isbn_seleccionado);
					isbn_anadidos.add(isbn_seleccionado);
					System.out.println("el valor de bool deberia ser false y es " + repe);
				}
				System.out.println("Después de la comprobación bool es " + repe);

				System.out.println("estos son los índices ocupados: ");
				
				for (int isbn: isbn_anadidos) {
					System.out.print("isbn: " + isbn);
					System.out.println(" con indice " + elementos);
					++elementos;
				}
			}
			ses.setAttribute("carrito", carrito_compra);
			ses.setAttribute("anadidos", isbn_anadidos);
			Conexiones.finalizarConexion(cn);
			rd = request.getRequestDispatcher("listado_carrito2.jsp");
			rd.forward(request, response);
			break;
			
		case "eliminar":
			
			isbn_seleccionado = Integer.parseInt(request.getParameter("isbn"));
			System.out.println("Procesar: opción eliminar el isbn " + isbn_seleccionado);
			carrito_compra = (HashSet<Libro>) ses.getAttribute("carrito");
			isbn_anadidos = (HashSet<Integer>) ses.getAttribute("anadidos");
			System.out.println("el tamaño del carrito antes de eliminación: " + carrito_compra.size());
			System.out.println("el tamaño del set de isbns antes de eliminación: " + isbn_anadidos.size());

			Libro libro_a_eliminar = libDAO.findById(isbn_seleccionado);
			if (carrito_compra.contains(libro_a_eliminar)){
				carrito_compra.remove(libro_a_eliminar);
				isbn_anadidos.remove(isbn_seleccionado);
			}
			
			System.out.println("el tamaño del carrito: " + carrito_compra.size());

			System.out.println("Ahora quedan estos isbn: ");
			for (int isbn: isbn_anadidos) {
				System.out.println(isbn);
			}
			
			
			ses.setAttribute("anadidos", isbn_anadidos);
			Conexiones.finalizarConexion(cn);
			rd = request.getRequestDispatcher("listado_carrito2.jsp");
			rd.forward(request, response);
			break;
			
		case ("ejecutarcompra"):
			System.out.println("Procesar: opcion procesarcompra");
			carrito_compra = (HashSet<Libro>) ses.getAttribute("carrito");
			VentaDAO vDAO = new VentaDAO(cn);
			Iterator itventa = carrito_compra.iterator();
			while (itventa.hasNext()) {
				Venta v = new Venta();
				Libro libc = (Libro) itventa.next();
				v.setIdLibro(libc.getIdLibro());
				System.out.println("El id de libro es " + libc.getIdLibro());
				Cliente c = (Cliente) ses.getAttribute("cliente");
				System.out.println("El id de cliente es " + c.getIdCliente());
				v.setIdCliente(c.getIdCliente());
				vDAO.insert(v);
			}
			request.setAttribute("mensajecompra", "Compra efectuada con éxito. Selecciona un tema para empezar");

			ses.removeAttribute("carrito");
			ses.removeAttribute("anadidos");
			Conexiones.finalizarConexion(cn);
			rd = request.getRequestDispatcher("selectema.jsp");
			rd.forward(request, response);
			break;
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
