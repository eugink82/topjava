<%@ page isErrorPage="true" contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="../fragments/headTag.jsp"/>

<body>
<jsp:include page="../fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container text-center">
        <br>
        <h2><spring:message code="common.appError"/></h2>
        <h3>
           <c:choose>
            <c:when test="${message=='user.duplicationEmail'}">
                <spring:message code="${message}"/>
            </c:when>
               <c:otherwise>
                   ${message}
               </c:otherwise>
            </c:choose>
        </h3>
    </div>
</div>
<!--
<c:forEach items="${exception.stackTrace}" var="stackTrace">
    ${stackTrace}
</c:forEach>
-->
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>