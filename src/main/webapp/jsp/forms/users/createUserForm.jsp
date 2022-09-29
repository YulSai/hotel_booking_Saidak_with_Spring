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
    <title><fmt:message key="msg.create.new.user.title"/></title>
</head>
<body>
<jsp:include page="../../navbar.jsp"/>
<h1><fmt:message key="msg.create.new.user.title"/></h1>
<p>${requestScope.message}</p>
<form method="post" action="controller" enctype="multipart/form-data">
    <input name="command" type="hidden" value="create_user"/>
    <label for="first_name-input"><fmt:message key="msg.create.user.first.name"/></label>
    <input id="first_name-input" name="first_name" type="text"/>
    <br/>
    <label for="last_name-input"><fmt:message key="msg.create.user.last.name"/></label>
    <input id="last_name-input" name="last_name" type="text"/>
    <br/>
    <label for="email-input"><fmt:message key="msg.create.user.email"/></label>
    <input id="email-input" name="email" type="email"/>
    <br/>
    <label for="password-input"><fmt:message key="msg.create.user.password"/></label>
    <input id="password-input" name="password" type="password" min="6"/>
    <br/>
    <label for="phone_number-input"><fmt:message key="msg.create.user.phone"/></label>
    <input id="phone_number-input" name="phone_number" type="tel" min="6"/>
    <br/>
    <label for="avatar_input"><fmt:message key="msg.user.avatar"/></label>
    <input id="avatar_input" name="avatar" type="file" accept="image/*"/>
    <br/>
    <input type="submit" value="<fmt:message key="msg.user.register"/>"/>
</form>
</body>
</html>