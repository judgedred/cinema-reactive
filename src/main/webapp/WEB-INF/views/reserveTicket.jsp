<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>ReserveTicket</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
</head>
<body>
<div class="wrapper">

    <jsp:include page="top.jsp"/>
    <div class="content">
    <p>Забронировать билет</p>

            <form:form action="reserveTicket?filmshowId=${filmshow.filmshowId}" modelAttribute="reservation" method="post">
                <form:label path="ticket">Билет</form:label>
                <form:select path="ticket">
                    <form:option value="0" label="Выберите билет"/>
                    <c:if test="${!empty filteredTicketList}">
                    <form:options items="${filteredTicketList}" itemValue="ticketId" />
                    </c:if>
                </form:select>
                <p><input type="submit" value="Зарезервировать билет"></p>
            </form:form>

    </div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
