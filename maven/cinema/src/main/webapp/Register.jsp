<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
	<title>Register</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function()
        {
            $("#login").change(function ()
            {
                if($("#login").val() == "")
                {
                    $("#login-check").empty();
                }
                else
                {
                    $.ajax({url: "ProcessServlet/RegisterCheck?login=" + $("#login").val(), success: function(data)
                    {
                        $("#login-check").text(data);
                    }})
                }
            });

            $("#reg").submit(function(event)
            {
                $.ajax({url: "ProcessServlet/RegisterCheck?email=" +$("#email").val(), async: false,  success: function(data)
                {
                    if(data != "")
                    {
                        alert(data);
                        event.preventDefault();
                    }
                    else
                    {
                        alert("Вы зарегистрированы");
                    }
                }
                })
            });

            $.ajax({url: "ProcessServlet/LoginCheck", success: function (data)
            {
                if(data != "")
                {
                    $("#auth").hide();
                    $("#authLoggedIn").find("ul").prepend("<li>" + data  + "</li>");
                    $("#authLoggedIn").show();
                }
            }})
       });

        function authForm()
        {
            var msg = $("#auth-form").serialize();
            $.ajax({url: "ProcessServlet/LoginCheck", type: "Post", data: msg, success: function(data)
            {
                if(data != null && data != "")
                {
                    $("#auth").hide();
                    $("#authLoggedIn").find("ul").prepend("<li>" + data  + "</li>");
                    $("#authLoggedIn").show();
                }
                else
                {
                    alert("Неверный логин или пароль");
                    location.reload();
                }
            }
            });
        }
        function logout()
        {
            $.ajax({url: "ProcessServlet/Logout", success: function()
            {
                $("#auth").show();
                $("#authLoggedIn").hide();
            }
            });
        }
    </script>

</head>
<body>

<div class="wrapper">

    <div class="top">
        <img src="resources/img/logo.jpg" align="left"/>

        <div id="authLoggedIn" style="display: none">
            <ul class="navigation">
                <li><a href="javascript: logout()" style="line-height: 15px; padding: 6px 0"  href="" title="брони">список билетов</a></li>
                <p><li><a href="javascript: logout()" style="line-height: 15px; padding: 0 60px"  href="" title="Выйти">Выход</a></li></p>
            </ul>
        </div>

        <div id="auth">
            <form id="auth-form" action="javascript:void(null);" method="Post" onsubmit="authForm()">
                <table>
                    <tr>
                        <%--<td>Логин</td>--%>
                        <td><label for="login-auth">Логин</label></td>
                        <td><input type="text" id="login-auth" name="login-auth" size="10"></td>
                    </tr>
                    <tr>
                        <td><label for="password-auth">Пароль</label></td>
                        <td><input type="text" id="password-auth" name="password-auth" size="10" ></td>
                    </tr>
                </table>
                <ul class="navigation">
                    <li><a style="line-height: 15px" href="" title="Home">Регистрация</a></li>
                    <%--<input type="hidden" name="from" value="${pageContext.request.requestURI}">--%>
                    <li><input type="submit" value="Вход"></li>
                </ul>
            </form>
        </div>
    </div>

     <ul class="navigation">

         <li><a href="" title="Home">Home</a></li>
         <li><a href="" title="About us">About us</a></li>
         <li><a href="" title="Portfolio">Portfolio</a>
             <ul>
                 <li><a href="" title="Websites">Websites</a></li>
                 <li><a href="" title="Webshops">Webshops</a></li>
                 <li><a href="" title="SEO">SEO</a></li>
                 <li><a href="" title="Responsive webdesign">Responsive webdesign</a></li>
             </ul>
         </li>
         <li><a href="" title="Contact">Contact</a></li>

         <div class="clear"></div>
     </ul>


 <form id="reg" action="Register" method="Get">
 <table>
         <tr>
             <td><label for="login">Введите логин </label><input type="text" name="login" id="login"></td>
             <td><div id="login-check"></div></td>
         </tr>
         <tr>
             <td><label for="password">Введите пароль </label><input type="text" name="password" id="password"></td>
         </tr>
         <tr>
             <td><label for="email">Введите email </label><input type="text" name="email" id="email"></td>
         </tr>
         <tr>
             <td><input type="submit" value="Зарегистрироваться"></td>
         </tr>
 </table>
 </form>
 <p><a href="UserList">Список пользователей</a></p>

     <div class="footer">
         <h1 class="pageTitle">Dropdown navigation</h1>
         <p>A beautifull but simple dropdown navigation. Realized with only CSS, no Javascript needed. Great fallback for users who disabled Javascript.</p>
     </div>
 </div>
 </body>
 </html>