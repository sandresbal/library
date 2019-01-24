<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="beans.Cliente"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>

</head>
<body>
<% if (request.getParameter("usuario") != null){%>
<jsp:useBean id="nuevoCliente" class="beans.Cliente" scope="request" />
<jsp:setProperty name="nuevoCliente" property="*" />
<jsp:forward page="Login?option=registro" />
<% }%>

<form action="registro.jsp" method="post">

	<label for="user">Usuario: </label> <input type="text" name="usuario"><br>

	<label for="pass">Password: </label> <input type="text" name="password"><br>

	<label for="email">Email: </label> <input type="email" name="email"><br>

	<label for="tel">Teléfono: </label> <input type="number" name="telefono"><br>

	<input type="submit" value="Grabar"><br>

</form>
	<a	href="index.jsp">Volver Inicio</a>
	<% 	String mensa = (String)request.getAttribute("mensaje");
		if (mensa != null){%> 
		<h3><%= mensa %></h3>
	 <%} %>
</body>
</html>