<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta name="_csrf_header" content="${_csrf.headerName}"/>
  <meta name="_csrf_token" content="${_csrf.token}"/>
  <link rel="stylesheet" type="text/css" href="/css/style.css">
  <link rel="stylesheet" type="text/css" href="/css/tables.css">
  <link rel="stylesheet" type="text/css" href="/css/rest/pages_for_rest.css">
  <script src="/js/lib/jquery-3.6.1.js"></script>
  <script type="module" src="/js/users/users.js" defer></script>


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
<h1><spring:message code="msg.users.title.rest"/></h1>
<p>${requestScope.message}</p>
<div class="query-string" style="display: none" text="${request.queryString}"></div>
<div class="pagination" style="margin:20px 0; cursor: pointer;">
  <li class="page-item"><button id="first" class="page-link"><spring:message code="msg.page.first"/></button></li>
  <li class="page-item"><button id="prev" class="page-link"><spring:message code="msg.page.prev"/></button></li>
  <li class="page-item active"><button id="current" class="page-link"></button></li>
  <li class="page-item"><button id="next" class="page-link"><spring:message code="msg.page.next"/></button></li>
  <li class="page-item"><button id="last" class="page-link"><spring:message code="msg.page.last"/></button></li>
</div>
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