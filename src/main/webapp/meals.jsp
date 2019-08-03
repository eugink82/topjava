<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Список еды</title>
</head>
<body>
<section>
    <h2><a href="index.jsp">Home</a></h2>
    <hr>
    <h2>Meals</h2>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>От даты:</dt>
            <dd><input type="date" name="startDt" value="${param.startDt}"></dd>
        </dl>
        <dl>
            <dt>До даты:</dt>
            <dd><input type="date" name="endDt" value="${param.endDt}"></dd>
        </dl>
        <dl>
            <dt>От времени:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>До времени:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
    <hr>
    <h3><a href="meals?action=create">Добавить еду</a></h3>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата/время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'exceed' : 'normal'}">
                <td><%=DateTimeUtil.toString(meal.getDateTime())%>
                </td>
                    <%-- <td> ${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()} </td> --%>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
                <td><a href="meals?action=update&id=${meal.id}">Изменить</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>
