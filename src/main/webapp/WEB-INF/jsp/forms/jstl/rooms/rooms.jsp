<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><spring:message code="msg.rooms.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th><spring:message code="msg.number"/></th>
        <th><spring:message code="msg.type"/></th>
        <th><spring:message code="msg.capacity"/></th>
        <th><spring:message code="msg.status"/></th>
        <th><spring:message code="msg.price"/>USD</th>
        <th><spring:message code="msg.action"/></th>
    </tr>
    <c:forEach items="${rooms}" var="room" varStatus="counter">
        <tr>
            <td>${counter.count}</td>
            <td><a href="/rooms/${room.id}">${room.number}</a></td>
            <td>${room.type}</td>
            <td>${room.capacity}</td>
            <td>${room.status}</td>
            <td>${room.price}</td>
            <td><a href="/rooms/update/${room.id}"><spring:message code="msg.update"/></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>