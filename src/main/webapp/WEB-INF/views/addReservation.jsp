<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>AddReservation</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#filmshow").change(function(){
                $.ajax({
                    url: "ticketsFilter/" + $("#filmshow").val(), dataType: "json", success: function(data) {
                        var options = '<option value="">Выберите билет</option>';
                        $.each(data, function(key, value) {
                            options += '<option value="' + key + '">' + value + '</option>';
                        })
                        $("select#ticket").html(options);
                    }
                })
            });
            $("#reservation").submit(function (event) {
                if($("#filmshow").val() == null || $("#user").val() == null
                || $("#filmshow").val() == 0 || $("#user").val() == 0
                || $("#ticket").val() == null || $("#ticket").val() == 0)
                {
                    alert("Заполните поля");
                    event.preventDefault();
                }
            });
        });
    </script>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

    <p>Забронировать билет</p>

    <c:if test="${!empty filmshowList && !empty userList}">
        <form:form action="addReservation" modelAttribute="reservation" method="post">
            <p><label for="filmshow">Сеанс</label>
                <select id="filmshow" name="filmshow">
                    <option value="0">Выберите сеанс</option>
                    <c:forEach items="${filmshowList}" var="filmshow">
                        <option value="${filmshow.filmshowId}">${filmshow}</option>
                    </c:forEach>
                </select></p>
            <p><form:label path="user">Пользователь</form:label>
                <form:select path="user">
                    <form:option value="0" label="Выберите пользователя"/>
                    <form:options items="${userList}" itemValue="userId"/>
                </form:select></p>
            <form:label path="ticket">Билет</form:label>
            <form:select path="ticket">
                <form:option value="0" label="Выберите билет"/>
            </form:select>
            <p><input type="submit" value="Зарезервировать билет"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
