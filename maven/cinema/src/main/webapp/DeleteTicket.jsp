<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Ticket</title>
    <link rel="stylesheet" href="/cinema/resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить билет</p>
    <form action="DeleteTicket" method="Get">
        <p><select name="ticket-select">
            <option selected disabled>Выберите билет</option>
                <%
				List<Ticket> ticketLs = (List<Ticket>)session.getAttribute("ticketList");
				for(Ticket t: ticketLs)
				{
			%>
            <option value=<%=t.getTicketId()%>><%=t%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
