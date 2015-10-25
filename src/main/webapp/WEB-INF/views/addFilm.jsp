<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
	<title>Add Film</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#film").submit(function (event) {
                if ($("#filmName").val() == null || $("#description").val() == "" || $("#description").val() == null
                        || $("#filmName").val() == "")
                {
                    alert("Заполните поля");
                    event.preventDefault();
                }
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Добавить фильм</p>

    <form:form action="addFilm" modelAttribute="film" method="post">
        <table>
            <tr>
                <td><form:label path="filmName">Название фильма</form:label></td>
                <td><form:input path="filmName" type="text"/></td>
            </tr>
            <tr>
                <td><form:label path="description">Описание</form:label></td>
                <td><form:textarea path="description" type="text" cols="22" rows="4"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Добавить"></td>
            </tr>
        </table>
    </form:form>

</div>
</body>
</html>