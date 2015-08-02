package com.web;

import com.domain.*;
import com.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

@Controller
public class Main extends HttpServlet
{
    @Autowired
    private MySqlFilmshowDao filmshowDao;

    @Autowired
    private MySqlFilmDao filmDao;

    @Autowired
    private MySqlUserDao userDao;

    @Autowired
    private MySqlHallDao hallDao;

    @Autowired
    private MySqlReservationDao reservationDao;

    @Autowired
    private MySqlSeatDao seatDao;

    @Autowired
    private MySqlTicketDao ticketDao;

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        String url = request.getServletPath();
        HttpSession session = request.getSession();

        if(url.equals("/Main"))
        {
            try
            {
                session.setAttribute("filmshowDao", filmshowDao);
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

        if(url.equals("/AddFilm"))
        {
            try
            {
                session.setAttribute("filmDao", filmDao);
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

        if(url.equals("/AddFilmshow"))
        {
            try
            {
                session.setAttribute("filmshowDao", filmshowDao);
                session.setAttribute("filmDao", filmDao);
                session.setAttribute("hallDao", hallDao);

                List<Film> filmList = filmDao.getFilmAll();
                List<Hall> hallList = hallDao.getHallAll();
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

        if(url.equals("/DeleteFilmshow"))
        {
            try
            {
                session.setAttribute("filmshowDao", filmshowDao);

                List<Filmshow> ls = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", ls);
                int filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                if(filmshow != null)
                {
                    filmshowDao.delete(filmshow);
                }
                ls = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", ls);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("DeleteFilmshow.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/AddTicket"))
        {
            try
            {
                session.setAttribute("filmshowDao", filmshowDao);
                session.setAttribute("seatDao", seatDao);

                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", filmshowLs);
                List<Seat> seatLs = seatDao.getSeatAll();
                session.setAttribute("seatList", seatLs);
                int filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                Float price = Float.parseFloat(request.getParameter("ticket-add-price"));
                int seatId = Integer.parseInt(request.getParameter("seat-select"));
                Seat seat = seatDao.getSeatById(seatId);
                if(filmshow != null && price != null && seat != null)
                {
                    Ticket ticket = new Ticket();
                    ticket.setFilmshow(filmshow);
                    ticket.setPrice(price);
                    ticket.setSeat(seat);
                    ticketDao.create(ticket);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("AddTicket.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/TicketList"))
        {
            try
            {
                session.setAttribute("filmshowDao", filmshowDao);
                session.setAttribute("ticketDao", ticketDao);

                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                if(filmshowLs != null)
                {
                    session.setAttribute("filmshowList", filmshowLs);
                }
                int filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                List<Ticket> ticketLs = ticketDao.getTicketAll();
                List<Ticket> filteredLs = new LinkedList<Ticket>();
                if(filmshow != null && ticketLs != null)                // TODO http://zeroturnaround.com/rebellabs/java-8-explained-applying-lambdas-to-java-collections/
                {
                    for(Ticket t : ticketLs)
                    {
                        if(t.getFilmshow().equals(filmshow))
                        {
                            filteredLs.add(t);
                        }
                    }
                    session.setAttribute("ticketList", filteredLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("TicketList.jsp");
            dispatcher.forward(request, response);

        }

        if(url.equals("/Register"))
        {
            try
            {
                session.setAttribute("userDao", userDao);

                User user = new User();
                String login = request.getParameter("login-reg");
                String password = request.getParameter("password-reg");
                String email = request.getParameter("email-reg");

                if(login != null && !login.isEmpty() && password != null && !password.isEmpty() && email != null && !email.isEmpty())
                {
                    user.setLogin(login);
                    user.setPassword(password);
                    user.setEmail(email);
                    userDao.create(user);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("Register.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/UserList"))
        {
            try
            {
                session.setAttribute("userDao", userDao);

                List<User> ls = userDao.getUserAll();
                session.setAttribute("userList", ls);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("UserList.jsp");
            dispatcher.forward(request, response);
        }

       /* if(url.equals("/Login"))
        {
            try
            {
                session.setAttribute("userDao", userDao);

                List<User> ls = userDao.getUserAll();
                Boolean userValid = false;
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
                response.sendRedirect(request.getParameter("from"));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }*/
           /* RequestDispatcher dispatcher = request.getRequestDispatcher("ProcessServlet");
            dispatcher.forward(request, response);*/
 //       }

        if(url.equals("/Admin"))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("Admin.jsp");
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException
    {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }
}

