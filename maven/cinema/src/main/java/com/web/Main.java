package com.web;

import com.dao.*;
import com.domain.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;


public class Main extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		try
			{												
	 			FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");		
	 			List<Filmshow> ls = filmshowDao.getFilmshowAll();
				session.setAttribute("filmshowList", ls);					
			}												
			catch(Exception e)										
			{												
		 		e.printStackTrace();
			}
        RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
		dispatcher.forward(request, response);
	}
}

