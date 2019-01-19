package controlador;

import java.io.IOException;
import java.io.PrintWriter;
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
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		 Connection conn = Conexiones.establecerConexion(); 
		 ClienteDAO cdao = new ClienteDAO(conn); 
		 String user = request.getParameter("user"); 
		 String pass = request.getParameter("pass"); 
		 Cliente c = new Cliente(); 
		 c.setUsuario(user);
		 c.setPassword(pass); 
		 Boolean b = cdao.checkUser(c); 
		 if (b) {
			 System.out.println("es usuario válido"); 
			 RequestDispatcher rd =
				request.getRequestDispatcher("selectema.jsp"); rd.forward(request, response); }
		 else { 
			 System.out.println("no es usuario válido"); 
			 PrintWriter out = response.getWriter();
			 out.print("<script>alert('usuario o password incorrecto')</script>"); 
		 }
		 Conexiones.finalizarConexion(conn);
		 
	}

}
