/**
 * 
 */
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import util.Constants;
import util.EncryptedMsg;
import client.RMIClient;

/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class Register extends HttpServlet {
	
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
		String bookerID = (String) request.getParameter("bookerID");
		String bookerName = (String) request.getParameter("bookerName");
		String telePhone = (String) request.getParameter("telePhone");
		String passwd = (String) request.getParameter("passwd");
		String email = (String) request.getParameter("email");
		
		HttpSession session = request.getSession();
		logger.debug("Booker ID: "+ bookerID + " Booker Name: " + bookerName
				+ " Telephone: " + telePhone +" Password: "+ passwd + " Email: "
						+ email);
		if ("".equals(bookerID) || "".equals(bookerName) || "".equals(telePhone) || "".equals(passwd)||"".equals(email)){
			request.setAttribute("islogsuc", false);
			request.getRequestDispatcher("register.jsp").forward(request, response);
		} else {
			if (client.insertBookers(bookerID, bookerName, telePhone, email, passwd)) {
				session.setAttribute("islogin", true);
				session.setAttribute("bookerID", bookerID);
				session.setAttribute("bookerName", bookerName);
				session.setAttribute("telePhone", telePhone);
				session.setAttribute("email", email);
				
				response.sendRedirect("/Servlet");
				return;
			} else {
				request.setAttribute("islogsuc", false);
				request.getRequestDispatcher("register.jsp").forward(request, response);
			}
		}
		//super.doPost(request, response);
	}
		
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//super.doGet(request, response);
	}
	

}
