<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
            $("#filmshow").change(function(){
                $.ajax({
                    url: "seatsFilter/" + $("#filmshow").val(), success: function(data) {
                        $("#seat-div").load(document.URL + " #seat-div");
                    }
                })
            });
            $("#ticket").submit(function (event) {
                if ($("#filmshow").val() == null || $("#price").val() == "" || $("#seat").val() == null)
                {
                    alert("Заполните поля");
                    event.preventDefault();
                }
            });
        });
    </script>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

<p>Добавить билет</p>

    <c:if test="${!empty filmshowList || !empty filteredSeatList}">
        <form:form action="addTicket" modelAttribute="ticket">
            <p><form:label path="filmshow">Сеанс</form:label>
                <form:select path="filmshow">
                    <form:option value="0" label="Выберите сеанс"/>
                    <form:options items="${filmshowList}" itemValue="filmshowId"/>
                </form:select></p>
            <p><form:label path="price">Введите цену</form:label>
                <form:input path="price" type="text"/></p>
            <div id="seat-div">
            <form:label path="seat">Место</form:label>
            <form:select path="seat">
                <form:option value="0" label="Выберите место"/>
                <form:options items="${filteredSeatList}" itemValue="seatId"/>
            </form:select>
            </div>
            <p><input type="submit" value="Добавить"></p>
        </form:form>
    </c:if>

<%--<form action="addTicket" method="Get" id="ticket-add">
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
  <p><input type="submit" value="Добавить билет"></p>
</form>--%>

    </div>
</body>
</html>
