package web;

import dao.*;
import domain.*;
import mysql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Register extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		response.setContentType("text/html; charset = utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Register</title></head>");
		out.println("<body>");
		out.println("<form action=Register method=GET>");
		out.println("<p>Введите логин <input type=text name=login></p>");
		out.println("<p>Введите пароль <input type=text name=password></p>");
		out.println("<p>Введите email <input type=text name=email></p>");
		out.println("<p><input type=submit value=Зарегистрироваться></p>");
		out.println("</form>");
		out.println("<p><a href=http://localhost:8080/cinema/UserList>Посмотреть список</a></p>");
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

		out.println("</body>");
		out.println("</html>");
		out.close();
		session.invalidate();

		
	}
}
