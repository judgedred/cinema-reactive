<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>LoginCheck</title>
</head>
<body>
	<p>UserList</p>

	<%
		String loginCheck = (List<User>)session.getAttribute("UserList");
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