<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
	<title>Add Film</title>
</head>
<body>
	<p>Добавить фильм</p>
	<form action="AddFilm" method="Get">
		<p>Введите название фильма<input type="text" name="filmName"></p>
		<p>Описание</p><p><textarea rows="3" name="description"></textarea></p>
		<p><input type="submit" value="Добавить"></p>
	</form>
</body>
</html>