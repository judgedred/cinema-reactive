<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
	<title>Delete Filmshow</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>Удалить сеанс</p>
	<form action="DeleteFilmshow" method="Get">
		<p><select name="filmshow-select">
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
