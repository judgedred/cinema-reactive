<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>


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
            $("#dateTime").datetimepicker({firstDay: 1, showOtherMonths: true, selectOtherMonths: true, dateFormat: "yy-mm-dd"});
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Добавление сеанса</p>
	<%--<form action="addFilmshow" method="Post">
                <p><label for="film">Фильм</label>
                <select id="film" name="film">
                    <option selected disabled>Выберите фильм</option>
                    <c:forEach items="${filmList}" var="film">
                        <option value="${film.filmId}">${film.filmName}</option>
                    </c:forEach>
                    </select>
                </p>
                <p><label for="hall">Зал</label>
                <select id="hall" name="hall">
                    <option selected disabled>Выберите зал</option>
                    <c:forEach items="${hallList}" var="hall">
                        <option value="${hall.hallId}">${hall.hallName}</option>
                    </c:forEach>
                </select>
                </p>
            <p><label for="dateTime">Дата</label>
            <input type="text" name="dateTime" id="dateTime" size="25"></p>
                <p><input type="submit" value="Добавить"></p>
	</form>--%>

    <form:form action="addFilmshow" modelAttribute="filmshow">
    <p><form:label path="film">Фильм</form:label>
        <form:select path="film">
        <form:option value="0" disabled="false" label="Выберите фильм"/>
        <form:options items="${filmList}" itemLabel="filmName" itemValue="filmId"/>     <%--itemValue="${film}" itemLabel="${film.filmName}"--%>
        </form:select></p>
        <form:label path="hall">Зал</form:label>
        <form:select path="hall">
            <form:option value="0" disabled="false" label="Выберите зал"/>
            <form:options items="${hallList}" itemLabel="hallName" itemValue="hallId"/>     <%--itemValue="${film}" itemLabel="${film.filmName}"--%>
        </form:select></p>
        <p><form:label path="dateTime">Дата</form:label>
            <form:input path="dateTime" type="text" size="25"/></p>
        <p><input type="submit" value="Добавить"></p>
    </form:form>
</div>
</body>
</html>