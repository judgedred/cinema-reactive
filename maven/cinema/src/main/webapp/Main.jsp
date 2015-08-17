<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Main</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
</head>
<body>

<div class="wrapper">
<jsp:include page="top.jsp"/>

	<p><h2>Сегодня в кино</h2></p>
		
	<%	
		List<Filmshow> ls = (List<Filmshow>)session.getAttribute("filmshowList");
	 	for(Iterator<Filmshow> i = ls.iterator(); i.hasNext(); )
		{
			Filmshow f = i.next(); 
	%>
			<p><a href="ReserveTicket"><%=f%></a></p>
	<%	
		} 
	%>

    <jsp:include page="footer.jsp"/>

   </div>
</body>
</html>
