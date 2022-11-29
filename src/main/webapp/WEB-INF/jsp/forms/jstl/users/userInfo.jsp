<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <meta name="_csrf_token" content="${_csrf.token}"/>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <title><spring:message code="msg.user"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.user.title"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <tr><img src="/images/avatars/${requestScope.user.avatar}" alt="${requestScope.user.avatar}" class="avatar"></tr>
    <tr>
        <th><spring:message code="msg.field"/></th>
        <th><spring:message code="msg.value"/></th>
    </tr>
    <tr>
        <td><spring:message code="msg.user.first.name"/></td>
        <td>${requestScope.user.firstName}</td>
    </tr>
    <tr>
        <td><spring:message code="msg.user.last.name"/></td>
        <td>${requestScope.user.lastName}</td>
    </tr>
    <tr>
        <td><spring:message code="msg.user.email"/></td>
        <td>${requestScope.user.email}</td>
    </tr>
    <tr>
        <td><spring:message code="msg.user.phone"/></td>
        <td><c:out value="${requestScope.user.phoneNumber}"/></td>
    </tr>
    <tr>
        <td><spring:message code="msg.user.role"/></td>
        <td>${requestScope.user.role.toString().toLowerCase()}</td>
    </tr>
    <tr>
        <td><spring:message code="msg.user.status"/></td>
        <c:if test="${requestScope.user.block == true}">
        <td><spring:message code="msg.user.status.textB"/></td>
        </c:if>
        <c:if test="${requestScope.user.block == false}">
        <td><spring:message code="msg.user.status.textA"/></td>
        </c:if>
    </tr>

</table>
<ul>
    <sec:authorize access="hasRole('ROLE_CLIENT')">
        <li><a class="btn" href="/users/update/${user.id}">
            <spring:message code="msg.user.update"/></a></li>
        <li><a class="btn" href="/users/change_password/${user.id}"> <spring:message
                code="msg.user.change.password"/></a></li>
    </sec:authorize>
    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li><a class="btn" href="/users/update_role/${user.id}">
            <spring:message code="msg.user.update.role"/></a></li>
        <li><a class="btn" href="/users/delete/${user.id}"> <spring:message code="msg.user.delete"/></a></li>
    </sec:authorize>
</ul>
</body>
</html>