<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
<form:form method="post" action="/users/change_password/${user.id}" modelAttribute="userDto">
        <form:input id="first_name-input" path="firstName" type="hidden" value="${requestScope.user.firstName}"/>
        <form:input id="last_name-input" path="lastName" type="hidden" value="${requestScope.user.lastName}"/>
        <form:input id="email-input" path="email" type="hidden" value="${requestScope.user.email}"/>
        <input id="username-input" name="username" type="hidden" value="<c:out value="${requestScope.user.username}"/>"/>
        <form:input id="phone_number-input" path="phoneNumber" type="hidden" min="13"
                    value="${requestScope.user.phoneNumber}"/>
        <form:input id="avatar_input" path="avatar" type="hidden" value="${requestScope.user.avatar}"/>
        <form:input id="role-input-admin" path="role" type="hidden" value="${requestScope.user.role}"/>
        <form:errors path="password" cssClass="error-block"/>
        <form:label path="password" for="password-input"><spring:message code="msg.create.user.password"/></form:label>
        <form:input id="password-input" path="password" type="password" min="6"/>
    <input class="btn" type="submit" value="<spring:message code="msg.update.user.save"/>"/>
    <button class="btn" type="button"><a href="/users/${requestScope.user.id}">
        <spring:message code="msg.cancel"/></a></button>
</form:form>
</body>
</html>