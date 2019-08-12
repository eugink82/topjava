<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<body>
 <jsp:include page="fragments/bodyTag.jsp"/>
<section>
    <h2><a href="${pageContext.request.contextPath}/"><spring:message code="app.home"/></a></h2>
    <hr>
    <h2><spring:message code="meal.title"/></h2>
    <form method="post" action="meals/filter">
        <dl>
            <dt><spring:message code="meal.startDate"/>:</dt>
            <dd><input type="date" name="startDt" value="${param.startDt}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endDate"/>:</dt>
            <dd><input type="date" name="endDt" value="${param.endDt}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.startTime"/>:</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.endTime"/>:</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit"><spring:message code="meal.filter"/></button>
    </form>
    <hr>
    <h3><a href="meals/create"><spring:message code="meal.add"/></a></h3>
    <hr>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th><spring:message code="meal.dateTime"/></th>
            <th><spring:message code="meal.description"/></th>
            <th><spring:message code="meal.calories"/></th>
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
                <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
                <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footerTag.jsp"/>
</body>
</html>
