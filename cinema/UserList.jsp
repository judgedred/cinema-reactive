<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="dao.*" %>
<%@ page import="domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<title>UserList</title>
</head>
<body>
	<%
		try
		{
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			List<User> ls = userDao.getUserAll();
			out.println("<p>UserList</p>");
			for(Iterator<User> i = ls.iterator(); i.hasNext(); )
			{
				User user = i.next();
				out.println("<p>" + user + "</p>");
			}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		out.close();
	%>
</body>
</html>