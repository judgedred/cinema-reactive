<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>AddUser</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#user").submit(function (event) {
                if ($("#login").val() == null || $("#password").val() == "" || $("#password").val() == null
                        || $("#login").val() == "" || $("#email").val() == null || $("#email").val() == "")
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

    <p>Добавить пользователя</p>

    <form:form action="addUser" modelAttribute="user" method="post">
        <table>
            <tr>
                <td><form:label path="login">Введите логин</form:label></td>
                <td><form:input path="login" type="text"/></td>
            </tr>
            <tr>
                <td><form:label path="password">Ввведите пароль</form:label></td>
                <td><form:input path="password" type="text"/></td>
            </tr>
            <tr>
                <td><form:label path="email">Ввведите email</form:label></td>
                <td><form:input path="email" type="text"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Добавить"></td>
            </tr>
        </table>
    </form:form>

</div>
</body>
</html>