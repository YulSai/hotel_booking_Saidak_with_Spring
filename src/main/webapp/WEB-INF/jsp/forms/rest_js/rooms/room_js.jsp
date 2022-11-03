<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
    <link rel="stylesheet" type="text/css" href="/css/tables.css">

    <script src="/js/lib/jquery-3.6.1.js"></script>
    <script src="/js/rooms/room.js" defer></script>

    <script type="text/javascript">
        let room_field = "<spring:message code="msg.field"/>";
        let room_value = "<spring:message code="msg.value"/>";
        let room_number = "<spring:message code="msg.number"/>";
        let room_type = "<spring:message code="msg.type"/>"
        let room_capacity = "<spring:message code="msg.capacity"/>";
        let room_status = "<spring:message code="msg.status"/>";
        let room_price = "<spring:message code="msg.price"/>";
        let room_update = "<spring:message code="msg.room.update"/>";



        function validate() {
            alert(room_field);
            alert(room_value);
            alert(room_number);
            alert(room_type);
            alert(room_capacity);
            alert(room_status);
            alert(room_price);
            alert(room_update);
        }
    </script>

    <title><spring:message code="msg.room.title.rest"/></title>
</head>
<body>
<jsp:include page="../../../navbar.jsp"/>
<h1><spring:message code="msg.room.title.rest"/></h1>
<p>${requestScope.message}</p>
<table class="first" id="roomTable">
    <tbody>
    </tbody>
</table>
<div class="links">
</div>


</body>
</html>