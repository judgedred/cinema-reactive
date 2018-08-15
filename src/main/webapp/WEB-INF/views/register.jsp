<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Register</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#login").change(function() {
                if($("#login").val() == "") {
                    $("#login-check").empty();
                }
                else {
                    $.ajax({
                        url: "registerCheck?login=" + $("#login").val(), success: function(data) {
                            $("#login-check").text(data);
                            if(data == "Логин занят") {
                                $("#register-submit").attr("disabled", "disabled");
                            }
                            else {
                                $("#register-submit").removeAttr("disabled");

                            }
                        }
                    })
                }
            });

            $("#user").submit(function(event) {
                $.ajax({
                    url: "registerCheck?email=" + $("#email").val(),
                    async: false,
                    success: function(data) {
                        if(data != "") {
                            alert(data);
                            event.preventDefault();
                        }
                    }
                });
            });

            if($("#registered").text() != "") {
                alert($("#registered").text());
                location.replace("registerForm");
            }
        });
    </script>

</head>
<body>

<div class="wrapper">

    <jsp:include page="top.jsp"/>
    <div class="content">

        <form:form action="register" modelAttribute="user" method="post">
            <table>
                <tr>
                    <td><form:label path="login">Введите логин</form:label></td>
                    <td><form:input type="text" path="login"/></td>
                    <td>
                        <div id="login-check"><form:errors path="login"/></div>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="password">Введите пароль</form:label></td>
                    <td><form:input type="password" path="password"/></td>
                    <td><form:errors path="password"/></td>
                </tr>
                <tr>
                    <td><form:label path="email">Введите email</form:label></td>
                    <td><form:input type="text" path="email"/></td>
                    <td><form:errors path="email"/></td>
                </tr>
                <tr>
                    <td><input type="submit" id="register-submit" value="Зарегистрироваться"></td>
                </tr>
            </table>
        </form:form>

        <div id="registered" hidden>${registered}</div>

    </div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>