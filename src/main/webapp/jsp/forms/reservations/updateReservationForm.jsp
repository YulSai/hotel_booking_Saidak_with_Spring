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
    <title><fmt:message key="msg.reservation.update.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.reservation.update.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="controller">
    <input name="command" type="hidden" value="update_reservation"/>
    <input name="id" type="hidden" value="${requestScope.reservation.id}"/>
    <br/>
    <select name="status" required="required">
        <option value=""><fmt:message key="msg.reservation.update.choose"/></option>
        <option value="IN_PROGRESS"><fmt:message key="msg.reservation.update.progress"/></option>
        <option value="CONFIRMED"><fmt:message key="msg.reservation.update.confirmed"/></option>
        <option value="REJECTED"><fmt:message key="msg.reservation.update.rejected"/></option>
    </select>
    <br/>
    <input type="submit" value="<fmt:message key="msg.update.status"/>"/>
</form>
</body>
</html>