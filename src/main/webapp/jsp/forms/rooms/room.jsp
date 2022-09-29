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
    <title><fmt:message key="msg.room.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.room.title"/></h1>
<p>${requestScope.message}</p>
<table class="first">
    <tr>
        <th><fmt:message key="msg.field"/></th>
        <th><fmt:message key="msg.value"/></th>
    </tr>
    <tr>
        <td class="name"><fmt:message key="msg.number"/></td>
        <td class="sign">${requestScope.room.number}</td>
    </tr>
    <tr>
        <td class="name"><fmt:message key="msg.type"/></td>
        <td class="sign">${requestScope.room.type}</td>
    </tr>
    <tr>
        <td class="name"><fmt:message key="msg.capacity"/></td>
        <td class="sign">${requestScope.room.capacity}</td>
    </tr>
    <tr>
        <td class="name"><fmt:message key="msg.status"/></td>
        <td class="sign">${requestScope.room.status}</td>
    </tr>
    <tr>
        <td class="name"><fmt:message key="msg.price"/>USD</td>
        <td class="sign">${requestScope.room.price}</td>
    </tr>
</table>
<ul>
    <li><a href="controller?command=update_room_form&id=${requestScope.room.id}"><fmt:message
            key="msg.room.update"/></a></li>
</ul>
</body>
</html>