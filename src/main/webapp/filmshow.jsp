<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.joda.time.LocalDate" %>
<%@ page import="org.joda.time.format.DateTimeFormat" %>
<%@ page import="org.joda.time.format.DateTimeFormatter" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Filmshow</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
    <script type="text/javascript">
        function authCheck()
        {
            var userValid = true;
            $.ajax({url: "ProcessServlet/authCheck", async: false, success: function(data){
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
<div class="content">
    <h2>Сеансы</h2>

    <%
        Map<LocalDate, List<Filmshow>> filmshowMap = (Map<LocalDate, List<Filmshow>>)session.getAttribute("filmshowMap");
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy");
        for(LocalDate date : filmshowMap.keySet())
        {
    %>
    <p><%=date.toString(fmt)%></p>
    <%
        List<Filmshow> filmshowLst = filmshowMap.get(date);
        for(Filmshow f : filmshowLst)
        {
    %>
    <p><a href="reserveTicket?filmshow-select=<%=f.getFilmshowId()%>" onclick="return authCheck();" ><%=f%></a></p>
    <%
        }
        }
    %>

    <p><h4>Чтобы забронировать билет, нажмите на сеанс.</h4></p>
</div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
