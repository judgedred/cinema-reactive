<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
	<title>Delete Filmshow</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#filmshow-delete").submit(function (event) {
                $.ajax({
                    url: "../ProcessServlet/FilmshowCheck?filmshow-select=" + $("#filmshow-select").val(),
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

	<p>Удалить сеанс</p>
	<form action="DeleteFilmshow" method="Get" id="filmshow-delete">
		<p><select name="filmshow-select" id="filmshow-select">
			<option selected disabled>Выберите сеанс</option>
			<%
				List<Filmshow> filmshowLst = (List<Filmshow>)session.getAttribute("filmshowList");
				for(Filmshow f: filmshowLst)
				{
			%>
			<option value=<%=f.getFilmshowId()%>><%=f%></option>
            <%
				}
			%>
		</p></select>
		<p><input type="submit" value="Удалить"></p>
	</form>
</div>
</body>
</html>
