<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>

<!--  Comenzamos comprobando si el usuario quiere ser recordado. Si es así,
es decir, si existe un atributo de aplicación cliente, redireccionamos al selector
de temas para hacer su compra -->

<% 

if (application.getAttribute("cliente") != null){
	System.out.println ("hay un atributo de app cliente, recordamos sesión y saltamos al login");
	response.sendRedirect("./selectema.jsp");
} %>


	<form action="Login?option=login" method="post">

		<label for="user">Usuario: </label> <input type="text" name="usuario"><br>

		<label for="pass">Password: </label> <input type="text" name="password"><br>

		Recordar usuario:<input type="checkbox" name="recordar" value="si"></input>

		<br> <input type="submit" value="Entrar"><br> 
		<a href="registro.jsp">Regístrese</a>

	</form>

<!-- Creamos un string para mostrar mensajes de error en el proceso del login -->

	<% 	String mensa = (String)request.getAttribute("mensaje");
		if (mensa != null){%>
	<h3><%= mensa %></h3>
	<%} %>

</body>
</html>