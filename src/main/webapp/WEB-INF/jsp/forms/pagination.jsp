<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/css/pages.css">
<div class="pagination">
    <c:if test="${requestScope.total_pages > 1}">
        <a href="/${requestScope.command}/?page=1"><spring:message code="msg.page.first"/></a>
        <c:if test="${requestScope.current_page > 1}">
            <a href="/${requestScope.command}/?page=${requestScope.current_page - 1}">&laquo;</a>
        </c:if>
        <a class="active"
           href="/${requestScope.command}/?page=1">${requestScope.current_page}/${requestScope.total_pages}</a>
        <c:if test="${requestScope.current_page < requestScope.total_pages}">
            <a href="/${requestScope.command}/?page=${requestScope.current_page + 1}">&raquo;</a>
        </c:if>
        <a href="/${requestScope.command}/?page=${requestScope.total_pages}"><spring:message code="msg.page.last"/></a>
    </c:if>
</div>