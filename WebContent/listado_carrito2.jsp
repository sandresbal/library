<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="modelo.LibroDAO, modelo.TemaDAO, java.sql.Connection,
    modelo.Conexiones, java.util.ArrayList, beans.Libro, java.util.Iterator,
    java.util.Set, java.util.HashSet"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado</title>
</head>
<body>
	<%
		
	%>
	<div style="margin-top:20px; margin-bottom:20px">
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
				String seleccionado = (String) session.getAttribute("tema");
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
					href="Procesar?opcion=comprar&isbn=<%=l.getIdLibro()%>&tema=<%=seleccionado%>">Comprar</a>
					<%
					System.out.println("el id del libro es " + l.getIdLibro());
					%>
					<td><%=l.getTitulo()%></td>
					<td><%=l.getAutor()%></td>
					<td><%=l.getPrecio()%></td>
					<tr>
				<% Conexiones.finalizarConexion(c);

			}
				if (session.getAttribute("carrito") != null){
				System.out.println("hay un carrito");
				}
		}%>
		
	</table>
			<a href="selectema.jsp">Otro tema</a>
			</div>
	
	<%	String mensaje_repe = (String)request.getAttribute("repetido");
		if (request.getAttribute("repetido") != null){%>
			<%= mensaje_repe%>
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
					<td><a href="Procesar?opcion=eliminar&isbn=<%=libro_carrito.getIdLibro()%>&tema=<%=seleccionado%>">Eliminar</a> 
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