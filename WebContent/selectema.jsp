<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="java.sql.Connection, modelo.Conexiones, modelo.TemaDAO,
	java.util.Iterator, java.util.ArrayList, beans.Tema"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Selecciona tema</title>
</head>
<body>
	<h1>Seleccione tema</h1>

	<form action="listado_carrito.jsp" method="post">

		<select name="tema">
		
			<% Connection con = Conexiones.establecerConexion();
			TemaDAO temaDAO = new TemaDAO(con);
			ArrayList<Tema> lista_temas = (ArrayList<Tema>)temaDAO.findAll();
			Iterator<Tema> it = lista_temas.iterator();
			while(it.hasNext()){
			    Tema tema =it.next();
			    System.out.println(tema.toString());%>
			    <option><%= tema.getTema()%></option>
			<% }
	
			%>

		</select> 
		<input type="submit" value="Ver Libros">

	</form>
	<% String seleccionado = request.getParameter("tema");
	request.setAttribute("String", seleccionado); %>

</body>
</html>