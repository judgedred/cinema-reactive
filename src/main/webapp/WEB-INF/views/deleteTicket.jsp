<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Delete Ticket</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#ticket").submit(function(event) {
                $.ajax({
                    url: "checkTicket/" + $("#ticketId").val(),
                    async: false,
                    success: function(data) {
                        if(data != "") {
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

    <p>Удалить билет</p>

    <c:if test="${!empty ticketList}">
        <form:form action="deleteTicket" modelAttribute="ticket">
            <p>
                <form:label path="ticketId">Билет</form:label>
                <form:select path="ticketId">
                    <form:option value="0" label="Выберите билет"/>
                    <form:options items="${ticketList}" itemLabel="description" itemValue="ticketId"/>
                </form:select>
            </p>
            <p><input type="submit" value="Удалить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
