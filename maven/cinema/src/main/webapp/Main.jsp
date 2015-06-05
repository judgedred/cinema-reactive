<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Main</title>
</head>
<body>
	<table border=1 align=center>
		<tr>
		<td>Новости</td>
		<td>Сеансы</td>
		<td>5D</td>
		<td>Фильмы</td>
		<td>О кинотеатре</td>
		<td><a href="Register.jsp">Зарегистрироваться</a></td>
		</tr>
	</table>
	<p><h2>Сегодня в кино</h2></p>
		
	<%	
		List<Filmshow> ls = (List<Filmshow>)session.getAttribute("filmshowList");
	 	for(Iterator<Filmshow> i = ls.iterator(); i.hasNext(); )
		{
			Filmshow f = i.next(); 
	%>
			<p><%=f%></p>
	<%	
		} 
	%>
	<p><a href="AddFilmshow">Добавить сеанс</a>
	<p><a href="DeleteFilmshow">Удалить сеанс</a>
		
</body>
</html>
