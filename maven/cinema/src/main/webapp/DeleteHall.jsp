<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete Hall</title>
    <link rel="stylesheet" href="/cinema/resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить зал</p>
    <form action="DeleteHall" method="Get">
        <p><select name="hall-select">
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
