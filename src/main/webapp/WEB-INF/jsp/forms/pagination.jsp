<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="/css/pages.css">
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename="pageMessage"/>
<div class="pagination">
    <c:if test="${requestScope.total_pages > 1}">
        <a href="/${requestScope.command}/?page=1"><fmt:message key="msg.page.first"/></a>
        <c:if test="${requestScope.current_page > 1}">
            <a href="/${requestScope.command}/?page=${requestScope.current_page - 1}">&laquo;</a>
        </c:if>
        <a class="active"
           href="/${requestScope.command}/?page=1">${requestScope.current_page}/${requestScope.total_pages}</a>
        <c:if test="${requestScope.current_page < requestScope.total_pages}">
            <a href="/${requestScope.command}/?page=${requestScope.current_page + 1}">&raquo;</a>
        </c:if>
        <a href="/${requestScope.command}/?page=${requestScope.total_pages}"><fmt:message
                key="msg.page.last"/></a>
    </c:if>
</div>