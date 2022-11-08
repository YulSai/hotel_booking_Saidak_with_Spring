<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><spring:message code="msg.reservation.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.reservation.detail"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../../pagination.jsp"/>
    <tr>
        <th><spring:message code="msg.id"/></th>
        <th><spring:message code="msg.item"/></th>
        <th><spring:message code="msg.cost"/></th>
        <th><spring:message code="msg.status"/></th>
    </tr>
    <td><a
            href="/reservations/${requestScope.reservation.id}">${requestScope.reservation.id}</a>
    </td>
    <td>
        <table>
            <tr>
                <th><spring:message code="msg.reservation.room"/></th>
                <th><spring:message code="msg.type"/></th>
                <th><spring:message code="msg.capacity"/></th>
                <th><spring:message code="msg.checkIn"/></th>
                <th><spring:message code="msg.checkOut"/></th>
                <th><spring:message code="msg.reservation.nights"/></th>
                <th><spring:message code="msg.price"/>USD</th>
                <th><spring:message code="msg.reservation.calc"/></th>

            </tr>
            <c:forEach items="${requestScope.reservation.details }" var="info">
                <tr>
                    <td><a href="/rooms/${info.room.id}">${info.room.number}</a></td>
                    <td>${info.room.type}</td>
                    <td>${info.room.capacity}</td>
                    <td>${info.checkIn}</td>
                    <td>${info.checkOut}</td>
                    <td>${info.nights}</td>
                    <td>${info.roomPrice}</td>
                    <td>${info.room.price} x ${info.nights}</td>
                </tr>
            </c:forEach>
        </table>
    </td>
    <td>${requestScope.reservation.totalCost} USD</td>
    <td>${requestScope.reservation.status.toString().toLowerCase()}</td>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <td><a class="btn" href="/reservations/update/${requestScope.reservation.id}"> <spring:message code="msg.update"/></a>
        </td>
    </c:if>
    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <td><a class="btn" href="/reservations/cancel_reservation/${requestScope.reservation.id}">
            <spring:message code="msg.cancel"/></a></td>
    </c:if>
</table>
</body>
</html>