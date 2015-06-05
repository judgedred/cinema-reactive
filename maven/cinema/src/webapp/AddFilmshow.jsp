<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="domain.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
	<title>Add Filmshow</title>
</head>
<body>
	<p>Добавление сеанса</p>
	<form action="AddFilmshow" method="Get">
		<p><select name="filmSelect">
			<option selected disabled>Выберите фильм</option>
			<% 
				List<Film> filmLst = (List<Film>)session.getAttribute("filmList");
				for(Iterator<Film> i = filmLst.iterator(); i.hasNext(); )
				{
					Film f = i.next();
			%>
				<option value=<%=f.getFilmId()%>><%=f.getFilmName()%></option>
			<%
				}
			%>
		</p></select>
		<p><select name="hallSelect">
			<option selected disabled>Выберите зал</option>
			<% 
				List<Hall> hallLst = (List<Hall>)session.getAttribute("hallList");
				for(Iterator<Hall> i = hallLst.iterator(); i.hasNext(); )
				{
					Hall h = i.next();
			%>
				<option value=<%=h.getHallId()%>><%=h.getHallName()%></option>
			<%
				}
			%>
		</p></select>
		<p>Выберите дату<input type="datetime" name="dateTime"></p>
		<p><input type="submit" value="Добавить"></p>
	</form>
		
</body>
</html>