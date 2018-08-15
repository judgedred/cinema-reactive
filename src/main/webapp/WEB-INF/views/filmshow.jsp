<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Filmshow</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
    <script type="text/javascript">
        function authCheck() {
            var userValid = true;
            $.ajax({
                url: "authCheck", async: false, success: function(data) {
                    if(data != "") {
                        alert(data);
                        userValid = false;
                    }
                }
            });
            if(!userValid) {
                return false;
            }
            else {
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

        <c:if test="${!empty filmshowMap}">
            <c:forEach items="${filmshowMap}" var="date">
                <p><fmt:formatDate value="${date.key.toDate()}" pattern="dd-MM-yyyy"/></p>
                <c:forEach items="${date.value}" var="filmshow">
                    <p><a href="reserveTicket?filmshowId=${filmshow.filmshowId}"
                          onclick="return authCheck();">${filmshow}</a></p>
                </c:forEach>

            </c:forEach>
        </c:if>

        <p><h4>Чтобы забронировать билет, нажмите на сеанс.</h4></p>
    </div>
    <jsp:include page="footer.jsp"/>

</div>
</body>
</html>
