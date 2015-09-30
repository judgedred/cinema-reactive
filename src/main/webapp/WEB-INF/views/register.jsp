<%@ page contentType="text/html; charset=utf-8"%>

<html>
<head>
	<title>Register</title>
    <link rel="stylesheet" href="../../resources/css/styles.css"/>
    <script type="text/javascript" src="../../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="../../resources/js/auth.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#login-reg").change(function () {
                if ($("#login-reg").val() == "") {
                    $("#login-reg-check").empty();
                }
                else {
                    $.ajax({
                        url: "ProcessServlet/registerCheck?login-reg=" + $("#login-reg").val(), success: function (data) {
                            $("#login-reg-check").text(data);
                            if(data == "Логин занят")
                            {
                                $("#reg-submit").attr("disabled", "disabled");
                            }
                            else
                            {
                                $("#reg-submit").removeAttr("disabled");

                            }
                        }
                    })
                }
            });

            $("#reg").submit(function (event) {
                $.ajax({
                    url: "ProcessServlet/registerCheck?email-reg=" + $("#email-reg").val(),
                    async: false,
                    success: function (data) {
                        if (data != "") {
                            alert(data);
                            event.preventDefault();
                        }
                        else if ($("#login-reg").val() == "" || $("#password-reg").val() == "" || $("#email-reg").val() == "")
                        {
                            alert("Заполните поля");
                            event.preventDefault();
                        }
                        else {
                            alert("Вы зарегистрированы");
                        }
                    }
                });
            });
        });
    </script>

</head>
<body>

<div class="wrapper">

    <jsp:include page="top.jsp"/>
    <div class="content">
    <form id="reg" action="register" method="Get">
    <table>
         <tr>
             <td><label for="login-reg">Введите логин </label><input type="text" name="login-reg" id="login-reg"></td>
             <td><div id="login-reg-check"></div></td>
         </tr>
         <tr>
             <td><label for="password-reg">Введите пароль </label><input type="text" name="password-reg" id="password-reg"></td>
         </tr>
         <tr>
             <td><label for="email-reg">Введите email </label><input type="text" name="email-reg" id="email-reg"></td>
         </tr>
         <tr>
             <td><input type="submit" id="reg-submit" value="Зарегистрироваться"></td>
         </tr>
    </table>
    </form>

    </div>
    <jsp:include page="footer.jsp"/>

 </div>
 </body>
 </html>