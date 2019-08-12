<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <jsp:include page="fragments/headTag.jsp"/>
</head>
<body>
<jsp:include page="fragments/bodyTag.jsp"/>
<section>
    <h2><a href="${pageContext.request.contextPath}/"><spring:message code="app.home"/></a></h2>
    <hr>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <h3><spring:message code="${meal.isNew() ? 'meal.add' : 'meal.edit'}"/></h3>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt><spring:message code="meal.dateTime"/></dt>
            <dd>
                <input type="datetime-local" name="dateTime" value="${meal.dateTime}" required>
            </dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.description"/></dt>
            <dd>
                <input type="text" name="description" value="${meal.description}" required>
            </dd>
        </dl>
        <dl>
            <dt><spring:message code="meal.calories"/></dt>
            <dd>
                <input type="number" name="calories" value="${meal.calories}" required>
            </dd>
        </dl>
        <button type="submit"><spring:message code="common.save"/></button>
        <button type="button" onclick="window.history.back()"><spring:message code="common.cancel"/></button>
    </form>
</section>
<jsp:include page="fragments/footerTag.jsp"/>
</body>
</html>
