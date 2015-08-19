<%@ page import="com.domain.Filmshow" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Seat" %>
<%@ page import="com.domain.Ticket" %>
<%@ page import="com.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>ReserveTicket</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
    <%--<script type="text/javascript">
        $(document).ready(function() {
            $("#filmshow-select").change(function(){
                $.ajax({
                    url: "../ProcessServlet/TicketsFilter?filmshow-select=" + $("#filmshow-select").val(), success: function(data) {
                        $("#ticket-div").load(document.URL + " #ticket-div");
                    }
                })
            });
            $("#reservation-add").submit(function (event) {
                if($("#filmshow-select").val() == null)
                {
                    alert("Заполните поля");
                    event.preventDefault();
                }
                else
                {
                    alert("Билет забронирован");
                }
            });
        });
    </script>--%>
</head>
<body>
<div class="wrapper">

    <jsp:include page="top.jsp"/>

    <p>Забронировать билет</p>
    <form action="ReserveTicket" method="Get" id="reservation-add">
        <div id="ticket-div">
            <p><label for="ticket-select">Билет </label><select name="ticket-select" id="ticket-select">
                <option selected disabled>Выберите билет</option>
                <%
                    List<Ticket> ticketLst = (List<Ticket>)session.getAttribute("filteredTicketList");
                    if(ticketLst != null)
                    {
                        for(Ticket t: ticketLst)
                        {
                %>
                <option value=<%=t.getTicketId()%>><%=t%></option>
                <%
                        }
                    }
                %>
            </select></p>
        </div>
        <p><input type="submit" value="Зарезервировать билет"></p>
    </form>

</div>
</body>
</html>
