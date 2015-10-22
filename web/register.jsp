<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
Created by Yu Jun @ 2015-05-16
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hotel Booking System</title>
</head>
<body>
<H1 align="center">
   	Hotel Booking System
</H1>
<br>
<br>
<%
    if (request.getAttribute("islogsuc") != null && !(Boolean) request.getAttribute("islogsuc"))
        out.println("<div align=\"center\" style=\"color:red\">Register failed, pleas try again!</div>");
%>
<form method="POST" action="/Register">
    <TABLE align="center">
        <TR>
            <TD>
                Booker ID Number:
            </TD>
            <TD>
                <input type="text" name="bookerID">
            </TD>
        </TR>
        <TR>
            <TD>
                Booker Name:
            </TD>
            <TD>
                <input type="text" name="bookerName">
            </TD>
        </TR>
        <TR>
            <TD>
                Telephone Number:
            </TD>
            <TD>
                <input type="text" name="telePhone">
            </TD>
        </TR>
        <TR>
            <TD>
                Password:
            </TD>
            <TD>
                <input type="password" name="passwd">
            </TD>
        </TR>
        <TR>
            <TD>
                Email:
            </TD>
            <TD>
                <input type="text" name="email">
            </TD>
        </TR>
    </TABLE>
    <div align="center">
        <input type="submit" value="SUBMIT"><input type="reset" value="RESET">
    </div>

</form>
</body>
</html>
