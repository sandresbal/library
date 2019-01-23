package controlador;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.Cliente;
import modelo.ClienteDAO;
import modelo.Conexiones;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = Conexiones.establecerConexion();
		ClienteDAO cdao = new ClienteDAO(conn);
		HttpSession sesion = request.getSession();
		Cliente c = null;
		Boolean b;

		String user = request.getParameter("usuario");
		String pass = request.getParameter("password");

		System.out.print("procesando el login");
		switch (request.getParameter("option")) {

		case ("login"):

			c = new Cliente();
			c.setUsuario(user);
			c.setPassword(pass);
			b = cdao.checkUser(c);
			System.out.println("¿Es usuario válido?" + b);
			RequestDispatcher rd;
			if (!b) {
				System.out.println("no es usuario válido");
				request.setAttribute("mensaje", "usuario o password incorrectos");
				rd = request.getRequestDispatcher("index.jsp");
				rd.forward(request, response);
			} else {
				System.out.println("es usuario válido");
				Cliente cdef = cdao.findByName(user);
				sesion.setAttribute("cliente", cdef);

				System.out.println("el user es " + c.getIdCliente());

				rd = request.getRequestDispatcher("selectema.jsp");
				rd.forward(request, response);
			}
			break;
		case ("registro"):
			c = (Cliente) request.getAttribute("nuevoCliente");
			System.out.println(c.toString());
			b = cdao.checkUser(c);
			if (!b) {
				request.setAttribute("mensaje", "Ya hay un usuario con ese nombre");
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				int i = cdao.insert(c);
				System.out.println("var i : " + i);

				if (i == 1) {

					request.setAttribute("mensaje", "Cliente dado de alta. Procede al login");
					request.getRequestDispatcher("index.jsp").forward(request, response);

				} else {
					request.setAttribute("mensaje", "Problemas al insertar");
					request.getRequestDispatcher("index.jsp").forward(request, response);

				}
			}
			break;
		}

		Conexiones.finalizarConexion(conn);

	}
}
