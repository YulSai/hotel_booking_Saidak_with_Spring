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
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><spring:message code="msg.reservations.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.reservations.title"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../../pagination.jsp"/>
    <c:if test="${requestScope.rooms} != null">
    <tr>
        <th>#</th>
        <th><spring:message code="msg.user"/></th>
        <th><spring:message code="msg.items"/></th>
        <th><spring:message code="msg.status"/></th>
        <th><spring:message code="msg.action"/></th>
    </tr>
    </c:if>
    <c:forEach items="${requestScope.reservations}" var="reservation" varStatus="counter">
        <tr>
            <td><a href="/reservations/${reservation.id}">${counter.count}</a></td>
            <td>
                <a href="/users/${reservation.user.id}">${reservation.user.email}</a>
            </td>
            <td>
                <table class="second">
                    <c:forEach items="${requestScope.reservation.details }" var="info">
                        <tr><a href="/rooms/${info.room.id}">${info.room.number}</a></tr>
                        <tr>${info.checkIn} </tr>
                        <tr>${info.checkOut} </tr>
                    </c:forEach>
                    <spring:message code="msg.cost"/>${reservation.totalCost} USD
                </table>
            <td>${reservation.status.toString().toLowerCase()}</td>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <td><a class="btn" href="/reservations/update/${reservation.id}"> <spring:message code="msg.reservations.update"/></a>
                </td>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_CLIENT')">
                <td><a class="btn" href="/reservations/cancel_reservation/${reservation.id}"> <spring:message code="msg.cancel"/></a>
                </td>
            </sec:authorize>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>