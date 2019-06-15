<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <link rel="stylesheet" href="css/style.css"/>
    <title>Title</title>
</head>
<body>
 <section>
   <h2><a href="index.html">Home</a></h2>
   <h3>Список еды</h3>
     <h2><a href="meals?action=create">Добавить еду</a></h2>
     <hr>
   <table border="1" cellpadding="8" cellspacing="0">
      <tr>
       <th>Date</th>
       <th>Description</th>
       <th>Calories</th>
       <th></th>
       <th></th>
      </tr>
       <c:forEach items="${meals}" var="meal">
           <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
               <tr data-mealExcess="${meal.excess}">
                   <td><%=meal.getDateTime().toLocalDate()+" "+meal.getDateTime().toLocalTime()%></td>
                   <td>${meal.description}</td>
                   <td>${meal.calories}</td>
                   <td><a href="meals?id=${meal.id}&action=delete"><img src="img/delete.png"></a></td>
                   <td><a href="meals?id=${meal.id}&action=update"><img src="img/pencil.png"></a></td>
               </tr>
       </c:forEach>
   </table>
 </section>
</body>
</html>


