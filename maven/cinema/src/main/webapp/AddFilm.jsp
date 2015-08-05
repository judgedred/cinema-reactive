<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
	<title>Add Film</title>
    <link rel="stylesheet" href="/cinema/resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Добавить фильм</p>
	<form action="AddFilm" method="Get">
		<p>Введите название фильма<input type="text" name="filmName"></p>
		<p>Описание</p><p><textarea rows="3" name="description"></textarea></p>
		<p><input type="submit" value="Добавить"></p>
	</form>
</div>
</body>
</html>