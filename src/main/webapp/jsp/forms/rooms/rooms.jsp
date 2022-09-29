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
    <title><fmt:message key="msg.rooms.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th><fmt:message key="msg.number"/></th>
        <th><fmt:message key="msg.type"/></th>
        <th><fmt:message key="msg.capacity"/></th>
        <th><fmt:message key="msg.status"/></th>
        <th><fmt:message key="msg.price"/>USD</th>
        <th><fmt:message key="msg.action"/></th>
    </tr>
    <c:forEach items="${rooms}" var="room" varStatus="counter">
        <tr>
            <td><a href="controller?command=room&id=${room.id}">${counter.count}</a></td>
            <td>${room.number}</td>
            <td>${room.type}</td>
            <td>${room.capacity}</td>
            <td>${room.status}</td>
            <td>${room.price}</td>
            <td><a href="controller?command=update_room_form&id=${room.id}"><fmt:message
                    key="msg.update"/></a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>