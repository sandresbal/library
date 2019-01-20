package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Libro;
import modelo.Conexiones;
import modelo.LibroDAO;

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
		int isbn_seleccionado = Integer.parseInt(request.getParameter("isbn"));
		Connection cn = Conexiones.establecerConexion();
		LibroDAO libDAO = new LibroDAO(cn);
		switch (opcion) {
		case "comprar":
			System.out.println("Procesar: estamos en opción comprar");
			ArrayList<Libro> carrito_compra;
			//Set<Libro> carrito_compra;
			Libro lib = libDAO.findById(isbn_seleccionado);
			Conexiones.finalizarConexion(cn);
			if (ses.getAttribute("carrito") == null) {
				carrito_compra = new ArrayList();
				System.out.println("Creamos carrito, estaba vacío");
				carrito_compra.add(lib);
			} else {
				carrito_compra =(ArrayList) ses.getAttribute("carrito");
				carrito_compra.add(lib);
				
			}
			ses.setAttribute("carrito", carrito_compra);
			RequestDispatcher rd = request.getRequestDispatcher("listado_carrito2.jsp");
			rd.forward(request, response);
			break;
		case "eliminar":
			System.out.println("Procesar: estamos en opción eliminar");
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
