<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>

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
                $.ajax({url: "authCheck", async: false, success: function(data){
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
	<h2>Сегодня в кино</h2>

        <c:if test="${!empty filmshowToday}">
            <table>
                <c:forEach items="${filmshowToday}" var="filmshow">
                    <tr>
                        <td><a href="reserveTicket?filmshowId=${filmshow.filmshowId}" onclick="return authCheck();" >${filmshow}</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>

        <p><h4>Чтобы забронировать билет, нажмите на сеанс.</h4></p>
    </div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
