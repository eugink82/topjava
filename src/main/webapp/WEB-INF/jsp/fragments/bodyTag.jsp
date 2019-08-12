<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header>
    <a href="meals"><spring:message code="app.title"/></a> | <a href="users"><spring:message code="user.title"/> </a>
    | <a href="${pageContext.request.contextPath}"><spring:message code="app.home"/> </a>
</header>
