<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.update.user.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.update.user.title"/></h1>
<p>${requestScope.message}</p>
<form:form method="post" action="/users/update/${requestScope.user.id}"
           enctype="multipart/form-data" modelAttribute="userDto">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <sec:authorize access="hasRole('ROLE_CLIENT')">
        <form:errors path="firstName" cssClass="error-block"/>
        <form:label path="firstName" for="first_name-input"><spring:message code="msg.create.user.first.name"/></form:label>
        <form:input id="first_name-input" path="firstName" type="text" value="${requestScope.user.firstName}"/>
        <br/>
        <form:errors path="lastName" cssClass="error-block"/>
        <form:label path="lastName" for="last_name-input"><spring:message code="msg.create.user.last.name"/></form:label>
        <form:input id="last_name-input" path="lastName" type="text" value="${requestScope.user.lastName}"/>
        <br/>
        <form:errors path="email" cssClass="error-block"/>
        <form:label path="email" for="email-input"><spring:message code="msg.create.user.email"/></form:label>
        <form:input id="email-input" path="email" type="email" value="${requestScope.user.email}"/>
        <br/>
        <form:errors path="username" cssClass="error-block"/>
        <label for="username-input"><spring:message code="msg.create.user.username"/></label>
        <input id="username-input" name="username" type="text" value="<c:out value="${requestScope.user.username}"/>"/>
        <br/>
        <form:input id="password-input" path="password" type="hidden" min="6" value="${requestScope.user.password}"/>

        <form:errors path="phoneNumber" cssClass="error-block"/>
        <form:label path="phoneNumber" for="phone_number-input"><spring:message code="msg.create.user.phone"/></form:label>
        <form:input id="phone_number-input" path="phoneNumber" type="tel" min="13"
               value="${requestScope.user.phoneNumber}"/>
        <br/>
        <label for="avatar_input"><spring:message code="msg.user.avatar"/></label>
        <input id="avatar_input" name="avatar" type="hidden" value="${requestScope.user.avatar}"/>
        <input id="avatar_input" name="avatarFile" type="file" accept="image/*"/>
        <br/>
        <input id="role-input" name="role" type="hidden" value="${user.role}"/>
    </sec:authorize>
    <input type="submit" class="btn" value="<spring:message code="msg.update.user.save"/>"/>
    <button class="btn" type="button"><a href="/users/${requestScope.user.id}">
        <spring:message code="msg.cancel"/></a></button>
</form:form>
</body>
</html>