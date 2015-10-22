	/**
 * 
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import util.Constants;
import classes.Room;
import client.RMIClient;
/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class Servlet extends HttpServlet {
	
	RMIClient client = null;
	List<String> cities = new ArrayList<String>();
	Logger logger = Logger.getLogger(Servlet.class);
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		try {
		super.init();
		String path = getServletContext().getRealPath("/") + Constants.SERVDIR;
		client = new RMIClient(path);
		logger.debug("Initialization Completed!");
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String hotelID = request.getParameter("hotelID");
		session.setAttribute("hotelID", hotelID); 
		String checkindate = request.getParameter("checkindate");
		session.setAttribute("checkindate", checkindate); 
		String checkoutdate = request.getParameter("checkoutdate");
		session.setAttribute("checkoutdate", checkoutdate); 
		logger.debug("Hotel ID: " + hotelID +" Check-in Date: " + checkindate
				+" Check-out Date: " + checkoutdate);
		
		List<Room> rooms1 = client.queryVacantrooms(hotelID, "2015-05-" + checkindate, "2015-05-" + checkoutdate, 1);
		List<Room> rooms2 = client.queryVacantrooms(hotelID, "2015-05-" + checkindate, "2015-05-" + checkoutdate, 2);
		
		if (rooms2 != null && rooms1 != null){
			logger.debug("From RMI Server and CORBA Server, Vacant Rooms Number: "+ rooms1.size()+rooms2.size());
			request.setAttribute("rooms1", rooms1);
			request.setAttribute("rooms2", rooms2);
		} else if (rooms1 != null && rooms2 == null) {
			logger.debug("From RMI Server, Vacant Rooms Number: " + rooms1.size());
			request.setAttribute("rooms1", rooms1);
			request.removeAttribute("rooms2");
		} else if (rooms2 != null && rooms1 == null) {
			logger.debug("From CORBA Server, Vacant Rooms Number: " + rooms2.size());
			request.setAttribute("rooms2", rooms2);
			request.removeAttribute("rooms1");
		} else {
			request.removeAttribute("rooms1");
			request.removeAttribute("rooms2");
		}
		request.getRequestDispatcher("query.jsp").forward(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<String> cities1 = client.queryCity(1);
		List<String> cities2 = client.queryCity(2);
		cities1.addAll(cities2);
		HashSet<String> cityhashset = new HashSet<String>(cities1);
		cities1.clear();
		cities.addAll(cityhashset);
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		if (session.getAttribute("islogin") == null || !(boolean) session.getAttribute("islogin")) {
			out.println("<script type=\"text/javascript\" language=\"javascript\">");
			out.println("alert(\"Please Login first\");");
			out.println("window.document.location.href=\"index.jsp\";</script>");
		}
		session.setAttribute("cities", cities);
		response.setContentType("text/html");
		String title = "Hotel Booking System";
		out.println(ServletFunctions.headWithTitle(title));
		out.println("<BODY>");
		out.println("<H1 align=\"center\">");
		out.println("Hotel Booking System");
		out.println("</H1>");
		out.println("<br>");
		out.println("<p align=\"center\">Hello Guest: " + session.getAttribute("bookerID") + "<a href=\"index.jsp?logout=1\">&nbspLOG OUT</a></p>");
		
		out.println("<br>");
		out.println("<FORM METHOD=POST ACTION=\"/Servlet\">");
		out.println("<TABLE align=\"center\">");
		out.println("<TR><TD>Select Hotel ID:</TD>");
		out.println("<TD><select name=\"hotelID\">");
		for (int j = 0; j < 30; j++) out.println("<option value=\"" + (j + 1) + "\">" + (j + 1) + "</option>");
		out.println("</select></TD></TR>");
		out.println("<TR><TD>Select Check-in Date</TD>");
		out.println("<TD>2015-05-<select name=\"checkindate\">");
		for (int i = 0;i < 9; i++) {
			out.println("<option value=\"0" + (i + 1) + "\">" + (i + 1) + "</option>");
		}
		for (int i = 9;i < 31;i++){
			out.println("<option value=\"" + (i + 1) + "\">" + (i + 1) + "</option>");
		}
		out.println("</select></TD></TR>");
		out.println("<TR><TD>Select Check-out Date</TD>");
		out.println("<TD>2015-05-<select name=\"checkoutdate\">");
		for (int i = 0;i < 9; i++) {
			out.println("<option value=\"0" + (i + 1) + "\">" + (i + 1) + "</option>");
		}
		for (int i = 9;i < 31;i++){
			out.println("<option value=\"" + (i + 1) + "\">" + (i + 1) + "</option>");
		}
        out.println("</select></TD></TR>");
        out.println("</TABLE>");
        out.println("<TABLE BORDER=\"1\" align=\"center\">");
        out.println("<TR><TD>City Supported by this System</TD></TR>");
        out.println("<TR><TD><div>");
        for(String city:cities)out.println(city);
        cities.clear();
        out.println("</div></TD></TR>");
        out.println("</TD></TR>");
        out.println("</TABLE>");
        out.println("<div style=\"text-align:center\">");
        out.println("<input type=\"submit\" value=\"QUERY\">");
        out.println("</div>");
        out.println("</FORM></BODY></HTML>");
	}
}
