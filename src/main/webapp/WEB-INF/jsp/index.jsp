<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyTag.jsp"/>
<form method="post" action="users">
    <spring:message code="app.login"/>
    <select name="userId">
        <option value="100000" selected>User</option>
        <option value="100001">Admin</option>
    </select>
    <button type="submit"><spring:message code="app.select"/></button>
</form>
<hr>
<jsp:include page="fragments/footerTag.jsp"/>
</body>
</html>
