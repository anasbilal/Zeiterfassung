package com.iadvise.resource;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name= "LogoutServlet",urlPatterns= {"/logout"})
public class LogoutServlet extends HttpServlet {  
        /**
	 * 
	 */
	private static final long serialVersionUID = -1605827484817394662L;
			
		protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {  
			response.setContentType("text/html");
			Cookie loginCookie = null;
	        Cookie[] cookies = request.getCookies();
	        if (cookies != null) {
	            for (Cookie cookie : cookies) {
	                if (cookie.getName().equals("username")) {
	                    loginCookie = cookie;
	                    break;
	                }
	            }
	        }
	        if (loginCookie != null) {
	            loginCookie.setMaxAge(0);
	            response.addCookie(loginCookie);
	        }
			HttpSession session= request.getSession();
			session.invalidate();
			ServletContext context= getServletContext();
			RequestDispatcher rd= context.getRequestDispatcher("/registration.html");
			rd.include(request, response);
	    } 
		
		public void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
				doGet(request, response);
			
		}
}  
