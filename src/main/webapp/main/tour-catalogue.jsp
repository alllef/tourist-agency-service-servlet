<%@ page import="jakarta.servlet.jsp." %>
<%@ page import="jakarta.servlet.jsp.JspWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Tour catalogue</h1><br>
<p><%= out%></p>
<form action="register" method="post">
    <h3>Min price</h3>
    <input id="min-price" name="min-price" type="number" required="required"/>
    <h3>Max price</h3>
    <input id="max-price" name="max-price" type="number" required="required"/>
    <h3>Last name</h3>
    <button type="submit" name="" value="Register" title="Register">Register</button>
</form>

</body>
</html>
