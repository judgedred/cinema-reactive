<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>FilmshowList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>FilmshowList</p>

    <%
        List<Filmshow> filmshowLs = (List<Filmshow>)session.getAttribute("filmshowList");
        for(Filmshow f : filmshowLs)
        {
    %>
    <p><%=f%></p>
    <%
        }
    %>
</div>
</body>
</html>