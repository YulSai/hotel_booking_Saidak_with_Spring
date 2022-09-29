<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title><fmt:message key="msg.update.room.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.update.room.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="controller">
    <input name="command" type="hidden" value="update_room"/>
    <input name="id" type="hidden" value="${requestScope.room.id}"/>
    <label for="room_number-input"><fmt:message key="msg.number"/> </label>
    <input id="room_number-input" name="room_number" type="text" value="${requestScope.room.number}"/>
    <br/>
    <select name="type" required="required">
        <option value="${requestScope.room.type}">${requestScope.room.type}</option>
        <option value="STANDARD"><fmt:message key="msg.standard"/></option>
        <option value="COMFORT"><fmt:message key="msg.comfort"/></option>
        <option value="LUX"><fmt:message key="msg.lux"/></option>
        <option value="PRESIDENT"><fmt:message key="msg.president"/></option>
    </select>
    <br/>
    <select name="capacity" required="required">
        <option value="${requestScope.room.capacity}">${requestScope.room.capacity}</option>
        <option value="SINGLE"><fmt:message key="msg.single"/></option>
        <option value="DOUBLE"><fmt:message key="msg.double"/></option>
        <option value="TRIPLE"><fmt:message key="msg.triple"/></option>
        <option value="FAMILY"><fmt:message key="msg.family"/></option>
    </select>
    <br/>
    <select name="status" required="required">
        <option value="${requestScope.room.status}">${requestScope.room.status}</option>
        <option value="AVAILABLE"><fmt:message key="msg.available"/></option>
        <option value="UNAVAILABLE"><fmt:message key="msg.unavailable"/></option>
    </select>
    <br/>
    <label for="price-input"><fmt:message key="msg.price"/>USD: </label>
    <input id="price-input" name="price" type="number" min="1" max="9999" value="${requestScope.room.price}"/>
    <br/>
    <input type="submit" value="<fmt:message key="msg.update"/>"/>
</form>
</body>
</html>