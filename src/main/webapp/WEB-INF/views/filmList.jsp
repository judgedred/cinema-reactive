<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>FilmList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>FilmList</p>
    <c:if test="${!empty filmList}">
        <table>
            <c:forEach items="${filmList}" var="film">
                <tr>
                    <td>${film}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

</div>
</body>
</html>
