<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="dao.*" %>
<%@ page import="domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>Register</title>
</head>
<body>
	<form action="Register.jsp" method="Get">
		<p>Введите логин<input type="text" name="login"></p>
		<p>Введите пароль<input type="text" name="password"></p>
		<p>Введите email<input type="text" name="email"></p>
		<p><input type="submit" value="Зарегистрироваться"></p>
	</form>
	<p><a href="UserList.jsp">Список пользователей</a></p>
	<%
		try
		{
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			User user = new User();
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			if(login != null && !login.isEmpty() && password != null && !password.isEmpty() && email != null && !email.isEmpty())
			{
				user.setLogin(login);
				user.setPassword(password);
				user.setEmail(email);
				userDao.create(user);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	%>

</body>
</html>