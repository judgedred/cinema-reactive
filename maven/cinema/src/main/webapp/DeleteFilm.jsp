<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Film</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#film-delete").submit(function (event) {
                $.ajax({
                    url: "../ProcessServlet/FilmCheck?film-select=" + $("#film-select").val(),
                    async: false,
                    success: function (data) {
                        if (data != "") {
                            alert(data);
                            event.preventDefault();
                        }
                    }
                })
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить фильм</p>
    <form action="DeleteFilm" method="Get" id="film-delete">
        <p><select name="film-select" id="film-select">
            <option selected disabled>Выберите фильм</option>
                <%
				List<Film> filmLs = (List<Film>)session.getAttribute("filmList");
				for(Film f: filmLs)
				{
			%>
            <option value=<%=f.getFilmId()%>><%=f%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
