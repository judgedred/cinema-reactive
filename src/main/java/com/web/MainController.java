package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MainController
{
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping("/admin")
    public ModelAndView adminView()
    {
        return new ModelAndView("admin");
    }

    @RequestMapping(value = "/admin/login", produces = "text/plain;charset=UTF-8")
    public ModelAndView adminLogin(@ModelAttribute User user) throws Exception
    {
        ModelAndView mav = new ModelAndView("adminMain");
        List<User> userList = userService.getUserAll();
        if(user != null && user.getLogin() != null && user.getPassword() != null)
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.reset();
            byte[] hash = digest.digest(user.getPassword().getBytes("UTF-8"));
            String passwordHash = DatatypeConverter.printHexBinary(hash);
            boolean userValid = false;
            if(user.getLogin().equals("admin"))
            {
                for(User u : userList)
                {
                    if(u.getLogin().equals(user.getLogin()) && u.getPassword().toUpperCase().equals(passwordHash))
                    {
                        mav.addObject("adminUserJson", u);
                        break;
                    }
                }
            }
        }
        return mav;
    }

    @RequestMapping("/admin/logout")
    public ModelAndView adminLogout(HttpSession session)
    {
        session.invalidate();
        return new ModelAndView("admin");
    }

    @RequestMapping("/reserveTicket")
    public @ResponseBody ModelAndView reserveTicket(@RequestBody Ticket ticket, HttpSession session,
                                                    @ModelAttribute Filmshow filmshow) throws Exception
    {
        ModelAndView mav = new ModelAndView("reserveTicket");
        User user = (User)session.getAttribute("validUser");
        if(ticket != null && ticket.getFilmshow() != null && user != null
                && ticket.getSeat() != null && ticket.getPrice() != null)
        {
            Reservation reservation = new Reservation();
            reservation.setUser(user);
            reservation.setTicket(ticket);
            reservationService.create(reservation);
        }
        if(filmshow != null)
        {
            List<Ticket> ticketList = ticketService.getTicketAll();
            List<Reservation> reservationList = reservationService.getReservationAll();
            boolean ticketFree;
            List<Ticket> filteredTicketList = new LinkedList<>();
            {
                for(Ticket t : ticketList)
                    if(t.getFilmshow().equals(filmshow))
                    {
                        ticketFree = true;
                        for(Reservation r : reservationList)
                        {
                            if(t.equals(r.getTicket()))
                            {
                                ticketFree = false;
                                break;
                            }
                        }
                        if(ticketFree)
                        {
                            filteredTicketList.add(t);
                        }
                    }
                }
            mav.addObject("filteredTicketList", filteredTicketList);
        }
        return mav;
    }

    @RequestMapping("/reservationList")
    public @ResponseBody ModelAndView listReservations(HttpSession session) throws Exception
    {
        ModelAndView mav = new ModelAndView("userReservationList");
        User user = (User)session.getAttribute("validUser");
        List<Reservation> reservationList = reservationService.getReservationAll();
        List<Reservation> filteredList = new LinkedList<>();
        if(user != null && reservationList != null)
        {
            for(Reservation r : reservationList)
            {
                if(r.getUser().equals(user))
                {
                    filteredList.add(r);
                }
            }
            mav.addObject("filteredReservationList", filteredList);
        }
        return mav;
    }
}
