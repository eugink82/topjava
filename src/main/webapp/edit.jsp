<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css">
    <title>Title</title>
</head>
<body>
<section>
    <h2><a href="index.jsp">Home</a></h2>
    <hr>
    <h3>${param.action=='create' ? 'create' : 'update' }</h3>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Дата/время</dt>
            <dd>
                <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
            </dd>
        </dl>
        <dl>
            <dt>Описание</dt>
            <dd>
                <input type="text" name="description" value="${meal.description}" required>
            </dd>
        </dl>
        <dl>
            <dt>Калории</dt>
            <dd>
                <input type="number" name="calories" value="${meal.calories}" required>
            </dd>
        </dl>
        <button type="submit">Сохранить</button>
        <button type="button" onclick="window.history.back()">Отменить</button>
    </form>
</section>

</body>
</html>
