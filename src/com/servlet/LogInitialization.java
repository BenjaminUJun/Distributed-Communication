package servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
/**
 * @author Yu Jun 2015/5/16.
 *
 */



public class LogInitialization extends HttpServlet {

	public LogInitialization() {
		// TODO Auto-generated constructor stub
		super();
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		String fileString = getInitParameter("log4j");
		super.init();
	}

}
