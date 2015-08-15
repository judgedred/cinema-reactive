<%@ page import="com.domain.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Reservation" %>
<%@ page import="com.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ReservationList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>
    <p>Список броней</p>
    <form action="ReservationList" method="Get" id="reservation-list">
        <p><label for="user-select">Пользователь </label><select name="user-select" id="user-select">
            <option selected disabled>Выберите пользователя</option>
            <%
                List<User> userLst = (List<User>)session.getAttribute("userList");
                for(User u: userLst)
                {
            %>
            <option value=<%=u.getUserId()%>><%=u%></option>
            <%
                }
            %>
        </select></p>
        <p><input type="submit" value="Показать"></p>
    </form>
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
</body>
</html>
