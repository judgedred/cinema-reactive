package com.web;

import com.domain.User;
import com.mysql.MySqlUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@Controller
public class AdminServlet extends HttpServlet
{
    @Autowired
    private MySqlUserDao userDao;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String url = request.getPathInfo();
        HttpSession session = request.getSession();
        boolean userValid = false;
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print("Access denied");


        if(url.equals("/Login"))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("Main");
            dispatcher.forward(request, response);
            try
            {
                session.setAttribute("userDao", userDao);


                List<User> ls = userDao.getUserAll();
                String login = request.getParameter("login-auth");
                String password = request.getParameter("password-auth");
                response.getWriter().print(login);
                response.getWriter().print(password);


                if(login.equals("admin") && password.equals("admin"))
                {

                    for(User u : ls)
                    {
                        if(u.getLogin().equals(login) && u.getPassword().equals(password))
                        {
                            userValid = true;


                            /*RequestDispatcher dispatcher = request.getRequestDispatcher("AdminMain.jsp");
                            dispatcher.forward(request, response);*/
                        }
                    }
                }
                else
                {
                    response.getWriter().print("Access denied");
                }
                if(userValid)
                {
                    /*RequestDispatcher dispatcher = request.getRequestDispatcher("AdminMain.jsp");
                    dispatcher.forward(request, response);*/
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }
}
