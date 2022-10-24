<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><fmt:message key="msg.update.user.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.update.user.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/users/update/${requestScope.user.id}" enctype="multipart/form-data">
    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <label for="first_name-input"><fmt:message key="msg.create.user.first.name"/></label>
        <input id="first_name-input" name="firstName" type="text" value="${requestScope.user.firstName}"/>
        <br/>
        <label for="last_name-input"><fmt:message key="msg.create.user.last.name"/></label>
        <input id="last_name-input" name="lastName" type="text" value="${requestScope.user.lastName}"/>
        <br/>
        <label for="email-input"><fmt:message key="msg.create.user.email"/></label>
        <input id="email-input" name="email" type="email" value="<c:out value="${requestScope.user.email}"/>"/>
        <br/>
        <input id="password-input" name="password" type="hidden" min="6" value="${requestScope.user.password}"/>
        <label for="phone_number-input"><fmt:message key="msg.create.user.phone"/></label>
        <input id="phone_number-input" name="phoneNumber" type="tel" min="13"
               value="${requestScope.user.phoneNumber}"/>
        <br/>
        <label for="avatar_input"><fmt:message key="msg.user.avatar"/></label>
        <input id="avatar_input" name="avatar" type="hidden" value="${requestScope.user.avatar}"/>
        <input id="avatar_input" name="avatarFile" type="file" accept="image/*"/>
        <br/>
        <input id="role-input-admin" name="role" type="hidden" value="${user.role}"/>
    </c:if>
    <input type="submit" value="<fmt:message key="msg.update.user.save"/>"/>
</form>
</body>
</html>