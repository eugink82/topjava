<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo" scope="request"/>
    <title>Meal</title>
</head>
<body>
<section>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Дата/время:</dt>
            <dd><input type="text" name="dateTime" size=25 value="${meal.dateTime}"> </dd>
        </dl>
        <dl>
            <dt>Описание:</dt>
            <dd><input type="text" name="description" size=40 value="${meal.description}"> </dd>
        </dl>
        <dl>
            <dt>Калории:</dt>
            <dd><input type="text" name="calories" size=40 value="${meal.calories}"> </dd>
        </dl>
        <hr>
        <button type="submit">Сохранить</button>
        <button onсlick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>
