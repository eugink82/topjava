<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<fmt:setBundle basename="messages.app"/>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyTag.jsp"/>
<form method="post" action="users">
    <fmt:message key="app.login"/>
    <select name="userId">
        <option value="100000" selected>User</option>
        <option value="100001">Admin</option>
    </select>
    <button type="submit"><fmt:message key="app.select"/></button>
</form>
<hr>
<jsp:include page="fragments/footerTag.jsp"/>
</body>
</html>
