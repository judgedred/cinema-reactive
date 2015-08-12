<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>HallList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>HallList</p>

    <%
        List<Hall> hallLs = (List<Hall>)session.getAttribute("hallList");
        for(Hall h : hallLs)
        {
    %>
    <p><%=h%></p>
    <%
        }
    %>
</div>
</body>
</html>