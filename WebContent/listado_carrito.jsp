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
<% 	//session.setAttribute("tema", request.getParameter("tema"));
	//String seleccionado = (String)session.getAttribute("tema");
	String seleccionado = request.getParameter("tema");
	System.out.println("el tema seleccionado es "+ seleccionado);
	Connection c = Conexiones.establecerConexion();
	LibroDAO librodao = new LibroDAO (c);
	ArrayList<Libro> resultado;
	if (seleccionado.equals("todos")){
		resultado = (ArrayList<Libro>)librodao.findAll();
		System.out.println("el tamaño del arraylist con todos es de " +resultado.size());
	} else {
		resultado = (ArrayList<Libro>)librodao.findByTheme(seleccionado);
		System.out.println("el tamaño del arraylist con los libros de un tema es de " +resultado.size());
	}
	Iterator<Libro> it = resultado.iterator();
	while (it.hasNext()){
		Libro l = it.next();%>
		<tr>
		<td> <a href="listado_carrito.jsp?isbn=<%=l.getIdLibro()%>&tema=<%=seleccionado%>">Comprar</a>
		<%System.out.println(l.getIdLibro()); %>
		<td><%= l.getTitulo()%></td>
		<td><%= l.getAutor()%></td>
		<td><%= l.getPrecio()%></td><tr>
	<%
	Conexiones.finalizarConexion(c);}

%>
</table>
<%if(request.getParameter("isbn") !=null) {
	int id = Integer.parseInt(request.getParameter("isbn"));
	Connection cn = Conexiones.establecerConexion();
	LibroDAO libDAO = new LibroDAO (cn);
	Libro lib = libDAO.findById(id);%>
	<div style="height:200px"></div>
	<table border="1">
	<tr><th></th><th>Título</th><th>Autor</th><th>Precio</th></tr>
			<tr>
		<td> <a href="listado_carrito.jsp?eliminar=si">Eliminar</a>
		<td><%= lib.getTitulo()%></td>
		<td><%= lib.getAutor()%></td>
		<td><%= lib.getPrecio()%></td><tr>
	</table>
	
	<%} else {
		System.out.println("viene nulo el parámetro título");
	}
	%>
</body>
</html>