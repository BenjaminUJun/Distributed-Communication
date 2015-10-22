<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="client.RMIClient" %>
<%@include file="veryfyLogin.jsp" %>
<%@page import="classes.*" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.List" %>
<%--
Created by Yu Jun @ 2015-05-16
--%>

<%
    List<String> cities = new ArrayList<String>();
    if (session.getAttribute("cities") != null)
        cities = (List<String>) session.getAttribute("cities");
    List<Room> room1s = (List<Room>) request.getAttribute("rooms1");
    List<Room> room2s = (List<Room>) request.getAttribute("rooms2");
    int size1 = 0;
    int size2 = 0;
    Room[] rooms1 = null;
    Room[] rooms2 = null;
    if (room1s != null && room1s.size() > 1) {
    	
 		size1 = room1s.size();
 		rooms1  = new Room[size1];
        Logger logger = Logger.getLogger("query.jsp");
        logger.debug("size: " + size1);
        for (int i = 0; i < size1; i++) {
        	rooms1[i] = room1s.get(i);
        }
    }
    String[] brands1 = new String[size1];
    if (room2s != null && room2s.size() > 1) {
    	
 		size2 = room2s.size();
 		rooms2  = new Room[size2];
        Logger logger = Logger.getLogger("query.jsp");
        logger.debug("size: " + size2);
        for (int i = 0; i < size2; i++) {
        	rooms2[i] = room2s.get(i);
        }
    }
    String[] brands2 = new String[size2];
    RMIClient client = new RMIClient(application.getRealPath("/") + "/WEB-INF/classes");
%>
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
<% 
	out.println("<p align=\"center\">Hello Guest: " + session.getAttribute("bookerID") + "<a href=\"index.jsp?logout=1\">&nbspLOG OUT</a></p>");
%>
<br>
<form method="POST" action="/Servlet">
	<TABLE align="center">
        <TR>
            <TD>Input Hotel Number</TD>
            <TD>
                <select name = "hotelID">
                    <%
                        for (int j = 0; j < 30;j++) {
                        out.println("<option value=\"" + (j+1) + "\">" + (j+1) + "</option>");
                        }
                    %>
                </select>
            </TD>
        </TR>
        <TR>
            <TD>Input Check-in Date</TD>
            <TD>
               2015-05-<select name = "checkindate">
                 <%
						for (int i = 0;i < 9; i++) {
							out.println("<option value=\"0" + (i + 1) + "\">" + (i + 1) + "</option>");
						}
						for (int i = 9;i < 31;i++){
							out.println("<option value=\"" + (i + 1) + "\">" + (i + 1) + "</option>");
						}
                 %>
                </select>
            </TD>
	</TR>
	<TR>
            <TD>Input Check-out Date</TD>
            <TD>
                2015-05-<select name = "checkoutdate">
                <%
					for (int i = 0;i < 9; i++) {
						out.println("<option value=\"0" + (i + 1) + "\">" + (i + 1) + "</option>");
					}
					for (int i = 9;i < 31;i++){
						out.println("<option value=\"" + (i + 1) + "\">" + (i + 1) + "</option>");
					}
                %>
                </select>
            </TD>
        </TR>
    </TABLE>
    <div style="text-align:center">
        <input type="submit" value="QUERY">
    </div>

</form>

<form method="post" action="booking.jsp">
<TABLE BORDER="1" align="center">
    <TR>
	<TD>
		Server
	</TD>
        <TD>
            Brand/Hotel NO/Room NO
        </TD>
        <TD>
            Check-in/Check-out Date
        </TD>
        <TD>
            Price
        </TD>
    </TR>
    <% for(int i = 0; i < size1 ; i++) { %>
    <TR>
	<TD>
		RMI
	</TD>
        <TD>
            <div>
                <%
                	if ((Integer.parseInt(rooms1[i].getHotelID()) % 3) == 1){
                		brands1[i] = "hilton";
                    	out.println("hilton/"+rooms1[i].getHotelID() + "/" + rooms1[i].getRoomID());
                	} else if ((Integer.parseInt(rooms1[i].getHotelID()) % 3) == 2){
                		brands1[i] = "chevron";
                		out.println("chevron/"+rooms1[i].getHotelID() + "/" + rooms1[i].getRoomID());
                	} else {
                		brands1[i] = "regent";
                		out.println("regent/"+rooms1[i].getHotelID() + "/" + rooms1[i].getRoomID());
                	}
                %>
            </div>
        </TD> 
        <TD>   
            <div>
                <%
                    out.println("2015-05-"+(String) request.getParameter("checkindate")  + "/" + "2015-05-" + (String) request.getParameter("checkoutdate"));
                %>
            </div>
        </TD>
        <TD>
        	<div>
        		<% 
        			out.println(client.queryRoomrates(rooms1[i].getHotelID(), 1));
        		%>
        	</div>
        </TD>
        <TD>
            <input CHECKED name = "booking" type="radio" value=<%= brands1[i] +"#"+rooms1[i].getHotelID() + "#" + rooms1[i].getRoomID() + "#" + "2015-05-" + (String) request.getParameter("checkindate") + "#" + "2015-05-" + (String) request.getParameter("checkoutdate") + "#" + "RMI" + "#"+ (String) session.getAttribute("bookerID")%>>

        </TD>
    </TR>
    
    <%} %>
</TABLE>
<TABLE BORDER="1" align="center">
    <TR>
	<TD>
		Server
	</TD>
        <TD>
            Brand/Hotel NO/Room NO
        </TD>
        <TD>
            Check-in/Check-out Date
        </TD>
        <TD>
            Price
        </TD>
    </TR>
    <% for(int i = 0; i < size2 ; i++) { %>
    <TR>
	<TD>
		CORBA
	</TD>
        <TD>
            <div>
                <%
                	if ((Integer.parseInt(rooms2[i].getHotelID()) % 3) == 1){
                		brands2[i] = "hilton";
                    	out.println("hilton/"+rooms1[i].getHotelID() + "/" + rooms2[i].getRoomID());
                	} else if ((Integer.parseInt(rooms2[i].getHotelID()) % 3) == 2){
                		brands2[i] = "chevron";
                		out.println("chevron/"+rooms2[i].getHotelID() + "/" + rooms2[i].getRoomID());
                	} else {
                		brands2[i] = "regent";
                		out.println("regent/"+rooms2[i].getHotelID() + "/" + rooms2[i].getRoomID());
                	}
                %>
            </div>
        </TD> 
        <TD>   
            <div>
                <%
                    out.println("2015-05-"+(String) request.getParameter("checkindate")  + "/" + "2015-05-" + (String) request.getParameter("checkoutdate"));
                %>
            </div>
        </TD>
        <TD>
        	<div>
        		<% 
        			out.println(client.queryRoomrates(rooms2[i].getHotelID(), 2));
        		%>
        	</div>
        </TD>
        <TD>
            <input CHECKED name = "booking" type="radio" value=<%= brands2[i] +"#"+rooms2[i].getHotelID() + "#" + rooms2[i].getRoomID() + "#" + "2015-05-" + (String) request.getParameter("checkindate") + "#" + "2015-05-" + (String) request.getParameter("checkoutdate") + "#" + "CORBA" + "#"+ (String) session.getAttribute("bookerID")%>>

        </TD>
    </TR>
    
    <%} %>
</TABLE>
<div style="text-align:center"><input type="submit" value="BOOK"></div>
</form>
</body>
</html>
