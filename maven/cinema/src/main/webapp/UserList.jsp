<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>UserList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>UserList</p>

	<%
		List<User> ls = (List<User>)session.getAttribute("userList");
		for(User u : ls)
		{
	%>
	<p><%=u%></p>
	<%
		}
	%>
</div>
</body>
</html>