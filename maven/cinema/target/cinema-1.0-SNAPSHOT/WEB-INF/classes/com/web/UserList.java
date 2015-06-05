package com.web;

import com.dao.*;
import com.domain.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class UserList extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		try
		{
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			List<User> ls = userDao.getUserAll();
			session.setAttribute("UserList", ls);
		}
		catch(Exception e)
		{	
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
		dispatcher.forward(request, response);
	}
}