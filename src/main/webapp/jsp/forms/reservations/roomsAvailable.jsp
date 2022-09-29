<%@ page contentType="text/html; charset=UTF-8"
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
    <title><fmt:message key="msg.available.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1 id="title"><fmt:message key="msg.available.detail"/></h1>
<h2>${sessionScope.check_in} - ${sessionScope.check_out}</h2>
<p>${requestScope.message}</p>
<table>
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
    <c:forEach items="${sessionScope.rooms_available}" var="room" varStatus="counter">
        <tr>
            <td><a href="controller?command=room&id=${room.id}">${counter.count}</a></td>
            <td>${room.number}</td>
            <td>${room.type}</td>
            <td>${room.capacity}</td>
            <td>${room.status}</td>
            <td>${room.price}</td>
            <td>
                <form method="post" action="controller">
                    <input type="hidden" name="command" value="add_booking">
                    <input type="hidden" name="room_id" value="${room.id}">
                    <input type="hidden" name="check_in" value="${check_in}">
                    <input type="hidden" name="check_out" value="${check_out}">
                    <input type="submit" value="<fmt:message key="msg.available.book"/>">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>