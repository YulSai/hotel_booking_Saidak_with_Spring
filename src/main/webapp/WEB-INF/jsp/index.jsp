<%@page language="java" contentType="text/html; charser=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title>HotelBooking</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<p>${requestScope.message}</p>
<spring:message code="msg.main.welcome.guest" var="guest"/>
<sec:authorize access="isAuthenticated()">
    <sec:authentication var="userAuthenticated" property="principal"/>
</sec:authorize>
<h1><spring:message code="msg.main.welcome" />${userAuthenticated.firstName}
    <sec:authorize access="isAnonymous()">${guest}!</sec:authorize></h1>
<img src="/images/mainImg.jpg" alt="hotel" />
</body>
</html>