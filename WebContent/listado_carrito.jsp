<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="modelo.LibroDAO, modelo.TemaDAO, java.sql.Connection,
    modelo.Conexiones, java.util.ArrayList, beans.Libro, java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Listado</title>
</head>
<body>
<% %>
<table border = "1">
<tr><th></th><th>Título</th><th>Autor</th><th>Precio</th></tr>
<% 	
String seleccionado = request.getParameter("tema");
	System.out.println("el tema seleccionado es " + seleccionado);
	
	Connection c = Conexiones.establecerConexion();
	LibroDAO librodao = new LibroDAO (c);
	ArrayList<Libro> resultado = (ArrayList<Libro>)librodao.findByTheme(seleccionado);
	Iterator<Libro> it = resultado.iterator();
	while (it.hasNext()){
		Libro l = it.next();%>
		<tr>
		<td> <a href="listado_carrito.jsp?idLibro=<%=l.getIdLibro()%>">Comprar</a>
		<td><%= l.getTitulo()%></td>
		<td><%= l.getAutor()%></td>
		<td><%= l.getPrecio()%></td><tr>
	c.finalizarConexion();
	<%}

%>
</table>
<%if(request.getParameter("idLibro") !=null) {
	Connection cn = Conexiones.establecerConexion();
	LibroDAO libDAO = new LibroDAO (cn);
	int id = Integer.parseInt(request.getParameter("idLibro"));
	Libro lib = libDAO.findById(id);%>
	<table>
	<tr><th></th><th>Título</th><th>Autor</th><th>Precio</th></tr>
			<tr>
		<td> <a href="listado_carrito.jsp?eliminar=si">Eliminar</a>
		<td><%= lib.getTitulo()%></td>
		<td><%= lib.getAutor()%></td>
		<td><%= lib.getPrecio()%></td><tr>
	</table>
	
	<%} 
	%>
</body>
</html>