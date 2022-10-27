<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <title><spring:message code="msg.room.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><spring:message code="msg.room.title"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <tr>
        <th><spring:message code="msg.field"/></th>
        <th><spring:message code="msg.value"/></th>
    </tr>
    <tr>
        <td class="name"><spring:message code="msg.number"/></td>
        <td class="sign">${requestScope.room.number}</td>
    </tr>
    <tr>
        <td class="name"><spring:message code="msg.type"/></td>
        <td class="sign">${requestScope.room.type}</td>
    </tr>
    <tr>
        <td class="name"><spring:message code="msg.capacity"/></td>
        <td class="sign">${requestScope.room.capacity}</td>
    </tr>
    <tr>
        <td class="name"><spring:message code="msg.status"/></td>
        <td class="sign">${requestScope.room.status}</td>
    </tr>
    <tr>
        <td class="name"><spring:message code="msg.price"/>USD</td>
        <td class="sign">${requestScope.room.price}</td>
    </tr>
</table>
<ul>
    <li><a href="/rooms/update/${requestScope.room.id}"><spring:message code="msg.room.update"/></a></li>
</ul>
</body>
</html>