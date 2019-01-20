<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
</head>
<body>
	<form action="Login?option=login" method="post">

		<label for="user">Usuario: </label> <input type="text" name="usuario"><br>

		<label for="pass">Password: </label> <input type="text" name="password"><br>

		Recordar usuario:<input type="checkbox" name="recordar" value="si"></input>

		<br> <input type="submit" value="Entrar"><br> 
		<a href="registro.jsp">Regístrese</a>

	</form>

	<% 	String mensa = (String)request.getAttribute("mensaje");
		if (mensa != null){%>
	<h3><%= mensa %></h3>
	<%} %>

</body>
</html>