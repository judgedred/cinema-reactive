<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete User</title>
    <link rel="stylesheet" href="/cinema/resources/css/styles.css"/>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить пользователя</p>
    <form action="DeleteUser" method="Get">
        <p><select name="user-select">
            <option selected disabled>Выберите пользователя</option>
                <%
				List<User> userLs = (List<User>)session.getAttribute("userList");
				for(User u: userLs)
				{
			%>
            <option value=<%=u.getUserId()%>><%=u%></option>
                <%
				}
			%>
        </p></select>
        <p><input type="submit" value="Удалить"></p>
    </form>
</div>
</body>
</html>
