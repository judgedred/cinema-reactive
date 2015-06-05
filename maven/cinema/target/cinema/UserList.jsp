<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>UserList</title>
</head>
<body>
	<p>UserList</p>

	<%
		List<User> ls = (List<User>)session.getAttribute("UserList");
		for(Iterator<User> i = ls.iterator(); i.hasNext(); )
		{
			User u = i.next();
	%>
			<p><%=u%></p>
	<%
		}
	%>

</body>
</html>