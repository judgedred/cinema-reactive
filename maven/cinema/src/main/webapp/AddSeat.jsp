<%@ page import="java.util.List" %>
<%@ page import="com.domain.Hall" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>AddSeat</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

    <p>Добавить место</p>
    <form action="AddSeat" method="Get" id="seat-add">
        <p><label for="hall-select">Зал </label><select name="hall-select" id="hall-select">
            <option selected disabled>Выберите зал</option>
            <%
                List<Hall> hallLst = (List<Hall>)session.getAttribute("hallList");
                for(Hall h: hallLst)
                {
            %>
            <option value=<%=h.getHallId()%>><%=h%></option>
            <%
                }
            %>
        </select></p>
        <p><label for="seat-add-row">Введите ряд </label><input type="text" name="seat-add-row" id="seat-add-row"></p>
        <p><label for="seat-add-number">Введите номер </label><input type="text" name="seat-add-number" id="seat-add-number"></p>
        <p><input type="submit" value="Добавить место"></p>
    </form>
</div>
</body>
</html>
