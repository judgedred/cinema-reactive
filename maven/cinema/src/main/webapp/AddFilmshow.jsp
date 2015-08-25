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
            $("#date-time").datetimepicker({firstDay: 1, showOtherMonths: true, selectOtherMonths: true, dateFormat: "yy-mm-dd"});
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Добавление сеанса</p>
	<form action="AddFilmshow" method="Get">
		<p><select name="filmSelect">
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
		</p></select>
		<p><select name="hallSelect">
			<option selected disabled>Выберите зал</option>
			<% 
				List<Hall> hallLst = (List<Hall>)session.getAttribute("hallList");
				for(Iterator<Hall> i = hallLst.iterator(); i.hasNext(); )
				{
					Hall h = i.next();
			%>
				<option value=<%=h.getHallId()%>><%=h.getHallName()%></option>
			<%
				}
			%>
		</p></select>
		<p>Выберите дату<input type="datetime" name="date-time" id="date-time" size="25"></p>
		<p><input type="submit" value="Добавить"></p>
	</form>
</div>
</body>
</html>