<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#auth-form").submit(function (event) {
                event.preventDefault();
                var user = {
                    login: $("#login-auth").val(),
                    password: $("#password-auth").val()
                }
                $.ajax({
                    url: "admin/login",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify(user),
                    /*success: function(data)
                    {
                        alert(data);
                    }*/
                });
            });
        });
    </script>
</head>
<body>

<form id="auth-form" action="admin/login" method="Post" style="margin: 200px auto">
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
