package web;

import dao.*;
import domain.*;
import mysql.*;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class Main extends HttpServlet
{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html; charset = utf-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Main</title></head>");
		out.println("<body>");
		out.println("<table border=1 align=center>");
		out.println("<tr>");
		out.println("<td>Новости</td>");
		out.println("<td>Сеансы</td>");
		out.println("<td>5D</td>");
		out.println("<td>Фильмы</td>");
		out.println("<td>О кинотеатре</td>");
		out.println("<td><a href=http://localhost:8080/cinema/Register>Зарегистрироваться</a></td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("<br>");
		out.println("<br>");
		out.println("<br>");
		out.println("<h2>Сегодня в кино</h2>");
		out.println("<br>");
		HttpSession session = request.getSession();
		try
		{
			
			FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");
			List<Filmshow> ls = filmshowDao.getFilmshowAll();
			for(Iterator<Filmshow> i = ls.iterator(); i.hasNext(); )
			{
				Filmshow f = i.next();
				out.println("<p>" + f + "<p>");
			} 
		}
		catch(Exception e)
		{
		 	e.printStackTrace(); 
		}
		out.println("</body></html>");
		out.close();
		session.invalidate(); 
		
	}
}

