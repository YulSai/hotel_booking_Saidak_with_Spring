<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <link rel="stylesheet" type="text/css" href="/css/login.css">

    <script src="/js/lib/jquery-3.6.1.js"></script>
    <script src="/js/users/user.js" defer></script>

    <script type="text/javascript">
        let user_field = "<spring:message code="msg.field"/>";
        let user_value = "<spring:message code="msg.value"/>";
        let user_first_name = "<spring:message code="msg.user.first.name"/>";
        let user_last_name = "<spring:message code="msg.user.last.name"/>"
        let user_email = "<spring:message code="msg.user.email"/>";
        let user_phone = "<spring:message code="msg.user.phone"/>";
        let user_role = "<spring:message code="msg.user.role"/>";
        let user_delete = "<spring:message code="msg.user.delete"/>";
        let user_update_role = "<spring:message code="msg.user.update.role"/>";
        let user_update = "<spring:message code="msg.user.update"/>";
        let user_change_password = "<spring:message code="msg.user.change.password"/>";
        let user_role_session = "${sessionScope.user.role}";


        function validate() {
            alert(user_field);
            alert(user_value);
            alert(user_first_name);
            alert(user_last_name);
            alert(user_email);
            alert(user_phone);
            alert(user_role);
            alert(user_delete);
            alert(user_update_role);
            alert(user_update);
            alert(user_change_password);
            alert(user_role_session);
        }
    </script>

    <title><spring:message code="msg.user.rest"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.user.title.rest"/></h1>
<p>${requestScope.message}</p>
<table class="first" id="userTable">
    <tbody>
    </tbody>
</table>
<div class="links">
</div>
</body>
</html>