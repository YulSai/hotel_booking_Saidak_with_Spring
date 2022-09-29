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
    <title><fmt:message key="msg.users.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th><fmt:message key="msg.user.first.name"/></th>
        <th><fmt:message key="msg.user.last.name"/></th>
        <th><fmt:message key="msg.user.email"/></th>
        <th><fmt:message key="msg.user.phone"/></th>
        <th><fmt:message key="msg.user.role"/></th>
    </tr>
    <c:forEach items="${requestScope.users}" var="user" varStatus="counter">
        <tr>
            <td>${counter.count}</td>
            <td><a href="controller?command=user&id=${user.id}">${user.firstName}</a></td>
            <td><a href="controller?command=user&id=${user.id}">${user.lastName}</a></td>
            <td><c:out value="${user.email}"/></td>
            <td>${user.phoneNumber}</td>
            <td>${user.role.toString().toLowerCase()}</td>
            <td>
                <li><a href="controller?command=update_user_form&id=${user.id}"><fmt:message key="msg.user.update"/></a>
                </li>
            </td>
            <td>
                <li><a href="controller?command=delete_user&id=${user.id}"><fmt:message key="msg.user.delete"/></a></li>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>