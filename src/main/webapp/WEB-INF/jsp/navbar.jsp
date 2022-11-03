<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/css/navbar.css">

<ul class="navbar">
    <li><a href="/"><spring:message code="msg.main.home"/></a></li>
    <li><a href="/rooms/search_available_rooms"><spring:message code="msg.main.search"/></a></li>
    <li><a href="/reservations/booking"><spring:message code="msg.main.booking"/></a></li>

    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <li><a href="/rooms/all"><spring:message code="msg.main.rooms"/></a></li>
        <li><a href="/rooms/js/all"><spring:message code="msg.main.rooms.rest"/></a></li>
        <li><a href="/users/all"><spring:message code="msg.main.users"/></a></li>
        <li><a href="/users/js/all"><spring:message code="msg.main.users.rest"/></a></li>
        <li><a href="/reservations/all"><spring:message code="msg.main.reservations"/></a></li>
        <li><a href="/rooms/create"><spring:message code="msg.main.new.room"/></a></li>
    </c:if>

    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <li><a href="/users/${sessionScope.user.id}"><spring:message code="msg.main.profile"/></a></li>
        <li><a href="/reservations/user_reservations/${sessionScope.user.id}">
            <spring:message code="msg.main.my.reservations"/></a></li>
    </c:if>

    <c:if test="${sessionScope.user != null}">
        <li><a class="right" href="/logout"><spring:message code="msg.main.logout"/></a></li>
    </c:if>
    <c:if test="${sessionScope.user == null}">
        <li><a href="/users/create"><spring:message code="msg.main.register"/></a></li>
        <li><a href="/login"><spring:message code="msg.main.sign.in"/></a></li>
    </c:if>

    <li><a href="/language/?lang=en"><spring:message code="msg.main.en"/></a></li>
    <li><a href="/language/?lang=ru"><spring:message code="msg.main.ru"/></a></li>
</ul>