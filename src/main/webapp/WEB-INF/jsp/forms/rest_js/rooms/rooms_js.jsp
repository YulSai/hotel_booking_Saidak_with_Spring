<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">
    <link rel="stylesheet" type="text/css" href="/css/rest/pages_for_rest.css">
    <script src="/js/lib/jquery-3.6.1.js"></script>
    <script src="/js/rooms/rooms_with_pagination.js" defer></script>

    <script type="text/javascript">
        let room_update = "<spring:message code="msg.update"/>";

        function validate() {
            alert(room_update);
        }
    </script>

    <title><spring:message code="msg.rooms.title.rest"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<p>${requestScope.message}</p>
<ul class="pagination" style="margin:20px 0; cursor: pointer;"></ul>
<div class="container">
    <div class="row">
        <table class="first" id="roomsTable">
            <thead>
            <tr>
                <th>#</th>
                <th><spring:message code="msg.number"/></th>
                <th><spring:message code="msg.type"/></th>
                <th><spring:message code="msg.capacity"/></th>
                <th><spring:message code="msg.status"/></th>
                <th><spring:message code="msg.price"/>USD</th>
                <th><spring:message code="msg.action"/></th>
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>