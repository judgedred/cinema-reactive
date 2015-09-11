<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Reservation</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить бронь</p>
    <form action="deleteReservation" method="Get">
        <p><select name="reservation-select">
            <option selected disabled>Выберите бронь</option>
                <%
				List<Reservation> reservationLs = (List<Reservation>)session.getAttribute("reservationList");
				for(Reservation r: reservationLs)
				{
			%>
            <option value=<%=r.getReservationId()%>><%=r%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
