<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
    <style>
        dl {
            background: none repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }

        dt {
            display: inline-block;
            width: 170px;
        }

        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<section>
    <h2><a href="index.html">Home</a></h2>
    <h3>Редактирование еды</h3>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Дата/время:</dt>
            <dd><input type="datetime-local" name="dateTime" value="${meal.dateTime}"> </dd>
        </dl>
        <dl>
            <dt>Описание:</dt>
            <dd><input type="text" name="description" size=40 value="${meal.description}"> </dd>
        </dl>
        <dl>
            <dt>Калории:</dt>
            <dd><input type="number" name="calories" size=40 value="${meal.calories}"> </dd>
        </dl>
        <hr>
        <button type="submit">Сохранить</button>
        <button onсlick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>
