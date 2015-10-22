package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import util.Constants;
import util.RegularExpression;
import client.RMIClient;

/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class Booking extends HttpServlet {
	private RMIClient client = null;
	private Logger logger = Logger.getLogger(Booking.class);

	public void init() {
		try {
			super.init();
			String path = getServletContext().getRealPath("/") + Constants.SERVDIR;
			client = new RMIClient(path);
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		String brand = (String) request.getParameter("brand");
		String hotelID = (String) request.getParameter("hotelID");
		String roomID = (String) request.getParameter("roomID");
		String checkinDate = (String) request.getParameter("checkindate");
		String checkoutDate = (String) request.getParameter("checkoutdate");
		String proxy = (String) request.getParameter("proxy");
		//HttpSession session = request.getSession();
		String bookerID = (String) request.getParameter("bookerID");
		String creditNO = (String) request.getParameter("creditNO");
		logger.debug("Brand: " + brand+ " Hotel ID: "+ hotelID + " Room ID: "+ roomID+
				" Check-in Date: "+ checkinDate +" Check-out Date: " +checkoutDate+
				" Booker ID: "+ bookerID+ " Credit Number: "+ creditNO +" Server Type: " + proxy);
		
		if ("".equals(creditNO)||(RegularExpression.checkDate(checkinDate, checkoutDate)==false)){
			request.setAttribute("status", 0);
			request.getRequestDispatcher("result.jsp").forward(request, response);
		} else {
		String result = client.booking(checkinDate, checkoutDate, hotelID, roomID, bookerID, creditNO, brand, "corba".equalsIgnoreCase(proxy)?2:1);
		logger.debug("Booking Result: " + result);
		
		if (Constants.BOOKSUCCESSFUL.equals(result)  ) {
			request.setAttribute("status", 1);
			request.getRequestDispatcher("result.jsp").forward(request, response);
		} else {
			request.setAttribute("status", 0);
			request.getRequestDispatcher("result.jsp").forward(request, response);
		}
		}
		
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(request, response);
	}
	
}
