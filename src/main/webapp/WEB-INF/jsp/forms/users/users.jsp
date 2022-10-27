<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><spring:message code="msg.users.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th><spring:message code="msg.user.first.name"/></th>
        <th><spring:message code="msg.user.last.name"/></th>
        <th><spring:message code="msg.user.email"/></th>
        <th><spring:message code="msg.user.phone"/></th>
        <th><spring:message code="msg.user.role"/></th>
    </tr>
    <c:forEach items="${requestScope.users}" var="user" varStatus="counter">
        <tr>
            <td>${counter.count}</td>
            <td><a href="/users/${user.id}">${user.firstName}</a></td>
            <td><a href="/users/${user.id}">${user.lastName}</a></td>
            <td><c:out value="${user.email}"/></td>
            <td>${user.phoneNumber}</td>
            <td>${user.role.toString().toLowerCase()}</td>
            <td>
                <li><a href="/users/update_role/${user.id}"><spring:message code="msg.user.update.role"/></a>
                </li>
            </td>
            <td>
                <li><a href="/users/delete/${user.id}"><spring:message code="msg.user.delete"/></a></li>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>