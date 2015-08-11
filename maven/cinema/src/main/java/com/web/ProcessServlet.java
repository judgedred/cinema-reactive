package com.web;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.User;
import com.mysql.MySqlFilmshowDao;
import com.mysql.MySqlSeatDao;
import com.mysql.MySqlUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Controller
public class ProcessServlet extends HttpServlet
{
    @Autowired
    private MySqlUserDao userDao;

    @Autowired
    private MySqlSeatDao seatDao;

    @Autowired
    private MySqlFilmshowDao filmshowDao;

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        HttpSession session = request.getSession();
        String url = request.getPathInfo();
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");

        if(url.equals("/RegisterCheck"))
        {
            try
            {
                session.setAttribute("userDao", userDao);

                String login = request.getParameter("login-reg");
                String email = request.getParameter("email-reg");
                List<User> ls = userDao.getUserAll();
                Boolean loginFree = true;
                Boolean emailFree = true;
                for(User u : ls)
                {
                    if(u.getLogin().equals(login))
                    {
                        loginFree = false;
                    }


                    if(u.getEmail().equals(email))
                    {
                        emailFree = false;
                    }

                }
                if(login != null)
                {
                    if(loginFree)
                    {
                        response.getWriter().print("Логин свободен");
                    }
                    else
                    {
                        response.getWriter().print("Логин занят");
                    }
                }
                if(email != null)
                {
                    if(!emailFree)
                    {
                        response.getWriter().print("Логин с таким email уже есть");
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/LoginCheck"))
        {
            try
            {
                session.setAttribute("userDao", userDao);

                List<User> ls = userDao.getUserAll();
                String login = request.getParameter("login-auth");
                String password = request.getParameter("password-auth");

                if(login != null && !login.isEmpty() && password != null && !password.isEmpty())
                {
                    for(User u : ls)
                    {
                        if(u.getLogin().equals(login) && u.getPassword().equals(password))
                        {
                            session.setAttribute("validUser", u);
                        }
                    }
                }

                User validUser = (User) session.getAttribute("validUser");
                if(validUser != null)
                {
                    response.getWriter().print(validUser.getLogin());

                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/Logout"))
        {
            session.invalidate();
        }

        if(url.equals("/SeatsFilter"))
        {
            try
            {
                session.setAttribute("seatDao", seatDao);

                List<Seat> seatLs = seatDao.getSeatAll();
                int filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                List<Seat> filteredLs = new LinkedList<Seat>();
                for(Seat s : seatLs)
                {
                    if(s.getHall() == filmshow.getHall())
                    {
                        filteredLs.add(s);
                    }
                }
                session.setAttribute("filteredSeatList", filteredLs);
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
