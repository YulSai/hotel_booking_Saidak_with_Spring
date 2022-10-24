<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <title><fmt:message key="msg.main.error"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h2><fmt:message key="msg.main.no.such.page"/></h2>
<p>${requestScope.message}</p>
</body>
</html>