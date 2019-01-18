<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registro</title>

<form action="Registro.jsp" method="post">

	<label for="user">Usuario: </label> <input type="text" name="user"><br>

	<label for="pass">Password: </label> <input type="text" name="pass"><br>

	<label for="email">Email: </label> <input type="text" name="email"><br>

	<label for="tel">Teléfono: </label> <input type="text" name="tel"><br>

	<input type="submit" value="Grabar"><br>

</form>

<!-- jsp con Javabean y forward para mandar a login, podía
sacar mensaje de usuario creado con éxito-->  

</head>
<body>

</body>
</html>