<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>AddHall</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

    <p>Добавить зал</p>

    <form:form action="addHall" modelAttribute="hall" method="post">
        <table>
            <tr>
                <td><form:label path="hallNumber">Введите номер</form:label></td>
                <td><form:input path="hallNumber" type="text"/></td>
            </tr>
            <tr>
                <td><form:label path="hallName">Введите название</form:label></td>
                <td><form:input path="hallName" type="text"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Добавить"></td>
            </tr>
        </table>
    </form:form>

    <%--<form action="addHall" method="Get" id="hall-add">
        <p><label for="hall-add-number">Введите номер </label><input type="text" name="hall-add-number" id="hall-add-number"></p>
        <p><label for="hall-add-name">Введите название </label><input type="text" name="hall-add-name" id="hall-add-name"></p>
        <p><input type="submit" value="Добавить зал"></p>
    </form>--%>
</div>
</body>
</html>