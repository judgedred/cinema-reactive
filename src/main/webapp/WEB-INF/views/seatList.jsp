<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
    <title>SeatList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>SeatList</p>

    <c:if test="${!empty seatList}">
        <table>
            <c:forEach items="${seatList}" var="seat">
                <tr>
                    <td>${seat}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>
</body>
</html>