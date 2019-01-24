<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.sql.Connection, modelo.Conexiones, modelo.TemaDAO,
	java.util.Iterator, java.util.ArrayList, beans.Tema, beans.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Selecciona tema</title>
</head>
<body>
	<div style="height: 20px">
		<!--  Comenzamos comprobando si hay un atributo de sesión o aplicación "cliente"
		Si no se ha iniciado sesión, esta página redirigirá al login. 
		En caso de que sí, se informa del nombr del usuario y se da la posibilidad de
		cerrar sesión -->
		<%
			if (application.getAttribute("cliente") != null) {
				Cliente cliente_sesion = (Cliente) application.getAttribute("cliente");
		%>
		<p>
			Usuario recordado
			<%=cliente_sesion.getUsuario()%>
			<a href="Procesar?opcion=cerrarsesion"> Cerrar sesión</a>
		</p>
		<%
			} else if (session.getAttribute("cliente") != null){
				Cliente cliente_peticion = (Cliente) session.getAttribute("cliente");
		%>
		<p>
			Sesión iniciada por
			<%=cliente_peticion.getUsuario()%>
			<a href="Procesar?opcion=cerrarsesion"> Cerrar sesión</a>
			
		</p>
		<%
			} else {
				response.sendRedirect("./index.jsp");
			}
		%>

	</div>
	
	<!-- Habilitamos una cadena para recoger el atributo de petición
	relativo a los mensajes exitosos de compra. Cuando se inserta una venta
	el usuario es dirigido al selector de temas informándole de que la compra se
	ha procesado -->

	<div style="height: 20px">
		<%
			if (request.getAttribute("mensajecompra") != null) {
				String mensajecompra = (String) request.getAttribute("mensajecompra");
		%>
		<p><%=mensajecompra%></p>
		<%
			}
		%>

	</div>


	<!--  Este formulario carga dinámicamente los nombre de los temas a partir de la 
	información de la base de datos. Cuando el usuario selecciona un tema le dirigimos
	a la página listado_carrito -->
	
	<h1>Seleccione tema</h1>

	<form action="listado_carrito.jsp" method="post">

		<select name="tema">

			<option value="todos">Todos</option>
			<%
				Connection con = Conexiones.establecerConexion();
				TemaDAO temaDAO = new TemaDAO(con);
				ArrayList<Tema> lista_temas = (ArrayList<Tema>) temaDAO.findAll();
				Iterator<Tema> it = lista_temas.iterator();
				while (it.hasNext()) {
					Tema tema = it.next();
					System.out.println(tema.toString());
			%>
			<option value=<%=tema.getTema()%>><%=tema.getTema()%></option>
			<%
				}
			%>


		</select> 
		<input type="submit" value="Ver Libros">

	</form>

</body>
</html>