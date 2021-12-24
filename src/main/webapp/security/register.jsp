<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, java.text.*" %>

<html lang="en-us">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="Description" content="A simplistic servlet example project for programmatic authentication.">
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<title></title>
</head>
<body>
	<h2>Please, fill registration form</h2>
	<form action="register" method="post">
		<h3>Email</h3>
		<input id="email" name="email" type="text" required="required" />
		<h3>First name</h3>
		<input id="first-name" name="first-name" type="text" required="required" />
		<h3>Last name</h3>
		<input id="last-name" name="last-name" type="text" required="required" />
		<h3>Password</h3>
		<input id="password" name="password" type="password"  required="required" />
		<br />
		<button type="submit" name="Register" value="Register" title="Register">Register</button>
	</form>
</body>
</html>