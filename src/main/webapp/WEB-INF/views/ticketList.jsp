<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="com.domain.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Filmshow" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TicketList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>
<p>TicketList</p>

    <c:if test="${!empty filmshowList}">
        <form:form action="ticketList" modelAttribute="ticket">
            <p><form:label path="filmshow">Сеанс</form:label>
                <form:select path="filmshow">
                    <form:option value="0" label="Выберите сеанс"/>
                    <form:options items="${filmshowList}" itemValue="filmshowId"/>
                </form:select></p>
            <p><input type="submit" value="Показать"></p>
        </form:form>
    </c:if>

    <c:if test="${!empty filteredTicketList}">
    <c:forEach items="${filteredTicketList}" var="ticket">
        <p>${ticket}</p>
    </c:forEach>
    </c:if>

    </div>
</body>
</html>
