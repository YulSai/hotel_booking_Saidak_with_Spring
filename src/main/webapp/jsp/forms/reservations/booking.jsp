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
    <title><fmt:message key="msg.booking.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1 id="title"><fmt:message key="msg.booking.your"/></h1>
<c:if test="${requestScope.booking == null}">
    <h2><fmt:message key="msg.booking.no.booking"/></h2>
</c:if>
<c:if test="${requestScope.booking != null}">
    <table>
        <tr>
            <th><fmt:message key="msg.items"/></th>
            <th><fmt:message key="msg.price"/>USD</th>
            <th><fmt:message key="msg.booking.checkIn"/></th>
            <th><fmt:message key="msg.booking.checkOut"/></th>
            <th><fmt:message key="msg.status"/></th>
        </tr>
        <c:forEach items="${requestScope.booking.details}" var="item">
            <tr>
                <td><a href="controller?command=room&id=${item.room.id}">${item.room.number}</a></td>
                <td>${item.room.price}</td>
                <td>${item.checkIn}</td>
                <td>${item.checkOut}</td>
                <td>${requestScope.booking.status}</td>
                <td><a href="controller?command=delete_booking&id=${item.room.id}">
                    <input type="submit" value="<fmt:message key="msg.delete"/>"></a>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3"><fmt:message key="msg.cost"/>${requestScope.booking.totalCost} USD</td>
        </tr>

    </table>
    <a href="controller?command=create_reservation"><input type="submit"
                                                           value="<fmt:message key="msg.booking.reserve"/>"></a>
    <a href="controller?command=clean_booking"><input type="submit" value="<fmt:message key="msg.booking.clean"/>"></a>
</c:if>
</body>
</html>