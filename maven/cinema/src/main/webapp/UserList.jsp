﻿<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>UserList</title>
</head>
<body>
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

</body>
</html>