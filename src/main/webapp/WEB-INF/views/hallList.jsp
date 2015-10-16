<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="com.domain.*" %>
<%@ page import="java.util.*" %>

<html>
<head>
    <title>HallList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>HallList</p>

    <c:if test="${!empty hallList}">
        <table>
            <c:forEach items="${hallList}" var="hall">
                <tr>
                    <td>${hall}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>
</body>
</html>