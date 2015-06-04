package web;

import dao.*;
import domain.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class DeleteFilmshow extends HttpServlet
{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		try
		{
			FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");
			int filmshowId = Integer.parseInt(request.getParameter("filmshowSelect"));
			Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
			if(filmshow != null)
			{
				filmshowDao.delete(filmshow);
			}
			List<Filmshow> ls = filmshowDao.getFilmshowAll();
			session.setAttribute("filmshowList", ls);
		}
		catch(Exception e)
		{
			e.printStackTrace();  
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("DeleteFilmshow.jsp");
		dispatcher.forward(request, response);
	}
}
