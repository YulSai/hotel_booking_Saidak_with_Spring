<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.update.room.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.update.room.title"/></h1>
<p>${requestScope.message}</p>
<form:form method="post" action="/rooms/update/${requestScope.room.id}" modelAttribute="roomDto">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <h2><spring:message code="msg.number"/> ${requestScope.room.number}</h2>
    <form:input id="room_number-input" path="number" type="hidden" value="${requestScope.room.number}"/>
    <form:errors path="type" cssClass="error-block"/>
    <form:select path="type" required="required">
        <form:option value="${requestScope.room.type}">${requestScope.room.type}</form:option>
        <form:option value="STANDARD"><spring:message code="msg.standard"/></form:option>
        <form:option value="COMFORT"><spring:message code="msg.comfort"/></form:option>
        <form:option value="LUX"><spring:message code="msg.lux"/></form:option>
        <form:option value="PRESIDENT"><spring:message code="msg.president"/></form:option>
    </form:select>
    <br/>
    <form:errors path="capacity" cssClass="error-block"/>
    <form:select path="capacity" required="required">
        <form:option value="${requestScope.room.capacity}">${requestScope.room.capacity}</form:option>
        <form:option value="SINGLE"><spring:message code="msg.single"/></form:option>
        <form:option value="DOUBLE"><spring:message code="msg.double"/></form:option>
        <form:option value="TRIPLE"><spring:message code="msg.triple"/></form:option>
        <form:option value="FAMILY"><spring:message code="msg.family"/></form:option>
    </form:select>
    <br/>
    <form:errors path="status" cssClass="error-block"/>
    <form:select path="status" required="required">
        <form:option value="${requestScope.room.status}">${requestScope.room.status}</form:option>
        <form:option value="AVAILABLE"><spring:message code="msg.available"/></form:option>
        <form:option value="UNAVAILABLE"><spring:message code="msg.unavailable"/></form:option>
    </form:select>
    <br/>
    <form:errors path="price" cssClass="error-block"/>
    <form:label path="price" for="price-input"><spring:message code="msg.price"/>USD: </form:label>
    <form:input id="price-input" path="price" type="text" value="${requestScope.room.price}"/>
    <br/>
    <input class="btn" type="submit" value="<spring:message code="msg.update"/>"/>
    <button class="btn" type="button"><a href="/rooms/${requestScope.room.id}">
        <spring:message code="msg.cancel"/></a></button>
</form:form>
</body>
</html>