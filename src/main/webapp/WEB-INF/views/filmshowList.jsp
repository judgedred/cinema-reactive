<%@ page contentType="text/html; charset=utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
    <title>FilmshowList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>FilmshowList</p>
    <c:if test="${!empty filmshowList}">
        <table>
            <c:forEach items="${filmshowList}" var="filmshow">
                <tr>
                    <td>${filmshow}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>