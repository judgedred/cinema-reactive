<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=utf-8" %>


<html>
<head>
    <title>Delete Film</title>
    <link rel="stylesheet" href="../resources/css/styles.css"/>
    <script type="text/javascript" src="../resources/js/jquery-2.1.4.js"></script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#film").submit(function (event) {
                $.ajax({
                    url: "filmCheck/" + $("#filmId").val(),
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

    <p>Удалить фильм</p>

    <c:if test="${!empty filmList}">
        <form:form action="deleteFilm" modelAttribute="film">
            <p>
                <form:label path="filmId">Фильм</form:label>
                <form:select path="filmId">
                    <form:option value="0" label="Выберите фильм"/>
                    <form:options items="${filmList}" itemValue="filmId" />
                </form:select>
            </p>
            <p><input type="submit" value="Удалить"></p>
        </form:form>
    </c:if>

</div>
</body>
</html>
