<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.joda.time.LocalDate" %>
<%@ page import="org.joda.time.format.DateTimeFormatter" %>
<%@ page import="org.joda.time.format.DateTimeFormat" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>Main</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
    <script type="text/javascript">
            function authCheck()
            {
                var userValid = true;
                $.ajax({url: "ProcessServlet/AuthCheck", async: false, success: function(data){
                    if(data != "")
                    {
                        alert(data);
                        userValid = false;
                    }
                }
                });
                if(!userValid)
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
    </script>

</head>
<body>
<div class="wrapper">
<jsp:include page="top.jsp"/>

	<h2>Сегодня в кино</h2>

    <%
        List<Filmshow> filmshowLst = (List<Filmshow>)session.getAttribute("filmshowToday");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
        for(Filmshow f : filmshowLst)
        {
    %>
            <p><a href="ReserveTicket?filmshow-select=<%=f.getFilmshowId()%>" onclick="return authCheck();" ><%=f%></a></p>
    <%
        }
    %>

    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
