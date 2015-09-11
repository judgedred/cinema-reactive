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
    <form action="addUser" method="Get" id="user-add">
        <p><label for="user-add-login">Введите логин </label><input type="text" name="user-add-login" id="user-add-login"></p>
        <p><label for="user-add-password">Введите пароль </label><input type="text" name="user-add-password" id="user-add-password"></p>
        <p><label for="user-add-email">Введите email </label><input type="text" name="user-add-email" id="user-add-email"></p>
        <p><input type="submit" value="Добавить пользователя"></p>
    </form>
</div>
</body>
</html>