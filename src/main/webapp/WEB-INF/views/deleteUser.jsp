<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Delete User</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#user").submit(function(event) {
                $.ajax({
                    url: "checkUser/" + $("#userId").val(),
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

    <p>Удалить пользователя</p>

    <c:if test="${!empty userList}">
        <form:form action="deleteUser" modelAttribute="user">
            <p>
                <form:label path="userId">Фильм</form:label>
                <form:select path="userId">
                    <form:option value="0" label="Выберите пользователя"/>
                    <form:options items="${userList}" itemValue="userId"/>
                </form:select>
            </p>
            <p><input type="submit" value="Удалить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
