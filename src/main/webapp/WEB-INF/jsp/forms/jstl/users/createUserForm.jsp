<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.create.new.user.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<p>${requestScope.message}</p>
<h1><spring:message code="msg.create.new.user.title"/></h1>
<form:form action="/users/create" enctype="multipart/form-data" method="post" modelAttribute="userDto">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <form:errors path="username" cssClass="error-block"/>
    <form:label path="username" for="username-input"><spring:message code="msg.create.user.username"/></form:label>
    <form:input id="username-input" path="username" type="text"/>
    <br/>
    <form:errors path="password" cssClass="error-block"/>
    <spring:message code="msg.create.user.password.min" var="placeholder_password"/>
    <form:label path="password" for="password-input"><spring:message code="msg.create.user.password"/></form:label>
    <form:input id="password-input" path="password" type="password" min="6" placeholder='${placeholder_password}'/>
    <br/>
    <form:errors path="firstName" cssClass="error-block"/>
    <form:label path="firstName" for="first_name-input"><spring:message code="msg.create.user.first.name"/></form:label>
    <form:input id="first_name-input" path="firstName" type="text"/>
    <br/>
    <form:errors path="lastName" cssClass="error-block"/>
    <form:label path="lastName" for="last_name-input"><spring:message code="msg.create.user.last.name"/></form:label>
    <form:input id="last_name-input" path="lastName" type="text"/>
    <br/>
    <form:errors path="email" cssClass="error-block"/>
    <form:label path="email" for="email-input"><spring:message code="msg.create.user.email"/></form:label>
    <form:input id="email-input" path="email" type="email"/>
    <br/>
    <form:errors path="phoneNumber" cssClass="error-block"/>
    <spring:message code="msg.create.user.phone.format" var="placeholder_phone_number"/>
    <form:label path="phoneNumber" for="phone_number-input"><spring:message code="msg.create.user.phone"/></form:label>
    <form:input id="phone_number-input" path="phoneNumber" type="tel" min="10"
           placeholder='${placeholder_phone_number}'/>
    <br/>
    <label for="avatar_input"><spring:message code="msg.user.avatar"/></label>
    <input id="avatar_input" name="avatarFile" type="file" accept="image/*"/>
    <br/>
    <form:button class="btn"><spring:message code="msg.user.register"/></form:button>
    <button class="btn" type="button"><a href="/}"><spring:message code="msg.cancel"/></a></button>
</form:form>
</body>
</html>