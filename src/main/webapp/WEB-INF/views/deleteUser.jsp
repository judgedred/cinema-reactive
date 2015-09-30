<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.util.*" %>
<%@ page import="com.domain.*" %>


<html>
<head>
    <title>Delete User</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#user-delete").submit(function (event) {
                $.ajax({
                    url: "../ProcessServlet/userCheck?user-select=" + $("#user-select").val(),
                    async: false,
                    success: function (data) {
                        if (data != "") {
                            alert(data);
                            event.preventDefault();
                        }
                    }
                })
            });
        });
    </script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="admin_menu.jsp"/>

    <p>Удалить пользователя</p>
    <form action="deleteUser" method="Get" id="user-delete">
        <p><select name="user-select" id="user-select">
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
