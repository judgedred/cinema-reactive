<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
	<title>LoginCheck</title>
</head>
<body>

	<%String loginCheck = (String)session.getAttribute("loginCheck");%>
			<p>Пользователь <%=loginCheck%> уже есть.</p>


</body>
</html>