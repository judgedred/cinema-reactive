<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
	<title>Add Film</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Добавить фильм</p>
	<form action="addFilm" method="Get" accept-charset="UTF-8">
		<p><label>Введите название фильма
            <input type="text" name="filmName">
        </label></p>
		<p><label for="description">Описание</label></p><p><textarea rows="3" name="description" id="description"></textarea></p>
		<p><input type="submit" value="Добавить"></p>
	</form>
</div>
</body>
</html>