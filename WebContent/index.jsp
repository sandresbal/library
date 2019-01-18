<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<form action="Login" method="post">

	<label for="user">Usuario: </label> 
	<input type="text" name="user"><br>

	<label for="pass">Password: </label> 
	<input type="text" name="pass"><br>

	Recordar
	usuario:<input type="checkbox" name="recordar" value="si"></input>
	
	<br> <input type="submit" value="Entrar"><br>

	<a href="registro.jsp">Regístrese</a>

</form>
</head>
<body>

</body>
</html>