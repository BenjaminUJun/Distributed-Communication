<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="client.RMIClient" %>
<%@ include file="veryfyLogin.jsp" %>
<%@ page import="org.apache.log4j.Logger"%>
<%--
Created by Yu Jun @ 2015-05-16
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	Logger logger = Logger.getLogger("booking.jsp");
	String temp = request.getParameter("booking");
	String[] info = temp.split("#");
	for(String s : info) {
		logger.debug("info: " + s);
	}
	String brand = info[0];
	String hotelID = info[1];
	String roomID = info[2];
	String checkinDate = info[3];
	String checkoutDate = info[4];
	String proxy = info[5];
	String bookerID = info[6];
	String creditNO = "1234567890123456";
    RMIClient client = new RMIClient(application.getRealPath("/") + "/WEB-INF/classes");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hotel Booking System</title>
</head>
<body>
<H1 align="center">
    Hotel Booking System
</H1>

<FORM action="/Booking" method="post">
    <TABLE border="1" align="center">
        <TR>
	    <TD>
		Server
 	    </TD>
	    <TD>
		Brand
	    </TD>
	    <TD>
		Guest ID Number
	    </TD>
            <TD>
                Hotel Number
            </TD>
            <TD>
                Room Number
            </TD>
            <TD>
                Check-in Date
            </TD>
            <TD>
            	Check-out Date
            </TD>
            <TD>
                Price
            </TD>
	    <TD>
		Credit Number
	    </TD>
        </TR>
        <TR>
	    <TD>
		<%= proxy%>
                <input type="hidden" name= "proxy" value=<%=proxy %>>
	    </TD>
	    <TD>
		<%= brand%>
                <input type="hidden" name= "brand" value=<%=brand %>>
	    </TD>
	    <TD>
		<%= bookerID%>
		<input type="hidden" name= "bookerID" value=<%=bookerID %>>
	    </TD>
            <TD>
                <%= hotelID%>
                <input type="hidden" name= "hotelID" value=<%=hotelID %>>
            </TD>
            <TD>
                <%= roomID%>
                <input type="hidden" name= "roomID" value=<%=roomID %>>
            </TD>
            <TD>
                <%= checkinDate%>
                <input type="hidden" name= "checkindate" value=<%=checkinDate %>>
            </TD>
            <TD>
                <%= checkoutDate%>
                <input type="hidden" name= "checkoutdate" value=<%=checkoutDate %>>
            </TD>
            <TD>
            	<%logger.debug(proxy + "!!!!!!"); %>
                <div><%= client.queryRoomrates(hotelID,"corba".equalsIgnoreCase(proxy)?2:1)%></div>
            </TD>
	    <TD>
		<input type="text" name="creditNO">
	    </TD>
        </TR>
    </TABLE>
    <div align="center">
        <input type="submit" value="SUBMIT"><input type="button" value="BACK" onclick="window.location.href='/Servlet'">
    </div>
</FORM>
</body>
</html>
