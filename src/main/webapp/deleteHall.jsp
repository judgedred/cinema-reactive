<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Hall</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#hall-delete").submit(function (event) {
                $.ajax({
                    url: "../ProcessServlet/hallCheck?hall-select=" + $("#hall-select").val(),
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

    <p>Удалить зал</p>
    <form action="deleteHall" method="Get" id="hall-delete">
        <p><select name="hall-select" id="hall-select">
            <option selected disabled>Выберите зал</option>
                <%
				List<Hall> hallLs = (List<Hall>)session.getAttribute("hallList");
				for(Hall h: hallLs)
				{
			%>
            <option value=<%=h.getHallId()%>><%=h%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
