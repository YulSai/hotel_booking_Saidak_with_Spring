<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.reservation.update.title"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.reservation.update.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/reservations/update/${requestScope.reservation.id}">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <br/>
    <select name="status" required="required">
        <option value=""><spring:message code="msg.reservation.update.choose"/></option>
        <option value="IN_PROGRESS"><spring:message code="msg.reservation.update.progress"/></option>
        <option value="CONFIRMED"><spring:message code="msg.reservation.update.confirmed"/></option>
        <option value="REJECTED"><spring:message code="msg.reservation.update.rejected"/></option>
    </select>
    <br/>
    <input class="btn" type="submit" value="<spring:message code="msg.update.status"/>"/>
    <button class="btn" type="button"><a href="/reservations/${requestScope.reservation.id}">
        <spring:message code="msg.cancel"/></a></button>
</form>
</body>
</html>