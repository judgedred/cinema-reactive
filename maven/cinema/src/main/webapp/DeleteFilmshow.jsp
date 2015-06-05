<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
	<title>Delete Filmshow</title>
</head>
<body>
	<p>Удалить сеанс</p>
	<form action="DeleteFilmshow" method="Get">
		<p><select name="filmshowSelect">
			<option selected disabled>Выберите сеанс</option>
			<%
				List<Filmshow> filmshowLst = (List<Filmshow>)session.getAttribute("filmshowList");
				for(Iterator<Filmshow> i = filmshowLst.iterator(); i.hasNext(); )
				{
					Filmshow f = i.next();
			%>
					<option value=<%=f.getFilmshowId()%>><%=f%></option>
			<%
				}
			%>
		</p></select>
		<p><input type="submit" value="Удалить"></p>
	</form>

</body>
</html>
