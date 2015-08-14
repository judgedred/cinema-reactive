<%@ page import="com.domain.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Filmshow" %>
<%@ page import="com.domain.Reservation" %>
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
