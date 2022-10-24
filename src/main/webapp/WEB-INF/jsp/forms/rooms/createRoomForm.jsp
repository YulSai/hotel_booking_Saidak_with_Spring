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
    <title><fmt:message key="msg.create.new.room.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.create.new.room.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/rooms/create">
    <label for="room_number-input"><fmt:message key="msg.number"/></label>
    <input id="room_number-input" name="number" type="text"/>
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
        <option value="FAMILY"><fmt:message key="msg.family"/>Y</option>
    </select>
    <br/>
    <select name="status" required="required">
        <option value=""><fmt:message key="msg.choose.status"/></option>
        <option value="AVAILABLE"><fmt:message key="msg.available"/></option>
        <option value="UNAVAILABLE"><fmt:message key="msg.unavailable"/></option>
    </select>
    <br/>
    <label for="price-input"><fmt:message key="msg.price"/></label>
    <input id="price-input" name="price" type="number" min="1" max="9999"/>
    <br/>
    <input type="submit" value="<fmt:message key="msg.create.new.room.register"/>"/>
</form>
</body>
</html>