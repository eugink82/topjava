<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="topjava" tagdir="/WEB-INF/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron pt-4">
    <div class="container">
        <div class="row">
            <div class="col-5 offset-3">
                <h3>${userTo.name} <spring:message code="app.profile"/></h3>
                <form:form class="form-group" modelAttribute="userTo" method="post" action="profile" charset="utf-8"
                           acceptCharset="UTF-8">
                    <topjava:inputField name="name" labelCode="user.name"/>
                    <topjava:inputField name="email" labelCode="user.email"/>
                    <topjava:inputField name="password" labelCode="user.password" inputType="password"/>
                    <topjava:inputField name="caloriesPerDay" labelCode="user.caloriesPerDay" inputType="number"/>
                    <div class="text-right">
                    <a class="btn btn-secondary" href="#" onclick="window.history.back()">
                        <span class="fa fa-close"></span>
                        <spring:message code="common.cancel"/>
                    </a>
                    <button type="submit" class="btn btn-primary">
                       <span class="fa fa-check"></span>
                        <spring:message code="common.save"/>
                    </button>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footerTag.jsp"/>
</body>
</html>