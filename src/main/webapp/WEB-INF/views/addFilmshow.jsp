<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Add Filmshow</title>
    <link rel="stylesheet" href="../resources/css/jquery-ui.css"/>
    <link rel="stylesheet" href="../resources/css/jquery-ui-timepicker-addon.css"/>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="../resources/js/jquery-ui-1.11.4.js"></script>
    <script type="text/javascript" src="../resources/js/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#dateTime").datetimepicker({
                firstDay: 1,
                showOtherMonths: true,
                selectOtherMonths: true,
                dateFormat: "yy-mm-dd"
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Добавление сеанса</p>

    <c:if test="${!empty filmList || !empty hallList}">
        <form:form action="addFilmshow" modelAttribute="filmshow" method="post">
            <p><form:label path="film">Фильм</form:label>
                <form:select path="film">
                    <form:option value="0" label="Выберите фильм"/>
                    <form:options items="${filmList}" itemLabel="filmName" itemValue="filmId"/>
                </form:select>
                <form:errors path="film"/></p>
            <form:label path="hall">Зал</form:label>
            <form:select path="hall">
                <form:option value="0" label="Выберите зал"/>
                <form:options items="${hallList}" itemLabel="hallName" itemValue="hallId"/>
            </form:select>
            <form:errors path="hall"/></p>
            <p><form:label path="dateTime">Дата</form:label>
                <form:input path="dateTime" type="text" size="25"/>
                <form:errors path="dateTime"/></p>
            <p><input type="submit" value="Добавить"></p>
        </form:form>
    </c:if>
</div>
</body>
</html>