package web;

import dao.*;
import domain.*;
import mysql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class UserList extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>UserList</title></head>");
		out.println("<body>");
		HttpSession session = request.getSession();
		try
		{
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			List<User> ls = userDao.getUserAll();
			out.println("<p>UserList</p>");
			for(Iterator<User> i = ls.iterator(); i.hasNext(); )
			{
				User user = i.next();
				out.println("<p>" + user + "</p>");
			}
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
		session.invalidate();
	}
}