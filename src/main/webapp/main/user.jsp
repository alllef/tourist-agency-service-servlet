<%--
  Created by IntelliJ IDEA.
  User: alekz29
  Date: 29/12/2021
  Time: 12:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User</title>
</head>
<body>
<form action="main/my-account.jsp" method="get">
    <button type="submit" name="my-account" value="my-account" title="my-account">Go to my account</button>
</form>

<form action="main/tour-catalogue.jsp" method="get">
    <button type="submit" name="tour-catalogue" value="tour-catalogue" title="tour-catalogue">Go to the tour catalogue</button>
</form>

<form action="/logout" method="post">
    <button type="submit" name="logout" value="logout" title="logout">Logout from user</button>
</form>
</body>
</html>
