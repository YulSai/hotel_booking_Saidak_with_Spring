<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/tables.css">
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <title><fmt:message key="msg.user"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.user.title"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <tr><img src="images/avatars/${requestScope.user.avatar}" alt="${requestScope.user.avatar}" class="avatar"></tr>
    <tr>
        <th><fmt:message key="msg.field"/></th>
        <th><fmt:message key="msg.value"/></th>
    </tr>
    <tr>
        <td><fmt:message key="msg.user.first.name"/></td>
        <td>${requestScope.user.firstName}</td>
    </tr>
    <tr>
        <td><fmt:message key="msg.user.last.name"/></td>
        <td>${requestScope.user.lastName}</td>
    </tr>
    <tr>
        <td><fmt:message key="msg.user.email"/></td>
        <td>${requestScope.user.email}</td>
    </tr>
    <tr>
        <td><fmt:message key="msg.user.phone"/></td>
        <td><c:out value="${requestScope.user.phoneNumber}"/></td>
    </tr>
    <tr>
        <td><fmt:message key="msg.user.role"/></td>
        <td>${requestScope.user.role.toString().toLowerCase()}</td>
    </tr>
</table>
<ul>
    <li><a href="controller?command=update_user_form&id=${requestScope.user.id}">
        <fmt:message key="msg.user.update"/></a></li>
    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <li><a href="controller?command=change_password_form&id=${requestScope.user.id}"> <fmt:message
                key="msg.user.change.password"/></a></li>
    </c:if>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <li><a href="controller?command=delete_user&id=${requestScope.user.id}"> <fmt:message
                key="msg.user.delete"/></a></li>
    </c:if>
</ul>
</body>
</html>