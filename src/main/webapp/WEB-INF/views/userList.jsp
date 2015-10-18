<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>

<html>
<head>
	<title>UserList</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

	<p>UserList</p>

    <c:if test="${!empty userList}">
        <table>
            <c:forEach items="${userList}" var="user">
                <tr>
                    <td>${user}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</div>
</body>
</html>