<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">
    <title><spring:message code="msg.login.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><spring:message code="msg.login.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/login">
    <input name="command" type="hidden" value="login"/>
    <img src="/images/avatarLogin.png" alt="Avatar" class="avatar">
    <br/>
    <label for="email-input"><b><spring:message code="msg.email"/></b></label>
    <input id="email-input" type="email" placeholder="<spring:message code="msg.login.enter.email"/>"
           name="email" required>
    <br/>
    <label for="password-input"><b><spring:message code="msg.password"/></b></label>
    <input id="password-input" type="password" placeholder="<spring:message code="msg.login.enter.password"/>"
           name="password" min="6" autocomplete="current-password" required>
    <br/>
    <button type="submit"><spring:message code="msg.login.login"/></button>
    <button type="button"><a href="/users/create"><spring:message code="msg.login.new.user"/></a>
    </button>
    <button type="button"><a href="/"><spring:message code="msg.cancel"/></a></button>
</form>
</body>
</html>