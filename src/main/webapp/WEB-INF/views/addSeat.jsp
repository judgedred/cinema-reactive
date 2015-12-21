<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>


<html>
<head>
    <title>AddSeat</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

    <p>Добавить место</p>

    <c:if test="${!empty hallList}">
    <form:form action="addSeat" modelAttribute="seat" method="post">
    <table>
        <tr>
            <td><form:label path="hall">Зал</form:label></td>
            <td><form:select path="hall">
                <form:option value="0" label="Выберите зал"/>
                <form:options items="${hallList}" itemValue="hallId"/>
            </form:select> </td>
            <td><form:errors path="hall" /></td>
        </tr>
        <tr>
            <td><form:label path="rowNumber">Введите ряд</form:label> </td>
            <td><form:input path="rowNumber" type="text"/> </td>
            <td><form:errors path="rowNumber" /></td>
        </tr>
        <tr>
            <td><form:label path="seatNumber">Введите номер</form:label> </td>
            <td><form:input path="seatNumber" type="text"/> </td>
            <td><form:errors path="seatNumber" /></td>
        </tr>
        <tr>
            <td><input type="submit" value="Добавить"/></td>
        </tr>
    </table>
    </form:form>
    </c:if>

</div>
</body>
</html>
