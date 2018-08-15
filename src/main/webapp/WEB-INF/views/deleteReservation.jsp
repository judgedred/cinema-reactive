<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Delete Reservation</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить бронь</p>

    <c:if test="${!empty reservationList}">
        <form:form action="deleteReservation" modelAttribute="reservation">
            <p>
                <form:label path="reservationId">Бронь</form:label>
                <form:select path="reservationId">
                    <form:option value="0" label="Выберите бронь"/>
                    <form:options items="${reservationList}" itemValue="reservationId"/>
                </form:select>
            </p>
            <p><input type="submit" value="Удалить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
