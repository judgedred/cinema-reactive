<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>AddTicketAll</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#filmshow").change(function(){
                $.ajax({
                    url: "seatsFilter/" + $("#filmshow").val()
                })
            });
            $("#ticket").submit(function (event) {
                if($("#filmshow").val() == null || $("#filmshow").val() == 0 || $("#price").val() == "")
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

    <p>Выпустить билеты</p>

    <c:if test="${!empty filmshowList}">
        <form:form action="addTicketAll" modelAttribute="ticket" method="post">
            <p><form:label path="filmshow">Сеанс</form:label>
                <form:select path="filmshow">
                    <form:option value="0" label="Выберите сеанс"/>
                    <form:options items="${filmshowList}" itemValue="filmshowId"/>
                </form:select></p>
            <p><form:label path="price">Введите цену</form:label>
                <form:input path="price" type="text"/></p>
            <p><input type="submit" value="Выпустить билеты"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
