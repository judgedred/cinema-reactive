<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
	<title>Register</title>
    <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#login").change(function ()
            {
                if($("#login") == null || $("#login").val() == "")
                {
                    $("#loginCheck").empty();
                }
                else
                {
                    $.get("LoginCheck?login=" + $("#login").val(), function(data)
                    {
                        $("#loginCheck").text(data);
                    })
                }
            })
        })
    </script>
</head>
<body>
<form action="Register" method="Get">
<table>
        <tr>
            <td>Введите логин <input type="text" name="login" id="login"></td>
            <td><div id="loginCheck"></div></td>
        </tr>
        <tr>
            <td>Введите пароль <input type="text" name="password"></td>
        </tr>
        <tr>
            <td>Введите email <input type="text" name="email"></td>
        </tr>
        <tr>
            <td><input type="submit" value="Зарегистрироваться"></td>
        </tr>
</table>
</form>
<p><a href="UserList">Список пользователей</a></p>
</body>
</html>