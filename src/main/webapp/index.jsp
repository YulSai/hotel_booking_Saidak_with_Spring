<%@page language="java" contentType="text/html; charser=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <title>HotelBooking</title>
</head>
<body>
<jsp:include page="jsp/navbar.jsp"/>
<p>${requestScope.message}</p>
<h1><fmt:message key="msg.main.welcome" /> ${sessionScope.user != null ? sessionScope.user.firstName : 'Guest'}!</h1>
<img src="images/mainImg.jpg" alt="hotel"/>
</body>
</html>