<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="modelo.LibroDAO, modelo.TemaDAO, java.sql.Connection,
    modelo.Conexiones, java.util.ArrayList, beans.Libro, beans.Cliente, java.util.Iterator,
    java.util.Set, java.util.HashSet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado</title>
</head>
<body>
	<div style="height: 20px">
	
		<!-- Al igual que en selectema.jsp comprobamos que un usuario se ha logueado
		detectando que hay un objeto cliente o bien como atributo de sesión o 
		bien como uno de aplicación -->
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
	<!--  La primera tabla se crea a partir de la información de la tabla libros, 
	tomando en consideración como atributo el tema pasado en la petición del
	selector en selectema.jsp.
	Creamos un objeto Connection, otro LibroDAO pasándole el primero como argumento
	y después usamos un método u otro de libroDAO sobre dicho objeto en función de
	qué haya seleccionado el usuario: si ha seleccionado ver libros de todos
	los temas, volcamos en el ArrayList de libros "resultado" los objetos de la clase
	Libro resultantes de ejecutar el método findAll sobre librodao; si el usuario
	ha seleccionado ver libros de un tema, usamos el método findByTheme.
	-->
	<div style="margin-top: 20px; margin-bottom: 20px">
		<table border="1">
			<tr>
				<th></th>
				<th>Título</th>
				<th>Autor</th>
				<th>Precio</th>
			</tr>
			<%
			if (request.getParameter("tema") != null) {
				session.setAttribute("tema", request.getParameter("tema"));
				String seleccionado = (String) request.getAttribute("tema");
				System.out.println("Listado: el tema seleccionado es " + seleccionado);
				Connection c = Conexiones.establecerConexion();
				LibroDAO librodao = new LibroDAO(c);
				ArrayList<Libro> resultado;
				if (seleccionado.equals("todos")) {
					resultado = (ArrayList<Libro>) librodao.findAll();
				} else {
					resultado = (ArrayList<Libro>) librodao.findByTheme(seleccionado);
				}
				Iterator<Libro> it = resultado.iterator();
				while (it.hasNext()) {
					Libro l = it.next();%>
			<tr>
				<td><a
					href="Procesar?opcion=comprar&isbn=<%=l.getIdLibro()%>&tema=<%=seleccionado%>&user">Comprar</a>
					<%
					System.out.println("el id del libro es " + l.getIdLibro());
					%>
				<td><%=l.getTitulo()%></td>
				<td><%=l.getAutor()%></td>
				<td><%=l.getPrecio()%></td>
			<tr>
				<% Conexiones.finalizarConexion(c);

			}

		}%>
			
		</table>
		<a href="selectema.jsp">Otro tema</a>
	</div>

	<!-- Creo espacio para un String que me informe de que el libro que intento
	añadir al carrito ya está añadido.
	 -->
	
	<%	String mensaje_repe = (String)request.getAttribute("repetido");
		if (request.getAttribute("repetido") != null){%>
	<%= mensaje_repe%>
	
	<!-- Para almacenar los objetos de la clase Libro del carrito he elegido
	una estructura de datos HashSet que no admite objetos repetidos. Además,
	he creado otro hashset de enteros para almacenar los isbn de los libros del
	carrito y servirme de él a la hora de detectar libros repetidos con más facilidad.
	Sólo en caso de que haya contenido en ambos hashset se pintará una tabla usando
	un iterador sobre el hashset del carrito.
	-->
	<% }
		
		HashSet<Libro> carrito_at = (HashSet<Libro>)session.getAttribute("carrito");
		HashSet<Integer> listado_isbn = (HashSet<Integer>)session.getAttribute("anadidos");
		String seleccionado = (String) session.getAttribute("tema");
		if (carrito_at != null && carrito_at.size() > 0){
			System.out.println ("el tamaño del carro al cargar de nuevo listado.jsp es " + carrito_at.size());
			System.out.println ("el tamaño de la lista al cargar de nuevo listado.jsp de isbns es " + listado_isbn.size());
			Iterator<Libro> iter = carrito_at.iterator();%>
	<table border="1">
		<tr>
			<th></th>
			<th>Título</th>
			<th>Autor</th>
			<th>Precio</th>
		</tr>
		<% while (iter.hasNext()){
				Libro libro_carrito = iter.next();%>
		<tr>
			<td><a
				href="Procesar?opcion=eliminar&isbn=<%=libro_carrito.getIdLibro()%>&tema=<%=seleccionado%>">Eliminar</a>
			<td><%=libro_carrito.getTitulo()%></td>
			<td><%=libro_carrito.getAutor()%></td>
			<td><%=libro_carrito.getPrecio()%></td>
		<tr>
			<% }%>
		
	</table>
	<% 
		}
		if (session.getAttribute("carrito")!= null){%>
	<a href="Procesar?opcion=ejecutarcompra">Ejecutar compra</a>
	<% }
	%>
</body>
</html>