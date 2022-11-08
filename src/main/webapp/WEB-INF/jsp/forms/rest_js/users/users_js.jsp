<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <link rel="stylesheet" type="text/css" href="/css/rest/pages_for_rest.css">
    <script src="/js/lib/jquery-3.6.1.js"></script>
    <script src="/js/users/users_with_pagination.js" defer></script>


    <script type="text/javascript">
        let user_update_role = "<spring:message code="msg.user.update.role"/>";
        let user_delete = "<spring:message code="msg.user.delete"/>";

        function validate() {
            alert(user_update_role);
            alert(user_delete);
        }
    </script>

    <title><spring:message code="msg.users.title.rest"/></title>

</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<p>${requestScope.message}</p>
<ul class="pagination" style="margin:20px 0; cursor: pointer;" text="${request.queryString}"></ul>
<div class="container">
    <div class="row">
        <table class="first" id="usersTable">
            <thead>
            <tr>
                <th>#</th>
                <th><spring:message code="msg.user.first.name"/></th>
                <th><spring:message code="msg.user.last.name"/></th>
                <th><spring:message code="msg.user.email"/></th>
                <th><spring:message code="msg.user.phone"/></th>
                <th><spring:message code="msg.user.role"/></th>
                <th><spring:message code="msg.user.status.textB"/></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>