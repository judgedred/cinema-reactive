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

    <style>
        @import url(http://fonts.googleapis.com/css?family=Raleway:400,200,300,100,500,700,600,800,900);

        body {
            background: #d4d4d4;
            font-family: 'Raleway', sans-serif;
            font-weight: 400;
            font-size: 14px;
            line-height: 26px;
            color: #666;
        }

        .clear {
            clear: both;
        }

        .wrapper {
            width: 620px;
            margin: 20px auto;
            border-radius: 10px;
            border: solid 3px #fff;
            box-shadow: 0 3px 5px 0 rgba(0, 0, 0, 0.1);
        }

        .top {
            min-height: 100px;
            background: #ED6B16;
            border-radius: 10px 10px 0 0;
        }

        .footer {
            min-height: 200px;
            padding: 20px;
            background: #e4e4e4;
            border-radius: 0 0 10px 10px;
        }

        .pageTitle {
            font-size: 24px;
            line-height: 40px;
            font-weight: 700;
            color: #000;
        }

        .navigation {
            list-style: none;
            padding: 0;
            margin: 0;
            background: rgb(158,58,58);
            border-top: solid 3px #fff;
            border-bottom: solid 3px #fff;
            /*
            box-shadow:  0px -2px 3px -1px rgba(0, 0, 0, 1);
            */
        }

        .navigation li {
            float: left;
        }

        .navigation li:hover {
            background: #782;
        }

        .navigation li:first-child {
            -webkit-border-radius: 10px 100px 5 6;
            border-radius: 100px 2 3 100px;
        }

        .navigation li a {
            display: inline;
            padding: 2 40px;
            text-decoration: none;
            line-height: 40px;
            color: #fff;
        }

        .navigation ul {
            display: none;
            position: absolute;
            list-style: none;
            margin-left: -3px;
            padding: 0;
            overflow: hidden;
        }

        .navigation ul li {
            float: none;
        }

        .navigation li:hover > ul {
            display: block;
            background: #222;
            border: solid 3px #fff;
            border-top: 0;

            -webkit-border-radius: 0 0 8px 8px;
            border-radius: 0 0 8px 8px;

            -webkit-box-shadow:  0px 3px 3px 0px rgba(0, 0, 0, 0.25);
            box-shadow:  0px 3px 3px 0px rgba(0, 0, 0, 0.25);
        }

        .navigation li:hover > ul li:hover {
            -webkit-border-radius: 0 0 5px 5px;
            border-radius: 0 0 5px 5px;
        }

        .navigation li li a:hover {
            background: #000;
        }

        .navigation ul li:last-child a,
        .navigation ul li:last-child a:hover {
            -webkit-border-radius: 0 0 5px 5px;
            border-radius: 0 0 5px 5px;
        }
    </style>

</head>
<body>
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
</body>
</html>