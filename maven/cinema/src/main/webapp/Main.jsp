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

	<h2>Сегодня в кино</h2>
		
	<%	
		List<Filmshow> ls = (List<Filmshow>)session.getAttribute("filmshowList");
        for(Filmshow f : ls)
        {
    %>
            <p><a href="ReserveTicket?filmshow-select=<%=f.getFilmshowId()%>" ><%=f%></a></p>
    <%
        }
    %>

    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
