package com.web;

import com.dao.*;
import com.domain.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class AddFilm extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		HttpSession session = request.getSession();
		try
		{
			FilmDao filmDao = (FilmDao)session.getAttribute("filmDao");
			String filmName = request.getParameter("filmName");
			String description = request.getParameter("description");
			Film film = new Film();
			if(filmName != null && !filmName.isEmpty() && description != null && !description.isEmpty())
			{
				film.setFilmName(filmName);
				film.setDescription(description);
				filmDao.create(film);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("AddFilm.jsp");
		dispatcher.forward(request, response);
	}
}