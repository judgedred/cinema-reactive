<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" language="java" %>
<%@ page import="dao.*" %>
<%@ page import="domain.*" %>
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
		
	 	try												
		{												
	 													
	 		FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");		
	 		List<Filmshow> ls = filmshowDao.getFilmshowAll();					
	 		for(Iterator<Filmshow> i = ls.iterator(); i.hasNext(); )				
	 		{											
	 			Filmshow f = i.next();								
	 			out.println("<p>" + f + "</p>");						
	 		} 											
		}												
		catch(Exception e)										
		{												
		 	e.printStackTrace(); 									
		}
		out.close();
		
		
	%>												
		
		
	


</body>
</html>
