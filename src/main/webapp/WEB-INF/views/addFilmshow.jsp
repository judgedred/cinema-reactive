<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>


<html>
<head>
	<title>Add Filmshow</title>
    <link rel="stylesheet" href="../resources/css/jquery-ui.css"/>
    <link rel="stylesheet" href="../resources/css/jquery-ui-timepicker-addon.css"/>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="../resources/js/jquery-ui-1.11.4.js"></script>
    <script type="text/javascript" src="../resources/js/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#dateTime").datetimepicker({firstDay: 1, showOtherMonths: true, selectOtherMonths: true, dateFormat: "yy-mm-dd"});
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Добавление сеанса</p>
	<form action="addFilmshow" method="Post">
		<%--<p><select name="filmSelect">
			<option selected disabled>Выберите фильм</option>
			<% 
				List<Film> filmLst = (List<Film>)session.getAttribute("filmList");
				for(Iterator<Film> i = filmLst.iterator(); i.hasNext(); )
				{
					Film f = i.next();
			%>
				<option value=<%=f.getFilmId()%>><%=f.getFilmName()%></option>
			<%
				}
			%>
		</p></select>--%>

                <p><label for="film">Фильм</label>
                <select id="film" name="film">
                    <option selected disabled>Выберите фильм</option>
                    <c:forEach items="${filmList}" var="film">
                        <option value="${film.filmId}">${film.filmName}</option>
                    </c:forEach>
                    </select>
                </p>
                <p><label for="hall">Зал</label>
                <select id="hall" name="hall">
                    <option selected disabled>Выберите зал</option>
                    <c:forEach items="${hallList}" var="hall">
                        <option value="${hall.hallId}">${hall.hallName}</option>
                    </c:forEach>
                </select>
                </p>
			<%--<%
				List<Hall> hallLst = (List<Hall>)session.getAttribute("hallList");
				for(Hall h: hallLst)
				{
					%>
            <option value=<%=h.getHallId()%>><%=h.getHallName()%>
            </option>
                <%
				}
			%>
		</p></select>--%>
            <p><label for="dateTime">Дата</label>
            <input type="text" name="dateTime" id="dateTime" size="25"></p>
                <p><input type="submit" value="Добавить"></p>
        <%--<p><input type="text" name="dateTime" id="dateTime" size="25"></p>--%>
	</form>
</div>
</body>
</html>