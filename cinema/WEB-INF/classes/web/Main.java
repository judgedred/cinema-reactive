package web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class Main extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
		dispatcher.forward(request, response);
	}
}

