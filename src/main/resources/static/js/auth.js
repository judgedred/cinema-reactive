$(document).ready(function() {
    var user = {
        login: null,
        password: null
    };
    $("#login-auth").focus();

    $.ajax({
        url: "loginCheck",
        type: "Post",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(user),
        success: function(data) {
            if(data != "") {
                $("#auth").html('<ul class="navigation">' +
                    '<li style="padding: 0 10px">' + data + '</li>' +
                    '<li><a href="reservationList" style="line-height: 15px; padding: 6px 0" title="брони">список билетов</a></li>' +
                    '<p><li><a href="javascript: logout()" style="line-height: 15px; padding: 0 60px" title="Выйти">Выход</a></li></p>' +
                    '</ul>');
            }
        }
    })
});

function authForm() {
    var user = {
        login: $("#login-auth").val(),
        password: $("#password-auth").val()
    }

    $.ajax({
        url: "loginCheck",
        type: "Post",
        contentType: "application/json; charset=UTF-8",
        data: JSON.stringify(user),
        success: function(data) {
            if(data != null && data != "") {
                location.reload();
            }
            else {
                alert("Неверный логин или пароль");
                location.reload();
            }
        }
    });
}

function logout() {
    $.ajax({
        url: "logout", success: function() {
            location.reload();
        }
    });
}