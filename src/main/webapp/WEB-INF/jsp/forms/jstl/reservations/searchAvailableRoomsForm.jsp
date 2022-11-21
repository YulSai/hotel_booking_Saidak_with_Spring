<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <script src="/js/lib/jquery-3.6.1.js"></script>
    <script src="/js/utils/date.js" defer></script>

    <title><spring:message code="msg.search.title"/></title>

</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.search.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/rooms/search_available_rooms">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    <label for="check_in"><spring:message code="msg.checkIn"/></label>
    <input id="check_in" name="check_in" type="date" min="" max="2025-12-30">
    <br/>
    <label for="check_out"><spring:message code="msg.checkOut"/></label>
    <input id="check_out" name="check_out" type="date" min="" max="2023-12-31">
    <br/>
    <select name="type" required="required">
        <option value=""><spring:message code="msg.choose.type"/></option>
        <option value="STANDARD"><spring:message code="msg.standard"/></option>
        <option value="COMFORT"><spring:message code="msg.comfort"/></option>
        <option value="LUX"><spring:message code="msg.lux"/></option>
        <option value="PRESIDENT"><spring:message code="msg.president"/></option>
    </select>
    <br/>
    <select name="capacity" required="required">
        <option value=""><spring:message code="msg.choose.capacity"/></option>
        <option value="SINGLE"><spring:message code="msg.single"/></option>
        <option value="DOUBLE"><spring:message code="msg.double"/></option>
        <option value="TRIPLE"><spring:message code="msg.triple"/></option>
        <option value="FAMILY"><spring:message code="msg.family"/></option>
    </select>
    <br/>
    <input class="btn" type="submit" value="<spring:message code="msg.search.search"/>"/>
</form>
</body>
</html>