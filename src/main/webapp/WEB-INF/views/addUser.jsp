<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <title>AddUser</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
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
                <td><form:errors path="login"/></td>
            </tr>
            <tr>
                <td><form:label path="password">Ввведите пароль</form:label></td>
                <td><form:input path="password" type="text"/></td>
                <td><form:errors path="password"/></td>
            </tr>
            <tr>
                <td><form:label path="email">Ввведите email</form:label></td>
                <td><form:input path="email" type="text"/></td>
                <td><form:errors path="email"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Добавить"></td>
            </tr>
        </table>
    </form:form>

</div>
</body>
</html>