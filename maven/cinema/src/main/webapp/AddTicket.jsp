<%@ page import="com.domain.Filmshow" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Seat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>AddTicket</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#filmshow-select").change(function(){
                $.ajax({
                    url: "../ProcessServlet/SeatsFilter?filmshow-select=" + $("#filmshow-select").val(), success: function(data) {
                        $("#seat-div").load(document.URL + " #seat-div");
                    }
                })
            });
        });
    </script>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

<p>Выпустить билеты</p>
<form action="AddTicket" method="Get" id="ticket-add">
  <p><label for="filmshow-select">Сеанс </label><select name="filmshow-select" id="filmshow-select">
    <option selected disabled>Выберите сеанс</option>
      <%
				List<Filmshow> filmshowLst = (List<Filmshow>)session.getAttribute("filmshowList");

				for(Filmshow f: filmshowLst)
				{
			%>
    <option value=<%=f.getFilmshowId()%>><%=f%></option>
      <%
				}
			%>
  </select></p>
    <p><label for="ticket-add-price">Введите цену </label><input type="text" name="ticket-add-price" id="ticket-add-price"></p>
    <div id="seat-div">
    <p><label for="seat-select">Место </label><select name="seat-select" id="seat-select">
        <option selected disabled>Выберите место</option>
            <%
				List<Seat> seatLst = (List<Seat>)session.getAttribute("filteredSeatList");
                if(seatLst != null)
                {
				for(Seat s: seatLst)
				{
			%>
        <option value=<%=s.getSeatId()%>><%=s%></option>
            <%
				}
                }
			%>
    </select></p>
    </div>
  <p><input type="submit" value="Выпустить билеты"></p>
</form>

    </div>
</body>
</html>
