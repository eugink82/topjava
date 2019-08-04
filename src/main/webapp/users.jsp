<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages/app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyTag.jsp"/>
<h3><fmt:message key="user.title"/></h3>
<section>
    <table border="1" cellspacing="0" cellpadding="8">
      <tr>
        <th><fmt:message key="user.name"/></th>
        <th><fmt:message key="user.email"/></th>
        <th><fmt:message key="user.roles"/></th>
        <th><fmt:message key="user.enabled"/></th>
        <th><fmt:message key="user.registered"/></th>
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
