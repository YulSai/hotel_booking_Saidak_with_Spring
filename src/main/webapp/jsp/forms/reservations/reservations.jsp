<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/tables.css">
    <title><fmt:message key="msg.reservations.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.reservations.title"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <jsp:include page="../pagination.jsp"/>
    <tr>
        <th>#</th>
        <th><fmt:message key="msg.user"/></th>
        <th><fmt:message key="msg.items"/></th>
        <th><fmt:message key="msg.status"/></th>
        <th><fmt:message key="msg.action"/></th>
    </tr>
    <c:forEach items="${requestScope.reservations}" var="reservation" varStatus="counter">
        <tr>
            <td><a href="controller?command=reservation&id=${reservation.id}">${counter.count}</a></td>
            <td>
                <a href="controller?command=user&id=${reservation.user.id}">${reservation.user.email}</a>
            </td>
            <td>
                <table class="second">
                    <c:forEach items="${requestScope.reservation.details }" var="info">
                        <tr><a href="controller?command=room&id=${info.room.id}">${info.room.number}</a></tr>
                        <tr>${info.checkIn} </tr>
                        <tr>${info.checkOut} </tr>
                    </c:forEach>
                    <fmt:message key="msg.cost"/>${reservation.totalCost} USD
                </table>
            <td>${reservation.status.toString().toLowerCase()}</td>
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <td><a href="controller?command=update_reservation_form&id=${reservation.id}">
                    <fmt:message key="msg.reservations.update"/></a></td>
            </c:if>
            <c:if test="${sessionScope.user.role == 'CLIENT'}">
                <td><a href="controller?command=cancel_reservation&id=${reservation.id}">
                    <fmt:message key="msg.cancel"/></a></td>
            </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>