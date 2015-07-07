package com.web;

import com.dao.*;
import com.domain.*;
import com.mysql.MySqlUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Controller
public class UserList extends HttpServlet
{
    @Autowired
    private MySqlUserDao userDao;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		try
		{
            session.setAttribute("userDao", userDao);
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

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }
}