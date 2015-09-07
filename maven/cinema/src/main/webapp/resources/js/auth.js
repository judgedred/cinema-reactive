$(document).ready(function()
{
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
        location.reload();
    }
    });
}