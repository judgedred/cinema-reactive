package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ReservationController
{
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FilmshowEditor filmshowEditor;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private TicketEditor ticketEditor;

    @RequestMapping("/admin/addReservationForm")
    public ModelAndView addReservationForm() throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("addReservation");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("userList", userList);
        mav.addObject("reservation", new Reservation());
        return mav;
    }

    @RequestMapping("/admin/addReservation")
    public ModelAndView addReservation(@Valid Reservation reservation, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            List<User> userList = userService.getUserAll();
            ModelAndView mav = new ModelAndView("addReservation");
            mav.addObject("filmshowList", filmshowList);
            mav.addObject("userList", userList);
            mav.addObject("reservation", reservation);
            return mav;
        }
        reservationService.create(reservation);
        return new ModelAndView(new RedirectView("addReservationForm"));
    }

    @RequestMapping("/admin/deleteReservation")
    public ModelAndView deleteReservation(@ModelAttribute Reservation reservation) throws Exception
    {
        if(reservation.getReservationId() != null && reservation.getReservationId() != 0)
        {
            reservation = reservationService.getReservationById(reservation.getReservationId());
            if(reservation != null)
            {
                reservationService.delete(reservation);
            }
        }
        List<Reservation> reservationList = reservationService.getReservationAll();
        return new ModelAndView("deleteReservation", "reservationList", reservationList);
    }

    @RequestMapping("/admin/reservationList")
    public ModelAndView listReservations(@ModelAttribute Reservation reservation) throws Exception
    {
        /*List<Reservation> reservationList = reservationService.getReservationAll();
        List<Reservation> filteredReservationList = new ArrayList<>();
        User user = reservation.getUser();
        if(reservationList != null && user != null && user.getLogin() != null
                && user.getPassword() != null && user.getEmail() != null)
        {
            filteredReservationList.addAll(reservationList.stream().filter(r -> r.getUser().equals(user)).collect(Collectors.toList()));
        }*/
        List<Reservation> reservationList = reservationService.getReservationAllByUser(reservation.getUser());
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("reservationList");
        mav.addObject("userList", userList);
        mav.addObject("filteredReservationList", reservationList);
        return mav;
    }

    @RequestMapping("/admin/ticketsFilter/{filmshowId}")
    public @ResponseBody Map<Integer, String> filterTickets(@PathVariable Integer filmshowId) throws Exception
    {
        /*if(filmshowId != null)
        {
            List<Ticket> ticketList = ticketService.getTicketAll();
            List<Reservation> reservationList = reservationService.getReservationAll();
            boolean ticketFree;
            Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
            Map<Integer, String> filteredTicketMap = new HashMap<>();
            if(filmshow != null && filmshow.getFilm() != null
                    && filmshow.getHall() != null && filmshow.getDateTime() != null)
            {
                for(Ticket t : ticketList)
                {
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
                            filteredTicketMap.put(t.getTicketId(), t.toString());
                        }
                    }
                }
            }
            return filteredTicketMap;
        }
        return null;*/
        if(filmshowId != null)
        {
            Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
            return ticketService.getTicketAllByFilmshow(filmshow);
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
