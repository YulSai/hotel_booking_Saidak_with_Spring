<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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
<p>${requestScope.message}</p>
</body>
</html>