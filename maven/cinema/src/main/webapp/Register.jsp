<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
	<title>Register</title>
</head>
<body>
	<form action="Register" method="Get">
		<p>Введите логин<input type="text" name="login" id="login" onchange="getLogin();"></p>
        <p><div id="loginCheck" name="loginCheck"></div> </p>
		<p>Введите пароль<input type="text" name="password"></p>
		<p>Введите email<input type="text" name="email"></p>
		<p><input type="submit" value="Зарегистрироваться"></p>
	</form>
	<p><a href="UserList">Список пользователей</a></p>
</body>


<script type="text/javascript">
    var request = false;
    try
    {
        request = new XMLHttpRequest();
        alert("XMLHttpRequest OK");
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
        alert("getLogin works");
        var login = document.getElementById("login");
        var url = "http://localhost:8080/cinema/Register?login=";
        request.open("GET", url+login, true);
        request.onreadystatechange = updatePage;
        request.send(null);

    }

    function updatePage()
    {
        alert("updatePage works");
        if(request.readyState == 4)
        {
            if(request.status == 200)
            {
                var response = request.responseText;
                alert("Response: " + response);
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
    }
</script>
</html>