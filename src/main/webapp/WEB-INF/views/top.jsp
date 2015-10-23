<%@ page contentType="text/html;charset=UTF-8" %>


<html>
<head>
    <title>Menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<div class="top">
    <a href="main"><img src="resources/img/logo.jpg" align="left"></a>

    <div id="authLoggedIn" style="display: none">
        <ul class="navigation">
            <li><a href="reservationList" style="line-height: 15px; padding: 6px 0" title="брони">список билетов</a></li>
            <p><li><a href="javascript: logout()" style="line-height: 15px; padding: 0 60px" title="Выйти">Выход</a></li></p>
        </ul>
    </div>

    <div id="auth">
        <form id="auth-form" action="javascript:void(null);" method="Post" onsubmit="authForm()">
            <table>
                <tr>
                    <td><label for="login-auth">Логин</label></td>
                    <td><input type="text" id="login-auth" name="login-auth" size="10"></td>
                </tr>
                <tr>
                    <td><label for="password-auth">Пароль</label></td>
                    <td><input type="text" id="password-auth" name="password-auth" size="10" ></td>
                </tr>
            </table>
            <ul class="navigation">
                <li><a href="register" style="line-height: 15px" title="Register">Регистрация</a></li>
                <li><input type="submit" value="Вход"></li>
            </ul>
        </form>
    </div>

</div>
<ul class="navigation">

    <li><a href="news" title="News">Новости</a></li>
    <li><a href="filmshow" title="Filmshow">Сеансы</a></li>
    <li><a href="film" title="Films">Фильмы</a></li>
    <li><a href="about" title="About">О кинотеатре</a></li>

    <div class="clear"></div>
</ul>
</html>