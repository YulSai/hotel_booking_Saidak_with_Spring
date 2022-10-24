<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><fmt:message key="msg.search.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.search.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/rooms/search_available_rooms">
    <label for="check_in"><fmt:message key="msg.checkIn"/></label>
    <input id="check_in" name="check_in" type="date" min="2022-08-21" max="2025-12-30">
    <br/>
    <label for="check_out"><fmt:message key="msg.checkOut"/></label>
    <input id="check_out" name="check_out" type="date" min="2022-08-22" max="2025-12-31">
    <br/>
    <select name="type" required="required">
        <option value=""><fmt:message key="msg.choose.type"/></option>
        <option value="STANDARD"><fmt:message key="msg.standard"/></option>
        <option value="COMFORT"><fmt:message key="msg.comfort"/></option>
        <option value="LUX"><fmt:message key="msg.lux"/></option>
        <option value="PRESIDENT"><fmt:message key="msg.president"/></option>
    </select>
    <br/>
    <select name="capacity" required="required">
        <option value=""><fmt:message key="msg.choose.capacity"/></option>
        <option value="SINGLE"><fmt:message key="msg.single"/></option>
        <option value="DOUBLE"><fmt:message key="msg.double"/></option>
        <option value="TRIPLE"><fmt:message key="msg.triple"/></option>
        <option value="FAMILY"><fmt:message key="msg.family"/></option>
    </select>
    <br/>
    <input type="submit" value="<fmt:message key="msg.search.search"/>"/>
</form>
</body>
</html>