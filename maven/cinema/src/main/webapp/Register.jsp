<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
	<title>Register</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <link rel="stylesheet" href="resources/css/login.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
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
                    $.ajax({url: "LoginCheck?login=" + $("#login").val(), success: function(data)
                    {
                        $("#loginCheck").text(data);
                    }})
                }
            })
            $("#reg").submit(function(event)
            {
                $.ajax({url: "LoginCheck?email=" +$("#email").val(), async: false,  success: function(data)
                {
                    if(data != "")
                    {
                        alert(data);
                        event.preventDefault();
                    }
                }
                })
            })
        })
    </script>

</head>
<body>

<div class="wrapper">

    <div class="top">
        <img src="resources/img/logo.jpg" align="left"/>

         <%-- <div style="float: right">
             <form id="auth" method="Get">
                 <ul class="navigation">
                     <li style="float:none">Логин      <input type="text" id="login_auth" size="10"></li>
                     <li style="float:none">Пароль  <input type="text" id="password_auth" size="10" ></li>
                     <li><a style="line-height: 15px" href="" title="Home">Вход</a></li>
                     <li><a style="line-height: 15px" href="" title="About us">Регистрация</a></li>
                 </ul>
             </form>
         </div>	   --%>


     <form method="post" action="" class="login">
     <p>
       <label for="login">Логин:</label>
       <input type="text" name="login" id="login" value="name@example.com">
     </p>

     <p>
       <label for="password">Пароль:</label>
       <input type="password" name="password" id="password" value="4815162342">
     </p>

     <p class="login-submit">
       <button type="submit" class="login-button">Войти</button>
     </p>

     <p class="forgot-password"><a href="index.html">Забыл пароль?</a></p>
   </form>
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
             <td>Введите логин <input type="text" name="login" id="login"></td>
             <td><div id="loginCheck"></div></td>
         </tr>
         <tr>
             <td>Введите пароль <input type="text" name="password"></td>
         </tr>
         <tr>
             <td>Введите email <input type="text" name="email" id="email"></td>
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