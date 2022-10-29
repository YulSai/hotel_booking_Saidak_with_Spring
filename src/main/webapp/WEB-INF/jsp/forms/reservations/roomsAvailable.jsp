<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><spring:message code="msg.available.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1 id="title"><spring:message code="msg.available.detail"/></h1>
<h2>${sessionScope.check_in} - ${sessionScope.check_out}</h2>
<p>${requestScope.message}</p>
<table>
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th><spring:message code="msg.number"/></th>
        <th><spring:message code="msg.type"/></th>
        <th><spring:message code="msg.capacity"/></th>
        <th><spring:message code="msg.status"/></th>
        <th><spring:message code="msg.price"/>USD</th>
        <th><spring:message code="msg.action"/></th>
    </tr>
    <c:forEach items="${sessionScope.rooms_available}" var="room" varStatus="counter">
        <tr>
            <td><a href="/rooms/${room.id}">${counter.count}</a></td>
            <td>${room.number}</td>
            <td>${room.type}</td>
            <td>${room.capacity}</td>
            <td>${room.status}</td>
            <td>${room.price}</td>
            <td>
                <form method="post" action="/reservations/add_booking">
                    <input type="hidden" name="roomId" value="${room.id}">
                    <input type="hidden" name="check_in" value="${check_in}">
                    <input type="hidden" name="check_out" value="${check_out}">
                    <input type="submit" value="<spring:message code="msg.available.book"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>