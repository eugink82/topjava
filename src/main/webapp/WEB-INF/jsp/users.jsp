<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyTag.jsp"/>
<h3><spring:message code="user.title"/></h3>
<section>
    <table border="1" cellspacing="0" cellpadding="8">
      <tr>
        <th><spring:message code="user.name"/></th>
        <th><spring:message code="user.email"/></th>
        <th><spring:message code="user.roles"/></th>
        <th><spring:message code="user.active"/></th>
        <th><spring:message code="user.registered"/></th>
      </tr>
        <c:forEach items="${users}" var="user">
            <jsp:useBean id="user" type="ru.javawebinar.topjava.model.User"/>
            <tr>
                <td>${user.name}</td>
                <td><a href="mailto:${user.email}">${user.email}</a></td>
                <td>${user.roles}</td>
                <td>${user.enabled}</td>
                <td><fmt:formatDate value="${user.registered}" pattern="dd-MM-yyyy"/></td>
           </tr>
        </c:forEach>
    </table>
</section>
<hr>
<jsp:include page="fragments/footerTag.jsp"/>
</body>
</html>
