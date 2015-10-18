<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <c:if test="${!empty userList}">
        <form:form action="reservationList" modelAttribute="reservation">
            <p><form:label path="user">Пользователь</form:label>
                <form:select path="user">
                    <form:option value="0" label="Выберите пользователя"/>
                    <form:options items="${userList}" itemValue="userId"/>
                </form:select></p>
            <p><input type="submit" value="Показать"></p>
        </form:form>
    </c:if>

    <c:if test="${!empty filteredReservationList}">
        <c:forEach items="${filteredReservationList}" var="reservation">
            <p>${reservation}</p>
        </c:forEach>
    </c:if>

</div>
</body>
</html>
