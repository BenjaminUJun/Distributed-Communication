<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%--
Created by Yu Jun @ 2015-05-16
--%>
<%
    if (session.getAttribute("islogin") == null || !(Boolean) session.getAttribute("islogin")) {
%>
    <script type="text/javascript" language="javascript">
        alert("Please login first");
        window.document.location.href="index.jsp";
    </script>
<%
    }
%>
