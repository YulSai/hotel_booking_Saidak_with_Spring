<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="css/navbar.css">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>

<ul class="navbar">
    <li><a href="/hotel_booking"><fmt:message key="msg.main.home"/></a></li>
    <li><a href="controller?command=search_available_rooms_form"><fmt:message key="msg.main.search"/></a></li>
    <li><a href="controller?command=booking"><fmt:message key="msg.main.booking"/></a></li>


    <c:if test="${sessionScope.user.role == 'ADMIN'}">
        <li><a href="controller?command=rooms"><fmt:message key="msg.main.rooms"/></a></li>
        <li><a href="controller?command=users"><fmt:message key="msg.main.users"/></a></li>
        <li><a href="controller?command=reservations"><fmt:message key="msg.main.reservations"/></a></li>
        <li><a href="controller?command=create_room_form"><fmt:message key="msg.main.new.room"/></a></li>
    </c:if>

    <c:if test="${sessionScope.user.role == 'CLIENT'}">
        <li><a href="controller?command=user&id=${sessionScope.user.id}"><fmt:message key="msg.main.profile"/></a></li>
        <li><a href="controller?command=user_reservations&id=${sessionScope.user.id}">
            <fmt:message key="msg.main.my.reservations"/></a></li>
    </c:if>

    <c:if test="${sessionScope.user != null}">
        <li><a class="right" href="controller?command=logout"><fmt:message key="msg.main.logout"/></a></li>
    </c:if>
    <c:if test="${sessionScope.user == null}">
        <li><a href="controller?command=create_user_form"><fmt:message key="msg.main.register"/></a></li>
        <li><a href="controller?command=login_form"><fmt:message key="msg.main.sign.in"/></a></li>
    </c:if>

    <li><a href="controller?command=language_select&language=en"><fmt:message key="msg.main.en"/></a></li>
    <li><a href="controller?command=language_select&language=ru"><fmt:message key="msg.main.ru"/></a></li>
</ul>