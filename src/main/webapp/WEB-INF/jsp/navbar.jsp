<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/css/navbar.css">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>

<ul class="navbar">
    <li><a href="/"><fmt:message key="msg.main.home"/></a></li>
    <li><a href="/rooms/search_available_rooms"><fmt:message key="msg.main.search"/></a></li>
    <li><a href="/reservations/booking"><fmt:message key="msg.main.booking"/></a></li>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <li><a href="/rooms/all"><fmt:message key="msg.main.rooms"/></a></li>
        <li><a href="/users/all"><fmt:message key="msg.main.users"/></a></li>
        <li><a href="/reservations/all"><fmt:message key="msg.main.reservations"/></a></li>
        <li><a href="/rooms/create"><fmt:message key="msg.main.new.room"/></a></li>
    </c:if>

    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <li><a href="/users/${sessionScope.user.id}"><fmt:message key="msg.main.profile"/></a></li>
        <li><a href="/reservations/user_reservations/${sessionScope.user.id}">
            <fmt:message key="msg.main.my.reservations"/></a></li>
    </c:if>

    <c:if test="${sessionScope.user != null}">
        <li><a class="right" href="/logout"><fmt:message key="msg.main.logout"/></a></li>
    </c:if>
    <c:if test="${sessionScope.user == null}">
        <li><a href="/users/create"><fmt:message key="msg.main.register"/></a></li>
        <li><a href="/login"><fmt:message key="msg.main.sign.in"/></a></li>
    </c:if>

    <li><a href="/language/en"><fmt:message key="msg.main.en"/></a></li>
    <li><a href="/language/ru"><fmt:message key="msg.main.ru"/></a></li>
</ul>