<%@ page contentType="text/html;charset=UTF-8" %>


<html>
<head>
    <title>Admin menu</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<ul class="navigation">
    <li><a href="filmshowList" title="Filmshow">Сеансы</a>
    <ul>
        <li><a href="addFilmshowForm" title="Add">Добавить</a></li>
        <li><a href="deleteFilmshow" title="Delete">Удалить</a></li>
    </ul>
    </li>
    <li><a href="ticketList" title="Ticket">Билеты</a>
        <ul>
            <li><a href="addTicketForm" title="Add">Добавить</a></li>
            <li><a href="deleteTicket" title="Delete">Удалить</a></li>
            <li><a href="addTicketAll" title="Add tickets for the show">Выпустить билеты на сеанс</a></li>
        </ul>
    </li>
    <li><a href="filmList" title="Films">Фильмы</a>
        <ul>
            <li><a href="addFilmForm" title="Add">Добавить</a></li>
            <li><a href="deleteFilm" title="Delete">Удалить</a></li>
        </ul>
    </li>
    <li><a href="seatList" title="Seats">Места</a>
    <ul>
        <li><a href="addSeatForm" title="Add">Добавить</a></li>
        <li><a href="deleteSeat" title="Delete">Удалить</a></li>
    </ul>
    </li>
    <li><a href="hallList" title="Halls">Залы</a>
        <ul>
            <li><a href="addHallForm" title="Add">Добавить</a></li>
            <li><a href="deleteHall" title="Delete">Удалить</a></li>
        </ul>
    </li>
    <li><a href="reservationList" title="Reservations">Брони</a>
        <ul>
            <li><a href="addReservationForm" title="Add">Добавить</a></li>
            <li><a href="deleteReservation" title="Delete">Удалить</a></li>
        </ul>
    </li>
    <li><a href="userList" title="Users">Пользователи</a>
        <ul>
            <li><a href="addUserForm" title="Add">Добавить</a></li>
            <li><a href="deleteUser" title="Delete">Удалить</a></li>
        </ul>
    </li>
    <li><a href="logout" title="Logout">Выход</a></li>
    <div class="clear"></div>
</ul>
</body>
</html>
