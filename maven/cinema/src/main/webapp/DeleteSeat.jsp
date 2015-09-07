<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Seat</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#seat-delete").submit(function (event) {
                $.ajax({
                    url: "../ProcessServlet/SeatCheck?seat-select=" + $("#seat-select").val(),
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

    <p>Удалить место</p>
    <form action="DeleteSeat" method="Get" id="seat-delete">
        <p><select name="seat-select" id="seat-select">
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
