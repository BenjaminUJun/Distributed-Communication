<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
Created by Yu Jun @ 2015-05-16
--%>


<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
int status = (Integer) request.getAttribute("status");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Hotel Booking System</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
 <H1 align="center">
    Hotel Booking System
</H1>

<br>

<%if(status == 1) { %>
	<div align="center" style="color:red">Book successful!</div>
<%} else {%>
	<div align="center" style="color:red">Book failed!</div>
<% } %>

<div align="center"><input type="button" value="BACK" onclick="window.location.href='/Servlet'"></div>
  </body>
</html>
