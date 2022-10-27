<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.update.user.role.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><spring:message code="msg.update.user.role.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/users/update_role/${requestScope.user.id}" enctype="multipart/form-data">
        <input id="first_name-input" name="firstName" type="hidden" value="${requestScope.user.firstName}"/>
        <input id="last_name-input" name="lastName" type="hidden" value="${requestScope.user.lastName}"/>
        <input id="email-input" name="email" type="hidden" value="<c:out value="${requestScope.user.email}"/>"/>
        <input id="password-input" name="password" type="hidden" min="6" value="${requestScope.user.password}"/>
        <input id="phone_number-input" name="phoneNumber" type="hidden" min="13"
               value="${requestScope.user.phoneNumber}"/>
        <input id="avatar_input" name="avatar" type="hidden" value="${requestScope.user.avatar}"/>
        <input id="role-input-admin" name="role" type="radio"
               value="ADMIN" ${requestScope.user.role=='ADMIN' ? 'checked' : ''}/>
        <label for="role-input-admin"><spring:message code="msg.update.user.admin"/></label>
        <input id="role-input-client" name="role" type="radio"
               value="CLIENT" ${requestScope.user.role=='CLIENT' ? 'checked' : ''}/>
        <label for="role-input-client"><spring:message code="msg.update.user.client"/></label>
        <br/>
    <input type="submit" value="<spring:message code="msg.update.user.save"/>"/>
</form>
</body>
</html>