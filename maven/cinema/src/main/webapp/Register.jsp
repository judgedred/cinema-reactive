<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
	<title>Register</title>
</head>
<body>
	<form action="Register" method="Get">
		<p>Введите логин<input type="text" name="login"></p>
		<p>Введите пароль<input type="text" name="password"></p>
		<p>Введите email<input type="text" name="email"></p>
		<p><input type="submit" value="Зарегистрироваться"></p>
	</form>
	<p><a href="UserList">Список пользователей</a></p>
</body>
</html>