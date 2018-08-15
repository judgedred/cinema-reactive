<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>AddTicket</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#filmshow").change(function() {
                $.ajax({
                    url: "seatsFilter/" + $("#filmshow").val(), dataType: "json", success: function(data) {
                        var options = '<option value="">Выберите место</option>';
                        $.each(data, function(key, value) {
                            options += '<option value="' + key + '">' + value + '</option>';
                        })
                        $("select#seat").html(options);
                    }
                })
            });
        });
    </script>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

    <p>Добавить билет</p>

    <c:if test="${!empty filmshowList}">
        <form:form action="addTicket" modelAttribute="ticket" method="post">
            <p><form:label path="filmshow">Сеанс</form:label>
                <form:select path="filmshow">
                    <form:option value="0" label="Выберите сеанс"/>
                    <form:options items="${filmshowList}" itemValue="filmshowId"/>
                </form:select>
                <form:errors path="filmshow"/></p>
            <p><form:label path="price">Введите цену</form:label>
                <form:input path="price" type="text"/>
                <form:errors path="price"/></p>
            <form:label path="seat">Место</form:label>
            <form:select path="seat">
                <form:option value="0" label="Выберите место"/>
            </form:select>
            <form:errors path="seat"/>
            <p><input type="submit" value="Добавить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
