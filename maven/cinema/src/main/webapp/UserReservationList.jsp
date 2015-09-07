<%@ page import="java.util.List" %>
<%@ page import="com.domain.Reservation" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserReservationList</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="top.jsp"/>
    <div class="content">

    <p>Список броней</p>
    <%
        List<Reservation> ls = (List<Reservation>)session.getAttribute("filteredReservationList");
        if(ls != null)
        {
            for(Reservation r : ls)
            {
    %>
    <p><%=r%></p>
    <%
            }
        }
    %>

    </div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
