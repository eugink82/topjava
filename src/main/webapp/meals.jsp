<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <style>
        .normal{
            color: green;
        }
        .exceed {
            color: red;
        }
    </style>
    <title>Список еды</title>
</head>
<body>
<section>
   <h2><a href="index.html">Home</a></h2>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Дата/время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
            <c:forEach items="${meals}" var="meal">
                <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
                <tr class="${meal.excess ? 'exceed' : 'normal'}">
                    <td><%=DateTimeUtil.toString(meal.getDateTime())%></td>
                    <td>${meal.description}</td>
                    <td>${meal.calories}</td>
                </tr>
            </c:forEach>
    </table>
</section>
</body>
</html>
