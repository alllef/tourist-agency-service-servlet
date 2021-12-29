<%--
  Created by IntelliJ IDEA.
  User: alekz29
  Date: 29/12/2021
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin</title>
</head>
<body>
<form action="admin/tour-catalogue" method="get">
    <button type="submit" name="tour-catalogue" value="tour-catalogue" title="tour-catalogue">Go to the tour catalogue</button>
</form>

<form action="admin/users" method="get">
    <button type="submit" name="admin-users" value="admin-users" title="admin-users">Manage blocked and unblocked users</button>
</form>

<form action="managing/tour-requests" method="get">
    <button type="submit" name="manage-requests" value="manage-requests" title="manage-requests">Manage requests</button>
</form>
</body>
</html>
