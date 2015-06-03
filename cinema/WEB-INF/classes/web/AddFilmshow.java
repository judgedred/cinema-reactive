package web;

import dao.*;
import domain.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.text.SimpleDateFormat;


public class AddFilmshow extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		try
		{
			FilmDao filmDao = (FilmDao)session.getAttribute("filmDao");
			List<Film> filmList = filmDao.getFilmAll();
			HallDao hallDao = (HallDao)session.getAttribute("hallDao");
			List<Hall> hallList = hallDao.getHallAll();
			FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");
			session.setAttribute("filmList", filmList);
			session.setAttribute("hallList", hallList);
			int filmId = Integer.parseInt(request.getParameter("filmSelect"));
			int hallId = Integer.parseInt(request.getParameter("hallSelect"));
			Date dateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").parse(request.getParameter("dateTime"));
			Film film = filmDao.getFilmById(filmId);
			Hall hall = hallDao.getHallById(hallId);
			Filmshow filmshow = new Filmshow();
			if(film != null && hall != null && dateTime != null)
			{
				filmshow.setFilm(film);
				filmshow.setHall(hall);
				filmshow.setDateTime(dateTime);
				filmshowDao.create(filmshow);	
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("AddFilmshow.jsp");
		dispatcher.forward(request, response);
	}
}