<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Reservation" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>UserReservationList</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="top.jsp"/>
    <div class="content">

    <p>Список броней</p>

        <c:if test="${!empty filteredReservationList}">
            <c:forEach items="${filteredReservationList}" var="reservation">
                <p>${reservation}</p>
            </c:forEach>
        </c:if>

    </div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
