package servlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Yu Jun 2015/5/16.
 *
 */
public class ServletFunctions {
	public static final int SECONDS_PER_MONTH = 60 * 60 * 24 * 30;
	public static final int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;
	public static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD"
			+ " HTML 4.0 Transitional//EN\">";
	
	public static String headWithTitle(String title) {
		return (DOCTYPE + "\n" + "<HTML>\n" + "<HEAD><TITLE>" + title + "</TITLE><HEAD>\n");
	}
	
	public static int getIntParameter(HttpServletRequest request, String paramName, int defaultValue) {
		String paramString = request.getParameter(paramName);
		int paramValue;
		try {
			paramValue = Integer.parseInt(paramString);
		} catch (NumberFormatException e) {
			paramValue = defaultValue;
		}
		return paramValue;
	}
	
	public static String getCookieValue (Cookie[] cookies, String cookieName,
			String defaultValue) {
	    for (int i = 0; i < cookies.length; i++) {
	        Cookie cookie = cookies[i];
	        if (cookieName.equals(cookie.getName()))
	          return cookie.getValue();
	      }
	      return defaultValue;
	}
	public static void main(String[] args) {
	//	System.out.println(headWithTitle("123"));
	}
	
}
