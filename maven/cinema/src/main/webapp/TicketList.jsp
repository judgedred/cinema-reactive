<%@ page import="com.domain.Ticket" %>
<%@ page import="java.util.List" %>
<%@ page import="com.domain.Filmshow" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>TicketList</title>
    <link rel="stylesheet" href="resources/css/styles.css"/>
    <script type="text/javascript" src="resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript" src="resources/js/auth.js"></script>
</head>
<body>
<div class="wrapper">
    <jsp:include page="top.jsp"/>
<p>TicketList</p>
<form action="TicketList" method="Get" id="ticket-list">
    <p><label for="filmshow-select">Сеанс </label><select name="filmshow-select" id="filmshow-select">
        <option selected disabled>Выберите сеанс</option>
        <%
            List<Filmshow> filmshowLst = (List<Filmshow>)session.getAttribute("filmshowList");
            for(Filmshow f: filmshowLst)
            {
        %>
        <option value=<%=f.getFilmshowId()%>><%=f%></option>
        <%
            }
        %>
    </select></p>
    <p><input type="submit" value="Показать"></p>
</form>
<%
  List<Ticket> ls = (List<Ticket>)session.getAttribute("ticketList");
  if(ls != null)
  {
  for(Ticket t : ls)
  {
%>
<p><%=t%></p>
<%
  }
  }
%>
    </div>>
</body>
</html>
