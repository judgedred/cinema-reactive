package com.web;

import com.dao.*;
import com.domain.*;
import com.mysql.MySqlFilmshowDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.PrintWriter;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Controller
public class Main extends HttpServlet
{
    @Autowired
    private MySqlFilmshowDao filmshowDao;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Test</title></head>");
        out.println("<body>");
        out.println("<h1>Testing the Servlet</h1>");
//        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
//        filmshowDao = context.getBean("mySqlFilmshowDao", MySqlFilmshowDao.class);

//		HttpSession session = request.getSession();
		try
			{												
//	 			FilmshowDao filmshowDao = (FilmshowDao)session.getAttribute("filmshowDao");
	 			List<Filmshow> ls = filmshowDao.getFilmshowAll();
//				session.setAttribute("filmshowList", ls);

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

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }
}

