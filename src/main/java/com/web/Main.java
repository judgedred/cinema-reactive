package com.web;

import com.domain.*;
import com.mysql.*;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.stream.Collectors;

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
    @SuppressWarnings("unchecked")
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        String url = request.getServletPath();
        HttpSession session = request.getSession();
        request.setCharacterEncoding("UTF-8");

        if(url.equals("/main"))
        {
            try
            {
                List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
                List<Filmshow> filteredLst = new LinkedList<>();
                for(Filmshow f : filmshowLst)
                {
                    Date javaDate = f.getDateTime();
                    LocalDate date = new LocalDate(javaDate);
                    if(date.equals(LocalDate.now()))
                    {
                        filteredLst.add(f);
                    }
                }
                session.setAttribute("filmshowToday", filteredLst);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("main.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/filmshow"))
        {
            try
            {
                Map<LocalDate, List<Filmshow>> filmshowMap = new TreeMap<>();
                List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
                for(Filmshow f : filmshowLst)
                {
                    Date javaDate = f.getDateTime();
                    LocalDate date = new LocalDate(javaDate);
                    List<Filmshow> dateGroupedFilmshow = filmshowMap.get(date);
                    if(dateGroupedFilmshow == null)
                    {
                        dateGroupedFilmshow = new ArrayList<>();
                        filmshowMap.put(date, dateGroupedFilmshow);
                    }
                    dateGroupedFilmshow.add(f);
                }
                session.setAttribute("filmshowMap", filmshowMap);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("filmshow.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/register"))
        {
            try
            {
                User user = new User();
                String login = request.getParameter("login-reg");
                String password = request.getParameter("password-reg");
                String email = request.getParameter("email-reg");
                if(password != null)
                {
                    MessageDigest digest = MessageDigest.getInstance("SHA-1");
                    digest.reset();
                    byte[] hash = digest.digest(password.getBytes("UTF-8"));
                    String passwordHash = DatatypeConverter.printHexBinary(hash);
                    List<User> userLst = userDao.getUserAll();
                    boolean userValid = true;

                    if(login != null
                            && !login.isEmpty()
                            && passwordHash != null
                            && !passwordHash.isEmpty()
                            && email != null
                            && !email.isEmpty())
                    {
                        user.setLogin(login);
                        user.setPassword(passwordHash);
                        user.setEmail(email);
                        for(User u : userLst)
                        {
                            if(u.getLogin().equals(user.getLogin()) || u.getEmail().equals(user.getEmail()))
                            {
                                userValid = false;
                                break;
                            }
                        }
                        if(userValid)
                        {
                            userDao.create(user);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/film"))
        {
            try
            {
                List<Film> filmLst = filmDao.getFilmAll();
                session.setAttribute("filmList", filmLst);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("film.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/admin/addFilm"))
        {
            try
            {
                String filmName = request.getParameter("filmName");
                String description = request.getParameter("description");
                Film film = new Film();
                List<Film> filmLst = filmDao.getFilmAll();
                boolean filmValid = true;
                if(filmName != null && !filmName.isEmpty() && description != null && !description.isEmpty())
                {
                    film.setFilmName(filmName);
                    film.setDescription(description);
                    for(Film f : filmLst)
                    {
                        if(f.getFilmName().equals(film.getFilmName()) && f.getDescription().equals(film.getDescription()))
                        {
                            filmValid = false;
                            break;
                        }
                    }
                    if(filmValid)
                    {
                        filmDao.create(film);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addFilm.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteFilm"))
        {
            try
            {
                List<Film> filmLs = filmDao.getFilmAll();
                session.setAttribute("filmList", filmLs);
                if(request.getParameter("film-select") != null)
                {
                    int filmId = Integer.parseInt(request.getParameter("film-select"));
                    Film film = filmDao.getFilmById(filmId);
                    if(film != null)
                    {
                        filmDao.delete(film);
                    }
                    filmLs = filmDao.getFilmAll();
                    session.setAttribute("filmList", filmLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteFilm.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/filmList"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/filmList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addFilmshow"))
        {
            try
            {
                List<Film> filmList = filmDao.getFilmAll();
                List<Hall> hallList = hallDao.getHallAll();
                session.setAttribute("filmList", filmList);
                session.setAttribute("hallList", hallList);
                if(request.getParameter("filmSelect") != null
                        && request.getParameter("hallSelect") != null
                        && request.getParameter("date-time") != null)
                {
                    int filmId = Integer.parseInt(request.getParameter("filmSelect"));
                    int hallId = Integer.parseInt(request.getParameter("hallSelect"));
                    Date dateTime = new SimpleDateFormat("yyyy-MM-ddHH:mm").parse(request.getParameter("date-time"));
                    Film film = filmDao.getFilmById(filmId);
                    Hall hall = hallDao.getHallById(hallId);
                    Filmshow filmshow = new Filmshow();
                    List<Filmshow> filmshowLst = filmshowDao.getFilmshowAll();
                    boolean filmshowValid = true;
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
                    if(film != null && hall != null && dateTime != null)
                    {
                        filmshow.setFilm(film);
                        filmshow.setHall(hall);
                        filmshow.setDateTime(dateTime);
                        for(Filmshow f : filmshowLst)
                        {
                            if(f.getFilm().equals(filmshow.getFilm())
                                    && f.getHall().equals(filmshow.getHall())
                                    && dateFormat.format(f.getDateTime()).equals(dateFormat.format(filmshow.getDateTime())))
                            {
                                filmshowValid = false;
                                break;
                            }
                        }
                        if(filmshowValid)
                        {
                            filmshowDao.create(filmshow);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addFilmshow.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteFilmshow"))
        {
            try
            {
                List<Filmshow> ls = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", ls);
                if(request.getParameter("filmshow-select") != null)
                {
                    int filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                    Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                    if(filmshow != null)
                    {
                        filmshowDao.delete(filmshow);
                    }
                    ls = filmshowDao.getFilmshowAll();
                    session.setAttribute("filmshowList", ls);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteFilmshow.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/filmshowList"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/filmshowList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addTicket"))
        {
            try
            {
                int filmshowId = 0;
                float price = 0;
                int seatId = 0;
                List<Filmshow> filmshowLs = filmshowDao.getFilmshowAll();
                session.setAttribute("filmshowList", filmshowLs);
                if(request.getParameter("filmshow-select") != null
                        && request.getParameter("ticket-add-price") != null
                        && request.getParameter("seat-select") != null)
                {
                    filmshowId = Integer.parseInt(request.getParameter("filmshow-select"));
                    price = Float.parseFloat(request.getParameter("ticket-add-price"));
                    seatId = Integer.parseInt(request.getParameter("seat-select"));
                }
                Filmshow filmshow = filmshowDao.getFilmshowById(filmshowId);
                Seat seat = seatDao.getSeatById(seatId);
                Ticket ticket = new Ticket();
                List<Ticket> ticketLst = ticketDao.getTicketAll();
                boolean ticketValid = true;
                if(filmshow != null && price != 0 && seat != null)
                {
                    ticket.setFilmshow(filmshow);
                    ticket.setPrice(price);
                    ticket.setSeat(seat);
                    for(Ticket t : ticketLst)
                    {
                        if(t.getFilmshow().equals(ticket.getFilmshow()) && t.getSeat().equals(ticket.getSeat()))
                        {
                            ticketValid = false;
                            break;
                        }
                    }
                    if(ticketValid)
                    {
                        ticketDao.create(ticket);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addTicket.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addTicketAll"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addTicketAll.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteTicket"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteTicket.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/ticketList"))
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
                List<Ticket> filteredLs = new LinkedList<>();
                if(filmshow != null && ticketLs != null)
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/ticketList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addReservation"))
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
                List<Reservation> reservationLst = reservationDao.getReservationAll();
                boolean reservationValid = true;
                if(user != null && ticket != null)
                {
                    Reservation reservation = new Reservation();
                    reservation.setUser(user);
                    reservation.setTicket(ticket);
                    for(Reservation r : reservationLst)
                    {
                        if(r.getTicket().equals(reservation.getTicket()))
                        {
                            reservationValid = false;
                            break;
                        }
                    }
                    if(reservationValid)
                    {
                        reservationDao.create(reservation);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addReservation.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteReservation"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteReservation.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/reservationList"))
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
                    filteredLs.addAll(reservationLs.stream().filter(r -> r.getUser().equals(user)).collect(Collectors.toList()));
                    session.setAttribute("filteredReservationList", filteredLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/reservationList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addSeat"))
        {
            try
            {
                int hallId = 0;
                int rowNumber = 0;
                int seatNumber = 0;
                List<Hall> hallLs = hallDao.getHallAll();
                session.setAttribute("hallList", hallLs);
                if(request.getParameter("hall-select") != null
                        && request.getParameter("seat-add-row") != null
                        && request.getParameter("seat-add-number") != null)
                {
                    hallId = Integer.parseInt(request.getParameter("hall-select"));
                    rowNumber = Integer.parseInt(request.getParameter("seat-add-row"));
                    seatNumber = Integer.parseInt(request.getParameter("seat-add-number"));
                }
                Hall hall = hallDao.getHallById(hallId);
                List<Seat> seatLst = seatDao.getSeatAll();
                boolean seatValid = true;
                if(hall != null && rowNumber != 0 && seatNumber != 0)
                {
                    Seat seat = new Seat();
                    seat.setHall(hall);
                    seat.setRowNumber(rowNumber);
                    seat.setSeatNumber(seatNumber);
                    for(Seat s : seatLst)
                    {
                        if(s.getSeatNumber().equals(seat.getSeatNumber())
                                && s.getHall().equals(seat.getHall())
                                && s.getRowNumber().equals(seat.getRowNumber()))
                        {
                            seatValid = false;
                            break;
                        }
                    }
                    if(seatValid)
                    {
                        seatDao.create(seat);
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addSeat.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteSeat"))
        {
            try
            {
                List<Seat> seatLs = seatDao.getSeatAll();
                session.setAttribute("seatList", seatLs);
                if(request.getParameter("seat-select") != null)
                {
                    int seatId = Integer.parseInt(request.getParameter("seat-select"));
                    Seat seat = seatDao.getSeatById(seatId);
                    if(seat != null)
                    {
                        seatDao.delete(seat);
                    }
                    seatLs = seatDao.getSeatAll();
                    session.setAttribute("filmshowList", seatLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteSeat.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/seatList"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/seatList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addHall"))
        {
            try
            {
                if(request.getParameter("hall-add-number") != null)
                {
                    int hallNumber = Integer.parseInt(request.getParameter("hall-add-number"));
                    String hallName = request.getParameter("hall-add-name");
                    List<Hall> hallLst = hallDao.getHallAll();
                    boolean hallValid = true;
                    if(hallNumber != 0 && hallName != null)
                    {
                        Hall hall = new Hall();
                        hall.setHallNumber(hallNumber);
                        hall.setHallName(hallName);
                        for(Hall h : hallLst)
                        {
                            if(h.getHallName().equals(hall.getHallName()) || h.getHallNumber().equals(hall.getHallNumber()))
                            {
                                hallValid = false;
                                break;
                            }
                        }
                        if(hallValid)
                        {
                            hallDao.create(hall);

                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addHall.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteHall"))
        {
            try
            {
                List<Hall> hallLs = hallDao.getHallAll();
                session.setAttribute("hallList", hallLs);
                if(request.getParameter("hall-select") != null)
                {
                    int hallId = Integer.parseInt(request.getParameter("hall-select"));
                    Hall hall = hallDao.getHallById(hallId);
                    if(hall != null)
                    {
                        hallDao.delete(hall);
                    }
                    hallLs = hallDao.getHallAll();
                    session.setAttribute("hallList", hallLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteHall.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/hallList"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/hallList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/addUser"))
        {
            try
            {
                User user = new User();
                String login = request.getParameter("user-add-login");
                String password = request.getParameter("user-add-password");
                String email = request.getParameter("user-add-email");
                if(password != null)
                {
                    MessageDigest digest = MessageDigest.getInstance("SHA-1");
                    digest.reset();
                    byte[] hash = digest.digest(password.getBytes("UTF-8"));
                    String passwordHash = DatatypeConverter.printHexBinary(hash);
                    List<User> userLst = userDao.getUserAll();
                    boolean userValid = true;

                    if(login != null
                            && !login.isEmpty()
                            && passwordHash != null
                            && !password.isEmpty()
                            && email != null
                            && !email.isEmpty())
                    {
                        user.setLogin(login);
                        user.setPassword(passwordHash);
                        user.setEmail(email);
                        for(User u : userLst)
                        {
                            if(u.getLogin().equals(user.getLogin()) || u.getEmail().equals(user.getEmail()))
                            {
                                userValid = false;
                            }
                        }
                        if(userValid)
                        {
                            userDao.create(user);
                        }
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/addUser.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/deleteUser"))
        {
            try
            {
                List<User> userLs = userDao.getUserAll();
                session.setAttribute("userList", userLs);
                if(request.getParameter("user-select") != null)
                {
                    int userId = Integer.parseInt(request.getParameter("user-select"));
                    User user = userDao.getUserById(userId);
                    if(user != null)
                    {
                        userDao.delete(user);
                    }
                    userLs = userDao.getUserAll();
                    session.setAttribute("userList", userLs);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/deleteUser.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin/userList"))
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
            User admin = (User)session.getAttribute("adminUser");
            if(admin != null)
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/userList.jsp");
                dispatcher.forward(request, response);
            }
            else
            {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                dispatcher.forward(request, response);
            }
        }

        if(url.equals("/admin"))
        {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/admin/login"))
        {
            try
            {
                List<User> ls = userDao.getUserAll();
                String login = request.getParameter("login-auth");
                String password = request.getParameter("password-auth");

                if(login != null && password != null)
                {
                    MessageDigest digest = MessageDigest.getInstance("SHA-1");
                    digest.reset();
                    byte[] hash = digest.digest(password.getBytes("UTF-8"));
                    String passwordHash = DatatypeConverter.printHexBinary(hash);

                    if(login.equals("admin"))
                    {
                        for(User u : ls)
                        {
                            if(u.getLogin().equals(login) && u.getPassword().toUpperCase().equals(passwordHash))
                            {
                                session.setAttribute("adminUser", u);
                                break;
                            }
                        }
                    }
                }
                User admin = (User)session.getAttribute("adminUser");
                if(admin != null)
                {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/adminMain.jsp");
                    dispatcher.forward(request, response);
                }
                else
                {
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/forbidden.jsp");
                    dispatcher.forward(request, response);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        if(url.equals("/admin/logout"))
        {
            session.invalidate();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/reserveTicket"))
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/reserveTicket.jsp");
            dispatcher.forward(request, response);
        }

        if(url.equals("/reservationList"))
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
            RequestDispatcher dispatcher = request.getRequestDispatcher("/userReservationList.jsp");
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

