<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Delete Seat</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#seat").submit(function (event) {
                $.ajax({
                    url: "checkSeat/" + $("#seatId").val(),
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

    <p>Удалить место</p>

    <c:if test="${!empty seatList}">
        <form:form action="deleteSeat" modelAttribute="seat">
            <p>
                <form:label path="seatId">Место</form:label>
                <form:select path="seatId">
                    <form:option value="0" label="Выберите место"/>
                    <form:options items="${seatList}" itemValue="seatId" />
                </form:select>
            </p>
            <p><input type="submit" value="Удалить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
