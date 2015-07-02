package com.web;

import com.dao.*;
import com.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.PrintWriter;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Controller
public class Main extends HttpServlet
{
    @Autowired
    private FilmshowDao filmshowDao;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Test</title></head>");
        out.println("<body>");
        out.println("<h1>Testing the Servlet</h1>");

		HttpSession session = request.getSession();
		try
			{												
//	 			FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");
	 			List<Filmshow> ls = filmshowDao.getFilmshowAll();
				session.setAttribute("filmshowList", ls);

                for(Iterator<Filmshow> i = ls.iterator(); i.hasNext(); )
                {
                    Filmshow f = i.next();
                    out.println("<h2>inside iterator</h2>" + " " + f);

                }
			}												
			catch(Exception e)										
			{												
		 		e.printStackTrace();
			}
        out.println("</body></html>");
        out.close();
//        RequestDispatcher dispatcher = request.getRequestDispatcher("Main.jsp");
//		dispatcher.forward(request, response);
	}
}

