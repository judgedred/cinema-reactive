<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Film</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить фильм</p>
    <form action="DeleteFilm" method="Get">
        <p><select name="film-select">
            <option selected disabled>Выберите фильм</option>
                <%
				List<Film> filmLs = (List<Film>)session.getAttribute("filmList");
				for(Film f: filmLs)
				{
			%>
            <option value=<%=f.getFilmId()%>><%=f%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
