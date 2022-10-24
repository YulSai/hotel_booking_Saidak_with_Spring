<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><fmt:message key="msg.reservation.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.reservation.detail"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th><fmt:message key="msg.id"/></th>
        <th><fmt:message key="msg.item"/></th>
        <th><fmt:message key="msg.cost"/></th>
        <th><fmt:message key="msg.status"/></th>
    </tr>
    <td><a
            href="/reservations/${requestScope.reservation.id}">${requestScope.reservation.id}</a>
    </td>
    <td>
        <table>
            <tr>
                <th><fmt:message key="msg.reservation.room"/></th>
                <th><fmt:message key="msg.type"/></th>
                <th><fmt:message key="msg.capacity"/></th>
                <th><fmt:message key="msg.checkIn"/></th>
                <th><fmt:message key="msg.checkOut"/></th>
                <th><fmt:message key="msg.reservation.nights"/></th>
                <th><fmt:message key="msg.price"/>USD</th>
                <th><fmt:message key="msg.reservation.calc"/></th>

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
        <td><a href="/reservations/update/${requestScope.reservation.id}"> <fmt:message key="msg.update"/></a>
        </td>
    </c:if>
    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <td><a href="/reservations/cancel_reservation/${requestScope.reservation.id}">
            <fmt:message key="msg.cancel"/></a></td>
    </c:if>
</table>
</body>
</html>