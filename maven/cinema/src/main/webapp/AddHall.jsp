<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>AddHall</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">

    <jsp:include page="admin_menu.jsp"/>

    <p>Добавить зал</p>
    <form action="AddHall" method="Get" id="hall-add">
        <p><label for="hall-add-number">Введите номер </label><input type="text" name="hall-add-number" id="hall-add-number"></p>
        <p><label for="hall-add-name">Введите название </label><input type="text" name="hall-add-name" id="hall-add-name"></p>
        <p><input type="submit" value="Добавить зал"></p>
    </form>
</div>
</body>
</html>