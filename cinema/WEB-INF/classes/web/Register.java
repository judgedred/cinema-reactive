package web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class Register extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
		dispatcher.forward(request, response);		
	}
}
