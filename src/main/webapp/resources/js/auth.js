$(document).ready(function()
{
    var user = {
        login: null,
        password: null
    };

    $.ajax({url: "loginCheck",
        type: "Post",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(user),
        success: function (data)
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
    var user = {
        login: $("#login-auth").val(),
        password: $("#password-auth").val()
    }

    $.ajax({url: "loginCheck",
        type: "Post",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(user),
        success: function(data)
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
    $.ajax({url: "logout", success: function()
    {
        $("#auth").show();
        $("#authLoggedIn").hide();
        location.reload();
    }
    });
}