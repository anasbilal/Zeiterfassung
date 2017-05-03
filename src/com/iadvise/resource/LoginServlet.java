package com.iadvise.resource;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iadvise.entities.User;
import com.iadvise.entities.UserRole;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5963250476147668440L;
	UserResource usr = new UserResource();
	User user;
	ArrayList<String> rollen = new ArrayList<>();

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = null;
		Iterator<UserRole> itr = null;
		//rollen = null;
		String rolle = null;
		PrintWriter out = response.getWriter();
		// get request parameters for userID and password
		String username = request.getParameter("username");
		String pwd = request.getParameter("loginpassword");
		System.out.println("Username/Passw: " + username + "/" + pwd);
		try {
			user = usr.findByName(username);
			System.out.println("User: " + user.toString());

		} catch (NullPointerException e) {
			rd = getServletContext().getRequestDispatcher("/registration.html");
			out.println("<font color=red>User existiert nicht</font>\n");
			rd.include(request, response);
		}

		if (user != null) {
			if (user.getUsername().equals(username)	&& user.getPasswort().equals(pwd)) {
				rollen.clear();
				itr=user.getUserRolesCollection().iterator();
				while(itr.hasNext()){
					UserRole element = (UserRole) itr.next();
					//System.out.println("Userrollen: "+element.getAuthority());
					rolle = element.getAuthority();
					rollen.add(rolle);
			}
				System.out.println("ROLLEN zum USER: "+rollen.toString());
								
				Cookie userCookie = new Cookie("username", username);
				Cookie roleCookie = new Cookie("userrollen",rollen.toString());
				// setting cookie to expiry in 60 mins
				userCookie.setMaxAge(60 * 60);
				roleCookie.setMaxAge(60 * 60);
				response.addCookie(userCookie);
				response.addCookie(roleCookie);
				response.sendRedirect("Zeitbuchung.jsp");
			} else {
				rd = getServletContext().getRequestDispatcher("/registration.html");
				out.println("<font color=red>Please make sure you enter UserID/Pass</font>\n");
				rd.include(request, response);
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}

}
