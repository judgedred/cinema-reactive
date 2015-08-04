<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title
    <link rel="stylesheet" href="resources/css/styles.css"/>
</head>
<body>

<form id="auth-form" action="Admin/Login" method="Post" style="margin: 200px auto">
    <table align="center">
        <tr>
            <td><label for="login-auth">Логин</label></td>
            <td><input type="text" id="login-auth" name="login-auth" size="10"></td>
        </tr>
        <tr>
            <td><label for="password-auth">Пароль</label></td>
            <td><input type="text" id="password-auth" name="password-auth" size="10" ></td>
        </tr>
        <tr>
            <td><input type="submit" value="Вход"></td>
        </tr>
    </table>
</form>

</body>
</html>
