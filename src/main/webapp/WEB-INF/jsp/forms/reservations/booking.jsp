<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.booking.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1 id="title"><spring:message code="msg.booking.your"/></h1>
<c:if test="${requestScope.booking == null}">
    <h2><spring:message code="msg.booking.no.booking"/></h2>
</c:if>
<c:if test="${requestScope.booking != null}">
    <table>
        <tr>
            <th><spring:message code="msg.items"/></th>
            <th><spring:message code="msg.price"/>USD</th>
            <th><spring:message code="msg.booking.checkIn"/></th>
            <th><spring:message code="msg.booking.checkOut"/></th>
            <th><spring:message code="msg.status"/></th>
        </tr>
        <c:forEach items="${requestScope.booking.details}" var="item">
            <tr>
                <td><a href="/rooms/${item.room.id}">${item.room.number}</a></td>
                <td>${item.room.price}</td>
                <td>${item.checkIn}</td>
                <td>${item.checkOut}</td>
                <td>${requestScope.booking.status}</td>
                <td><a href="/reservations/delete_booking/${item.room.id}">
                    <input type="submit" value="<spring:message code="msg.delete"/>"></a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3"><spring:message code="msg.cost"/>${requestScope.booking.totalCost} USD</td>
        </tr>

    </table>
    <a href="/reservations/create"><input type="submit" value="<spring:message code="msg.booking.reserve"/>"></a>
    <a href="/reservations/clean_booking"><input type="submit" value="<spring:message code="msg.booking.clean"/>"></a>
</c:if>
</body>
</html>