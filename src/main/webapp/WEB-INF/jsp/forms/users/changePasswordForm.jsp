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
<form method="post" action="/users/change_password/${user.id}">
    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <input id="first_name-input" name="firstName" type="hidden" value="${requestScope.user.firstName}"/>
        <input id="last_name-input" name="lastName" type="hidden" value="${requestScope.user.lastName}"/>
        <input id="email-input" name="email" type="hidden" value="<c:out value="${requestScope.user.email}"/>"/>
        <input id="phone_number-input" name="phoneNumber" type="hidden" min="13"
               value="${requestScope.user.phoneNumber}"/>
        <input id="avatar_input" name="avatar" type="hidden" value="${requestScope.user.avatar}"/>
        <input id="role-input-admin" name="role" type="hidden" value="${requestScope.user.role}"/>
        <label for="password-input"><fmt:message key="msg.create.user.password"/></label>
        <input id="password-input" name="password" type="password" min="6" value="${requestScope.user.password}"/>
    </c:if>
    <input type="submit" value="<fmt:message key="msg.update.user.save"/>"/>
</form>
</body>
</html>