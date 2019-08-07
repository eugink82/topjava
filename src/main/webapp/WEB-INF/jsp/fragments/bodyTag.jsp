<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages/app"/>
<header>
    <a href="meals"><fmt:message key="app.name"/></a> | <a href="users"><fmt:message key="user.title"/> </a>
    | <a href="${pageContext.request.contextPath}"><fmt:message key="app.main"/> </a>
</header>
