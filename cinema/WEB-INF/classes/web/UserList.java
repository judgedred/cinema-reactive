package web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class UserList extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
		dispatcher.forward(request, response);
	}
}