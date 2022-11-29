<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><spring:message code="msg.main.error"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="msg.main.error"/></h1>
<h4><c:out value="${message}"/></h4>
<c:if test="${message == null}">
    <h4><spring:message code="msg.main.no.such.page"/></h4>
</c:if>
</body>
</html>