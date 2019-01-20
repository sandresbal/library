package controlador;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
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
		RequestDispatcher rd;
		switch (opcion) {
		case "comprar":
			System.out.println("Procesar: estamos en opción comprar");
			//ArrayList<Libro> carrito_compra;
			HashSet<Libro> carrito_compra;
			HashSet<Integer> isbn_anadidos;
			Libro lib = libDAO.findById(isbn_seleccionado);
			Conexiones.finalizarConexion(cn);
			if (ses.getAttribute("carrito") == null) {
				//carrito_compra = new ArrayList();
				carrito_compra = new HashSet<Libro>();
				System.out.println("Creamos carrito, estaba vacío");
				carrito_compra.add(lib);
				isbn_anadidos = new HashSet<Integer>();
				isbn_anadidos.add(isbn_seleccionado);
			} else {
				//carrito_compra =(ArrayList) ses.getAttribute("carrito");
				carrito_compra = (HashSet<Libro>) ses.getAttribute("carrito");
				carrito_compra.add(lib);
				isbn_anadidos = (HashSet<Integer>) ses.getAttribute("anadidos");
				int elementos = 1;
				for (int isbn: isbn_anadidos) {
					if (isbn_seleccionado == isbn) {
						request.setAttribute("repetido", "No puedes comprar dos unidades del mismo producto");
					}
				}
				if (request.getAttribute("repetido") == null) {
					isbn_anadidos.add(isbn_seleccionado);
				}
				System.out.println("estos son los índices ocupados: ");
				for (int isbn: isbn_anadidos) {
					System.out.print("isbn: " + isbn);
					System.out.println(" con indice" + elementos);
					++elementos;
				}
			}
			ses.setAttribute("carrito", carrito_compra);
			ses.setAttribute("anadidos", isbn_anadidos);
			rd = request.getRequestDispatcher("listado_carrito2.jsp");
			rd.forward(request, response);
			break;
		case "eliminar":
			System.out.println("Procesar: opción eliminar el isbn " + isbn_seleccionado);
			carrito_compra = (HashSet<Libro>) ses.getAttribute("carrito");
			isbn_anadidos = (HashSet<Integer>) ses.getAttribute("anadidos");
			Iterator<Libro> it = carrito_compra.iterator();
			System.out.println("el tamaño del carrito antes de eliminación: " + carrito_compra.size());
			if (it.hasNext()) {
				Libro libro_en_carro = it.next();
				if (isbn_seleccionado == libro_en_carro.getIdLibro()) {
					System.out.println("Eliminando el " + libro_en_carro.toString());
					System.out.println("Su isbn es " + libro_en_carro.getIdLibro());
					carrito_compra.remove(libro_en_carro);
				}
			}
			System.out.println("el tamaño del carrito: " + carrito_compra.size());

			System.out.println("Ahora quedan estos isbn: ");

			for (int isbn: isbn_anadidos) {
				System.out.println(isbn);
			}
			Iterator<Integer> itisbn = isbn_anadidos.iterator();
			if (itisbn.hasNext()) {
				if (isbn_seleccionado == itisbn.next()) {
					carrito_compra.remove(isbn_anadidos);
					System.out.println("Eliminado el " + isbn_anadidos.toString());
				}
			}
			ses.setAttribute("anadidos", isbn_anadidos);
			rd = request.getRequestDispatcher("listado_carrito2.jsp");
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
