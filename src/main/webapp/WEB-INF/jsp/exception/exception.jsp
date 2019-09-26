<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="../fragments/headTag.jsp"/>
<body>
<jsp:include page="../fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container text-center">
        <h2>${typeMessage}</h2>
        <h3>${message}</h3>
    </div>
</div>

<!--
<c:forEach items="${exception.stackTrace}" var="stackError">
    ${stackError}
</c:forEach>
-->
<jsp:include page="../fragments/footerTag.jsp"/>
</body>
</html>
