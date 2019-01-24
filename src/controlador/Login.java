package controlador;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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

		HttpSession sesion = request.getSession();
		ServletContext sc = request.getServletContext();
		
		Connection conn = Conexiones.establecerConexion();
		ClienteDAO cdao = new ClienteDAO(conn);

		
		Cliente c = null;
		Boolean b;
		String[] rem;

		String user = request.getParameter("usuario");
		String pass = request.getParameter("password");

		System.out.print("procesando el login");
		
		switch (request.getParameter("option")) {

		/*
		 * En el caso de que la opción elegida sea login, creamos un objeto cliente
		 * Seteamos el nombre de usuario y el password a los parámetros usuario y 
		 * password cuyo value hemos pasado a dos strings anteriormente (user y pass)
		 * Guardamos en el array de String rem si el usuario ha marcado "recordar"
		 * en el checkbox.
		 * Después, ejecutamos sobre el cliente c la función checkUser creada en
		 * ClienteDAO, encargada de comprobar si el usuario existe en la base de 
		 * datos de clientes.
		 * Si no existe (devuelve un false) se lo indicamos en un mensaje
		 * Si existe, creamos un objeto cliente a partir de la función findByName
		 * que devuelve un objeto de ese tipo a partir del valor del campo nombre
		 * en la tabla usuarios.
		 * En caso de que "recordar" esté marcado, guardamos ese nuevo objeto cliente
		 * como atributo de aplicación. En caso de que no, se guardará como
		 * atributo de sesión.
		 */
		case ("login"):

			c = new Cliente();
			c.setUsuario(user);
			c.setPassword(pass);
			rem = request.getParameterValues("recordar");
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
				
				if (rem != null) {
					System.out.println("recordar está marcado");
					sc.setAttribute("cliente", cdef);
				} else {
					System.out.println("recordar está marcado");
					sesion.setAttribute("cliente", cdef);
				}

				System.out.println("el user es " + cdef.getIdCliente());

				rd = request.getRequestDispatcher("selectema.jsp");
				rd.forward(request, response);
			}
			break;
			
		/*
		 * En el caso de tomar la opción registro, creamos un nuevo objeto cliente
		 * a partir del objeto creado en la acción jsp.
		 * El metodo comprueba si ese usuario existe ya con ese nombre en la tabla
		 * de clientes. Si es así, se le informa de que ya hay un user con ese nombre 
		 * y se le redirecciona al login. Si no, damos de alta al cliente 
		 * insertándolo en la tabla de usuarios
		 */
			
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
