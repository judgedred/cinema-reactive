<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Delete Hall</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#hall").submit(function(event) {
                $.ajax({
                    url: "checkHall/" + $("#hallId").val(),
                    async: false,
                    success: function(data) {
                        if(data != "") {
                            alert(data);
                            event.preventDefault();
                        }
                    }
                })
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить зал</p>

    <c:if test="${!empty hallList}">
        <form:form action="deleteHall" modelAttribute="hall">
            <p>
                <form:label path="hallId">Зал</form:label>
                <form:select path="hallId">
                    <form:option value="0" label="Выберите зал"/>
                    <form:options items="${hallList}" itemLabel="hallName" itemValue="hallId"/>
                </form:select>
            </p>
            <p><input type="submit" value="Удалить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
