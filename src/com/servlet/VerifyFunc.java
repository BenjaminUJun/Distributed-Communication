/**
 * 
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.Constants;
import classes.Booker;
import client.RMIClient;

/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class VerifyFunc extends HttpServlet {

	private RMIClient client = null;
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
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
		String bookerID = request.getParameter("bookerID");
		String passwd = request.getParameter("passwd");
		PrintWriter writer = response.getWriter();
		
		List<Booker> bookers = client.queryBookerinfo(bookerID);
		if ((bookers != null ) && (bookers.size()!=0) && (bookers.get(0).getPasswd().equals(passwd))){
			HttpSession session = request.getSession();
			session.setAttribute("islogin", true);
			session.setAttribute("bookerID", bookerID);
			session.setAttribute("bookerName", bookers.get(0).getBookerName());
			session.setAttribute("telePhone", bookers.get(0).getTelePhone());
			session.setAttribute("email", bookers.get(0).getEmail());
            
			request.setAttribute("isVerified", true);
			response.sendRedirect("/Servlet");
		} else {
			request.setAttribute("isVerified", false);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
		//super.doPost(request, response);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//	super.doGet(request, response);
	}

}
