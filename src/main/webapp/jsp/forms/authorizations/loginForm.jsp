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
    <link rel="stylesheet" type="text/css" href="css/login.css">
    <title><fmt:message key="msg.login.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.login.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="controller">
    <input name="command" type="hidden" value="login"/>
    <img src="images/avatarLogin.png" alt="Avatar" class="avatar">
    <br/>
    <label for="email-input"><b><fmt:message key="msg.email"/></b></label>
    <input id="email-input" type="email" placeholder="<fmt:message key="msg.login.enter.email"/>"
           name="email" required>
    <br/>
    <label for="password-input"><b><fmt:message key="msg.password"/></b></label>
    <input id="password-input" type="password" placeholder="<fmt:message key="msg.login.enter.password"/>"
           name="password" min="6" required>
    <br/>
    <button type="submit"><fmt:message key="msg.login.login"/></button>
    <button type="button"><a href="controller?command=create_user_form"><fmt:message key="msg.login.new.user"/></a>
    </button>
    <button type="button"><a href="/hotel_booking"><fmt:message key="msg.cancel"/></a></button>
</form>
</body>
</html>