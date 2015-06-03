package web;

import dao.*;
import domain.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class Register extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{	
		HttpSession session = request.getSession();
		try
		{
			UserDao userDao = (UserDao)session.getAttribute("userDao");
			User user = new User();
			String login = request.getParameter("login");
			String password = request.getParameter("password");
			String email = request.getParameter("email");
			if(login != null && !login.isEmpty() && password != null && !password.isEmpty() && email != null && !email.isEmpty())
			{
				user.setLogin(login);
				user.setPassword(password);
				user.setEmail(email);
				userDao.create(user);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
		dispatcher.forward(request, response);		
	}
}
