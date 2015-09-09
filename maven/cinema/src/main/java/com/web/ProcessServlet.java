package com.web;

import com.domain.*;
import com.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;


@Controller
public class ProcessServlet extends HttpServlet
{
    @Autowired
    private MySqlUserDao userDao;

    @Autowired
    private MySqlSeatDao seatDao;

    @Autowired
    private MySqlFilmshowDao filmshowDao;

    @Autowired
    private MySqlTicketDao ticketDao;

    @Autowired
    private MySqlReservationDao reservationDao;

    @Autowired
    private MySqlFilmDao filmDao;

    @Autowired
    private MySqlHallDao hallDao;

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
                String login = request.getParameter("login-reg");
                String email = request.getParameter("email-reg");
                List<User> ls = userDao.getUserAll();
                Boolean loginFree = true;
                Boolean emailFree = true;
                if(login != null && !login.isEmpty() || email != null && !email.isEmpty())
                {
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
                List<User> ls = userDao.getUserAll();
                String login = request.getParameter("login-auth");
                String password = request.getParameter("password-auth");
                if(password != null)
                {
                    MessageDigest digest = MessageDigest.getInstance("SHA-1");
                    digest.reset();
                    byte[] hash = digest.digest(password.getBytes("UTF-8"));
                    String passwordHash = DatatypeConverter.printHexBinary(hash);

                    if(login != null && !login.isEmpty() && passwordHash != null && !passwordHash.isEmpty())
                    {
                        for(User u : ls)
                        {
                            if(u.getLogin().equals(login) && u.getPassword().toUpperCase().equals(passwordHash))
                            {
                                session.setAttribute("validUser", u);
                                break;
                            }
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
                int filmshowId = 0;
                List<Seat> seatLs = seatDao.getSeatAll();
                List<Ticket> ticketLs = ticketDao.getTicketAll();
                boolean seatFree;
                if(request.getParameter("filmshow-select") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                }
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                List<Seat> filteredSeatLs = new LinkedList<>();
                if(filmshow != null)
                {
                    for(Seat s : seatLs)
                    {
                        seatFree = true;
                        if(s.getHall().equals(filmshow.getHall()))
                        {
                            for(Ticket t : ticketLs)
                            {
                                if(t.getFilmshow().equals(filmshow) && t.getSeat().equals(s))
                                {
                                    seatFree = false;
                                    break;
                                }
                            }
                            if(seatFree)
                            {
                                filteredSeatLs.add(s);
                            }
                        }
                    }
                }
                session.setAttribute("filteredSeatList", filteredSeatLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/TicketCheck"))
        {
            try
            {
                List<Reservation> reservationLst = reservationDao.getReservationAll();
                if(request.getParameter("ticket-select") != null)
                {
                    int ticketId = Integer.parseInt(request.getParameter("ticket-select"));
                    Ticket ticket = ticketDao.getTicketById(ticketId);
                    boolean ticketFree = true;
                    if(ticket != null)
                    {
                        for(Reservation r : reservationLst)
                        {
                            if(r.getTicket().equals(ticket))
                            {
                                ticketFree = false;
                                break;
                            }
                        }
                        if(!ticketFree)
                        {
                            response.getWriter().print("Билет зарезервирован. Сначала удалите бронь.");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/TicketsFilter"))
        {
            try
            {
                int filmshowId = 0;
                List<Ticket> ticketLs = ticketDao.getTicketAll();
                List<Reservation> reservationLs = reservationDao.getReservationAll();
                boolean ticketFree;
                if(request.getParameter("filmshow-select") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                }
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                List<Ticket> filteredTicketLs = new LinkedList<>();
                if(filmshow != null)
                {
                    for(Ticket t : ticketLs)
                    {
                        if(t.getFilmshow().equals(filmshow))
                        {
                            ticketFree = true;
                            for(Reservation r : reservationLs)
                            {
                                if(t.equals(r.getTicket()))
                                {
                                    ticketFree = false;
                                    break;
                                }
                            }
                            if(ticketFree)
                            {
                                filteredTicketLs.add(t);
                            }
                        }
                    }
                }
                session.setAttribute("filteredTicketList", filteredTicketLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/FilmCheck"))
        {
            try
            {
                List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
                if(request.getParameter("film-select") != null)
                {
                    int filmId = Integer.parseInt(request.getParameter("film-select"));
                    Film film = filmDao.getFilmById(filmId);
                    boolean filmFree = true;
                    if(film != null)
                    {
                        for(Filmshow f : filmshowLst)
                        {
                            if(f.getFilm().equals(film))
                            {
                                filmFree = false;
                                break;
                            }
                        }
                        if(!filmFree)
                        {
                            response.getWriter().print("На фильм создан сеанс. Сначала удалите сеанс.");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/FilmshowCheck"))
        {
            try
            {
                List<Ticket> ticketLst = ticketDao.getTicketAll();
                if(request.getParameter("filmshow-select") != null)
                {
                    int filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                    Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                    boolean filmshowFree = true;
                    if(filmshow != null)
                    {
                        for(Ticket t : ticketLst)
                        {
                            if(t.getFilmshow().equals(filmshow))
                            {
                                filmshowFree = false;
                                break;
                            }
                        }
                        if(!filmshowFree)
                        {
                            response.getWriter().print("На сеанс имеются билеты. Сначала удалите билеты.");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/HallCheck"))
        {
            try
            {
                List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
                List<Seat> seatLst = seatDao.getSeatAll();
                if(request.getParameter("hall-select") != null)
                {
                    int hallId = Integer.parseInt(request.getParameter("hall-select"));
                    Hall hall = hallDao.getHallById(hallId);
                    boolean hallFreeFilmshow = true;
                    boolean hallFreeSeat = true;
                    if(hall != null)
                    {
                        for(Filmshow f : filmshowLst)
                        {
                            if(f.getHall().equals(hall))
                            {
                                hallFreeFilmshow = false;
                                break;
                            }
                            for(Seat s : seatLst)
                            {
                                if(s.getHall().equals(hall))
                                {
                                    hallFreeSeat = false;
                                    break;
                                }
                            }
                        }
                        if(!hallFreeFilmshow)
                        {
                            response.getWriter().print("В зале имеются сеансы. Сначала удалите сеансы.");
                        }
                        if(!hallFreeSeat)
                        {
                            response.getWriter().print("В зале имеются места. Сначала удалите места.");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/SeatCheck"))
        {
            try
            {
                List<Ticket> ticketLst = ticketDao.getTicketAll();
                if(request.getParameter("seat-select") != null)
                {
                    int seatId = Integer.parseInt(request.getParameter("seat-select"));
                    Seat seat = seatDao.getSeatById(seatId);
                    boolean seatFree = true;
                    if(seat != null)
                    {
                        for(Ticket t : ticketLst)
                        {
                            if(t.getSeat().equals(seat))
                            {
                                seatFree = false;
                                break;
                            }
                        }
                        if(!seatFree)
                        {
                            response.getWriter().print("На место имеется билет. Сначала удалите билет.");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/UserCheck"))
        {
            try
            {
                List<Reservation> reservationLst = reservationDao.getReservationAll();
                if(request.getParameter("user-select") != null)
                {
                    int userId = Integer.parseInt(request.getParameter("user-select"));
                    User user = userDao.getUserById(userId);
                    boolean userFree = true;
                    if(user != null)
                    {
                        for(Reservation r : reservationLst)
                        {
                            if(r.getUser().equals(user))
                            {
                                userFree = false;
                                break;
                            }
                        }
                        if(!userFree)
                        {
                            response.getWriter().print("У пользователя есть брони. Сначала удалите бронь.");
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/AuthCheck"))
        {
            try
            {
                User user = (User)session.getAttribute("validUser");
                if(user == null)
                {
                    response.getWriter().print("Войдите в систему");
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
