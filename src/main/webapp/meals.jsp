<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css"/>
    <title>Title</title>
</head>
<body>
 <section>
   <table border="1" cellpadding="8" cellspacing="0">
      <tr>
       <th>LocalDateTime</th>
       <th>description</th>
       <th>calories</th>
       <th></th>
       <th></th>
      </tr>
       <c:forEach items="${mealsTo}" var="meal">
           <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
               <tr data-mealExcess="${meal.excess}">
                   <td><%=meal.getDateTime().toLocalDate()+" "+meal.getDateTime().toLocalTime()%></td>
                   <td>${meal.description}</td>
                   <td>${meal.calories}</td>
                   <td><a href="meals?id=${meal.id}&action=delete"><img src="img/delete.png"></a></td>
                   <td><a href="meals?uuid=${meal.id}&action=edit"><img src="img/pencil.png"></a></td>
               </tr>
       </c:forEach>
   </table>
 </section>
</body>
</html>


