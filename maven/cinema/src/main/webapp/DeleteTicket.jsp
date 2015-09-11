<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Ticket</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#ticket-delete").submit(function (event) {
                $.ajax({
                    url: "../ProcessServlet/ticketCheck?ticket-select=" + $("#ticket-select").val(),
                    async: false,
                    success: function (data) {
                        if (data != "") {
                            alert(data);
                            event.preventDefault();
                        }
                    }
                })
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить билет</p>
    <form action="deleteTicket" method="Get" id="ticket-delete">
        <p><select name="ticket-select" id="ticket-select">
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
