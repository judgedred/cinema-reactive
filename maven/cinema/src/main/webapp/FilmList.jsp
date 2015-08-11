<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>FilmList</title>
    <link rel="stylesheet" href="/cinema/resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>FilmList</p>

    <%
        List<Film> filmLs = (List<Film>)session.getAttribute("filmList");
        for(Film f : filmLs)
        {
    %>
    <p><%=f%></p>
    <%
        }
    %>
</div>
</body>
</html>
