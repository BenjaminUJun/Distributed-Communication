<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
Created by Yu Jun @ 2015-05-16
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
    if (request.getAttribute("logout") != null && (Integer) request.getAttribute("logout") == 1) {
        session.setAttribute("islogin", false);
        session.removeAttribute("bookerID");
        session.removeAttribute("bookerName");
        session.removeAttribute("telePhone");
        session.removeAttribute("email");
    }
%>

<html>
  <head>
    <title>Hotel Booking System</title>
  </head>
  <body>
    <H1 align="center">
        Hotel Booking System
    </H1>
  <br>
  <br>
    <%
        if (request.getAttribute("isVerified") != null && !(Boolean) request.getAttribute("isVerified"))
            out.println("<div align=\"center\" style=\"color:red\">Log in failed, pleas try again!</div>");
    %>
  <form method="POST" action="/VerifyFunc">
      <TABLE align="center">
          <TR>
              <TD>
                  Guest ID Number:
              </TD>
              <TD>
                  <input type="text" name="bookerID">
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
      </TABLE>
      <div align="center">
          <input type="submit" value="LOG IN"><input type="button" value="REGISTER" onclick="window.location.href='register.jsp'">
      </div>

  </form>
  </body>
</html>
