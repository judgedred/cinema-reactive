<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
	<title>Register</title>
    <script type="text/javascript" src="js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#login").change()
        })
    </script>
</head>
<body>
<table>
    <form action="Register" method="Get">
        <tr>
            <td>Введите логин <input type="text" name="login" id="login" onchange="getLogin();"></td>
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
    </form>
</table>
<p><a href="UserList">Список пользователей</a></p>
</body>

<script type="text/javascript">
    var request = false;
    try
    {
        request = new XMLHttpRequest();
    }
    catch(explorer)
    {
        try
        {
            request = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch(explorer2)
        {
            try
            {
                request = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch(fail)
            {
                request = false;
            }
        }
    }
    if(!request)
    {
        alert("Error initializing XMLHttpRequest");
    }

    function getLogin()
    {
        var login = document.getElementById("login").value;
        var url = "http://localhost:8080/cinema/LoginCheck?login=";
        request.open("GET", url+login, true);
        request.onreadystatechange = updatePage;
        request.send(null);
    }

    function updatePage()
    {
        if(request.readyState == 4)
        {
            if(request.status == 200)
            {
                var response = request.responseText;
                document.getElementById("loginCheck").innerHTML = response;
            }
            else if(request.status == 404)
            {
                alert("Request url does not exist");
            }
            else
            {
                alert("Error: status code is " + request.status);
            }
        }

        if(document.getElementById("login") == null || document.getElementById("login").value == "")
        {
            document.getElementById("loginCheck").innerHTML = null;
        }
    }
</script>
</html>