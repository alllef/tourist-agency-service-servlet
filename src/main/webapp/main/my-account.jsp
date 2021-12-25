<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ page import="com.github.alllef.model.entity.User" %><%--
  Created by IntelliJ IDEA.
  User: alekz29
  Date: 25/12/2021
  Time: 12:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My account</title>
</head>
<body>
<div id="user-block">
    <% HttpSession session1 = request.getSession(false);
        User currentUser = (User) session1.getAttribute("user");
    %>
    <h2>First name: <%=currentUser.getFirstName()%>
    </h2>
    <h2>Last name: <%=currentUser.getLastName()%>
    </h2>
    <h3>Email: <%=currentUser.getEmail()%>
    </h3>
    <h3>Password: <%=currentUser.getPassword()%>
    </h3>
</div>

<jsp:include page="/my-account"/>
</body>

</html>
