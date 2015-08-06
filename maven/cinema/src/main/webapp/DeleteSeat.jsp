<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Seat</title>
    <link rel="stylesheet" href="/cinema/resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить место</p>
    <form action="DeleteSeat" method="Get">
        <p><select name="seat-select">
            <option selected disabled>Выберите место</option>
                <%
				List<Seat> seatLs = (List<Seat>)session.getAttribute("seatList");
				for(Seat s: seatLs)
				{
			%>
            <option value=<%=s.getSeatId()%>><%=s%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
