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

        if(url.equals("/Admin/AddFilm"))
        {
            try
            {
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddFilm.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteFilm"))
        {
            try
            {
                List<Film> filmLs = filmDao.getFilmAll();
                session.setAttribute("filmList", filmLs);
                int filmId = Integer.parseInt(request.getParameter("film-select"));
                Film film = filmDao.getFilmById(filmId);
                if(film != null)
                {
                    filmDao.delete(film);
                }
                filmLs = filmDao.getFilmAll();
                session.setAttribute("filmList", filmLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteFilm.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/FilmList"))
        {
            try
            {
                List<Film> filmLs = filmDao.getFilmAll();
                session.setAttribute("filmList", filmLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/FilmList.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/AddFilmshow"))
        {
            try
            {
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddFilmshow.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteFilmshow"))
        {
            try
            {
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteFilmshow.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/FilmshowList"))
        {
            try
            {
                List<Filmshow> ls = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", ls);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher("/FilmshowList.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/AddTicket"))
        {
            try
            {
                int filmshowId = 0;
                float price = 0;
                int seatId = 0;
                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", filmshowLs);
                if(request.getParameter("filmshow-select") != null && request.getParameter("ticket-add-price") != null && request.getParameter("seat-select") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                    price = Float.parseFloat(request.getParameter("ticket-add-price"));
                    seatId = Integer.parseInt(request.getParameter("seat-select"));
                }
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                Seat seat = seatDao.getSeatById(seatId);
                if(filmshow != null && price != 0 && seat != null)
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddTicket.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/AddTicketAll"))
        {
            try
            {
                int filmshowId = 0;
                float price = 0;
                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", filmshowLs);
                List<Seat> filteredSeatLs = (List<Seat>)session.getAttribute("filteredSeatList");
                if(request.getParameter("filmshow-select") != null && request.getParameter("ticket-add-price") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                    price = Float.parseFloat(request.getParameter("ticket-add-price"));
                }
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                if(filteredSeatLs != null)
                {
                    for(Seat s : filteredSeatLs)
                    {
                        if(filmshow != null && price != 0)
                        {
                            Ticket ticket = new Ticket();
                            ticket.setFilmshow(filmshow);
                            ticket.setPrice(price);
                            ticket.setSeat(s);
                            ticketDao.create(ticket);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddTicketAll.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteTicket"))
        {
            try
            {
                int ticketId = 0;
                List<Ticket> ticketLs = ticketDao.getTicketAll();
                session.setAttribute("ticketList", ticketLs);
                if(request.getParameter("ticket-select") != null)
                {
                    ticketId = Integer.parseInt(request.getParameter("ticket-select"));
                }
                Ticket ticket = ticketDao.getTicketById(ticketId);
                if(ticket != null)
                {
                    ticketDao.delete(ticket);
                }
                ticketLs = ticketDao.getTicketAll();
                session.setAttribute("ticketList", ticketLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteTicket.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/TicketList"))
        {
            try
            {
                int filmshowId = 0;
                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                if(filmshowLs != null)
                {
                    session.setAttribute("filmshowList", filmshowLs);
                }
                if(request.getParameter("filmshow-select") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                }
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
                    session.setAttribute("filteredTicketList", filteredLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/TicketList.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/AddReservation"))
        {
            try
            {
                int ticketId = 0;
                int userId = 0;
                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                List<User> userLs = userDao.getUserAll();
                session.setAttribute("filmshowList", filmshowLs);
                session.setAttribute("userList", userLs);
                if(request.getParameter("ticket-select") != null && request.getParameter("user-select") != null)
                {
                    ticketId = Integer.parseInt(request.getParameter("ticket-select"));
                    userId = Integer.parseInt(request.getParameter("user-select"));
                }
                Ticket ticket = ticketDao.getTicketById(ticketId);
                User user = userDao.getUserById(userId);
                if(user != null && ticket != null)
                {
                    Reservation reservation = new Reservation();
                    reservation.setUser(user);
                    reservation.setTicket(ticket);
                    reservationDao.create(reservation);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddReservation.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteReservation"))
        {
            try
            {
                int reservationId = 0;
                List<Reservation> reservationLs = reservationDao.getReservationAll();
                session.setAttribute("reservationList", reservationLs);
                if(request.getParameter("reservation-select") != null)
                {
                    reservationId = Integer.parseInt(request.getParameter("reservation-select"));
                }
                Reservation reservation = reservationDao.getReservationById(reservationId);
                if(reservation != null)
                {
                    reservationDao.delete(reservation);
                }
                reservationLs = reservationDao.getReservationAll();
                session.setAttribute("reservationList", reservationLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteReservation.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/ReservationList"))
        {
            try
            {
                int userId = 0;
                List<User> userLs = userDao.getUserAll();
                if(userLs != null)
                {
                    session.setAttribute("userList", userLs);
                }
                if(request.getParameter("user-select") != null)
                {
                    userId = Integer.parseInt(request.getParameter("user-select"));
                }
                User user = userDao.getUserById(userId);
                List<Reservation> reservationLs = reservationDao.getReservationAll();
                List<Reservation> filteredLs = new LinkedList<>();
                if(user != null && reservationLs != null)
                {
                    for(Reservation r : reservationLs)
                    {
                        if(r.getUser().equals(user))
                        {
                            filteredLs.add(r);
                        }
                    }
                    session.setAttribute("filteredReservationList", filteredLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ReservationList.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/AddSeat"))
        {
            try
            {
                int hallId = 0;
                int rowNumber = 0;
                int seatNumber = 0;
                List<Hall> hallLs = hallDao.getHallAll();
                session.setAttribute("hallList", hallLs);
                if(request.getParameter("hall-select") != null && request.getParameter("seat-add-row") != null && request.getParameter("seat-add-number") != null)
                {
                    hallId = Integer.parseInt(request.getParameter("hall-select"));
                    rowNumber = Integer.parseInt(request.getParameter("seat-add-row"));
                    seatNumber = Integer.parseInt(request.getParameter("seat-add-number"));
                }
                Hall hall = hallDao.getHallById(hallId);
                if(hall != null && rowNumber != 0 && seatNumber != 0)
                {
                    Seat seat = new Seat();
                    seat.setHall(hall);
                    seat.setRowNumber(rowNumber);
                    seat.setSeatNumber(seatNumber);
                    seatDao.create(seat);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddSeat.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteSeat"))
        {
            try
            {
                List<Seat> seatLs = seatDao.getSeatAll();
                session.setAttribute("seatList", seatLs);
                int seatId = Integer.parseInt(request.getParameter("seat-select"));
                Seat seat = seatDao.getSeatById(seatId);
                if(seat != null)
                {
                    seatDao.delete(seat);
                }
                seatLs = seatDao.getSeatAll();
                session.setAttribute("filmshowList", seatLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteSeat.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/SeatList"))
        {
            try
            {
                List<Seat> seatls = seatDao.getSeatAll();
                session.setAttribute("seatList", seatls);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/SeatList.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/AddHall"))
        {
            try
            {
                int hallNumber = Integer.parseInt(request.getParameter("hall-add-number"));
                String hallName = request.getParameter("hall-add-name");
                if(hallNumber != 0 && hallName != null)
                {
                    Hall hall = new Hall();
                    hall.setHallNumber(hallNumber);
                    hall.setHallName(hallName);
                    hallDao.create(hall);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddHall.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteHall"))
        {
            try
            {
                List<Hall> hallLs = hallDao.getHallAll();
                session.setAttribute("hallList", hallLs);
                int hallId = Integer.parseInt(request.getParameter("hall-select"));
                Hall hall = hallDao.getHallById(hallId);
                if(hall != null)
                {
                    hallDao.delete(hall);
                }
                hallLs = hallDao.getHallAll();
                session.setAttribute("hallList", hallLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteHall.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/HallList"))
        {
            try
            {
                List<Hall> hallls = hallDao.getHallAll();
                session.setAttribute("hallList", hallls);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/HallList.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Register"))
        {
            try
            {
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

        if(url.equals("/Admin/AddUser"))
        {
            try
            {
                User user = new User();
                String login = request.getParameter("user-add-login");
                String password = request.getParameter("user-add-password");
                String email = request.getParameter("user-add-email");

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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/AddUser.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/DeleteUser"))
        {
            try
            {
                List<User> userLs = userDao.getUserAll();
                session.setAttribute("userList", userLs);
                int userId = Integer.parseInt(request.getParameter("user-select"));
                User user = userDao.getUserById(userId);
                if(user != null)
                {
                    userDao.delete(user);
                }
                userLs = userDao.getUserAll();
                session.setAttribute("userList", userLs);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteUser.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/Admin/UserList"))
        {
            try
            {
                List<User> ls = userDao.getUserAll();
                session.setAttribute("userList", ls);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UserList.jsp");
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

        if(url.equals("/Admin/Login"))
        {
            try
            {
                List<User> ls = userDao.getUserAll();
                String login = request.getParameter("login-auth");
                String password = request.getParameter("password-auth");

                if(login.equals("admin") && password.equals("admin"))
                {

                    for(User u : ls)
                    {
                        if(u.getLogin().equals(login) && u.getPassword().equals(password))
                        {
                            session.setAttribute("adminUser", u);
                            RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminMain.jsp");
                            dispatcher.forward(request, response);
                        }
                    }
                }
                else
                {
                    response.getWriter().print("Access denied");
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/ReserveTicket"))
        {
            try
            {
                int ticketId = 0;
                int filmshowId = 0;
                Filmshow filmshow;

                if(request.getParameter("ticket-select") != null)
                {
                    ticketId = Integer.parseInt(request.getParameter("ticket-select"));
                }
                Ticket ticket = ticketDao.getTicketById(ticketId);
                User user = (User)session.getAttribute("validUser");

                if(user != null && ticket != null)
                {
                    Reservation reservation = new Reservation();
                    reservation.setUser(user);
                    reservation.setTicket(ticket);
                    reservationDao.create(reservation);
                }

                if(request.getParameter("filmshow-select") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                }

                if(filmshowId != 0)
                {
                    filmshow = filmshowDao.getFilmshowById(filmshowId);
                    session.setAttribute("filmshowReserve", filmshow);
                }
                    List<Ticket> ticketLs = ticketDao.getTicketAll();
                    List<Reservation> reservationLs = reservationDao.getReservationAll();
                    boolean ticketFree;
                    List<Ticket> filteredTicketLs = new LinkedList<>();
                    filmshow = (Filmshow)session.getAttribute("filmshowReserve");
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/ReserveTicket.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/ReservationList"))
        {
            try
            {
                User user = (User)session.getAttribute("validUser");
                List<Reservation> reservationLs = reservationDao.getReservationAll();
                List<Reservation> filteredLs = new LinkedList<>();
                if(user != null && reservationLs != null)
                {
                    for(Reservation r : reservationLs)
                    {
                        if(r.getUser().equals(user))
                        {
                            filteredLs.add(r);
                        }
                    }
                    session.setAttribute("filteredReservationList", filteredLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UserReservationList.jsp");
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

