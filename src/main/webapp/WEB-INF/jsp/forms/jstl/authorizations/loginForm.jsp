<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.login.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="/login">
    <input name="command" type="hidden" value="login"/>
    <img src="/images/avatarLogin.png" alt="Avatar" class="avatar">

    <c:if test="${param.timeout}"><p style="color:red"><spring:message code="msg.error.timeout"/></p></c:if>
    <c:if test="${param.error}"><p style="color:red"><spring:message code="msg.incorrect.email.password"/></p></c:if>
    <c:if test="${param.logout}"><p style="color:red"><spring:message code="msg.error.logout"/></p></c:if>


    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <br/>
    <label for="username-input"><b><spring:message code="msg.username"/></b></label>
    <input id="username-input" type="text" placeholder="<spring:message code="msg.login.username.email"/>"
           name="username" required>
    <br/>
    <label for="password-input"><b><spring:message code="msg.password"/></b></label>
    <input id="password-input" type="password" placeholder="<spring:message code="msg.login.enter.password"/>"
           name="password" min="6" autocomplete="current-password" required>
    <br/>
    <button class="btn" type="submit"><spring:message code="msg.login.login"/></button>
    <button class="btn" type="button"><a href="/users/create"><spring:message code="msg.login.new.user"/></a>
    </button>
    <button class="btn" type="button"><a href="/"><spring:message code="msg.cancel"/></a></button>
</form>
</body>
</html>