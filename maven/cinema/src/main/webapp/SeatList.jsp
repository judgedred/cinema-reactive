<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>SeatList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>SeatList</p>

    <%
        List<Seat> seatLs = (List<Seat>)session.getAttribute("seatList");
        for(Seat s : seatLs)
        {
    %>
    <p><%=s%></p>
    <%
        }
    %>
</div>
</body>
</html>