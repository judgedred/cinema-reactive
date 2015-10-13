package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.SeatService;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Controller
public class TicketController
{
    @Autowired
    private TicketService ticketService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private TicketEditor ticketEditor;

    @Autowired
    private FilmshowEditor filmshowEditor;

    @RequestMapping("/admin/addTicket")
    public ModelAndView addTicket(@ModelAttribute Ticket ticket) throws Exception
    {
        List<Ticket> ticketList = ticketService.getTicketAll();
        boolean ticketValid = true;
        if(ticket != null && ticket.getFilmshow() != null
                && ticket.getSeat() != null && ticket.getPrice() != null)
        {
            for(Ticket t : ticketList)
            {
                if(t.getFilmshow().equals(ticket.getFilmshow()) && t.getSeat().equals(ticket.getSeat()))
                {
                    ticketValid = false;
                    break;
                }
            }
            if(ticketValid)
            {
                ticketService.create(ticket);
                return new ModelAndView(new RedirectView("/cinema/admin/addTicket"));
            }
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowList", filmshowList);
        return mav;
    }

    @RequestMapping("/admin/addTicketAll")
    public ModelAndView addTicketAll(@ModelAttribute Ticket ticket, List<Seat> filteredSeatList) throws Exception
    {
        if(ticket != null && ticket.getFilmshow() != null
            && ticket.getSeat() != null && ticket.getPrice() != null
                && filteredSeatList != null)
        {
            for(Seat s : filteredSeatList)
            {
                ticket.setSeat(s);
                ticketService.create(ticket);
            }
            return new ModelAndView(new RedirectView("/cinema/admin/addTicketAll"));
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowList", filmshowList);
        return mav;
    }

//    @RequestMapping("/admin/updateTicket")

    @RequestMapping("/admin/deleteTicket")
    public ModelAndView deleteTicket(@ModelAttribute Ticket ticket, HttpServletResponse response) throws Exception
    {
        try
        {
            if(ticket != null && ticket.getFilmshow() != null
                    && ticket.getSeat() != null && ticket.getPrice() != null)
            {
                ticketService.delete(ticket);
            }
            List<Ticket> ticketList = ticketService.getTicketAll();
            return new ModelAndView("deleteTicket", "ticketList", ticketList);
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/admin/ticketList")
    public ModelAndView listTickets(@ModelAttribute Filmshow filmshow) throws Exception
    {
        List<Ticket> ticketList = ticketService.getTicketAll();
        List<Ticket> filteredList = new ArrayList<>();
        if(ticketList != null && filmshow != null && filmshow.getFilm() != null
                && filmshow.getHall() != null && filmshow.getDateTime() != null)
        {
            for(Ticket t : ticketList)
            {
                if(t.getFilmshow().equals(filmshow))
                {
                    filteredList.add(t);
                }
            }
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("ticketList");
        mav.addObject("ticketList", ticketList);
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("filteredList", filteredList);
        mav.addObject("ticket", new Ticket());
        return mav;
    }

    @RequestMapping("/admin/seatsFilter/{filmshowId}")
    public Model filterSeats(@PathVariable int filmshowId, Model model) throws Exception
    {
        List<Seat> seatList = seatService.getSeatAll();
        List<Ticket> ticketList = ticketService.getTicketAll();
        boolean seatFree;
        Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
        List<Seat> filteredSeatList = new ArrayList<>();
        if(filmshow != null && filmshow.getFilm() != null
                && filmshow.getHall() != null && filmshow.getDateTime() != null)
        {
            for(Seat s : seatList)
            {
                seatFree = true;
                if(s.getHall().equals(filmshow.getHall()))
                {
                    for(Ticket t : ticketList)
                    {
                        if(t.getFilmshow().equals(filmshow) && t.getSeat().equals(s))
                        {
                            seatFree = false;
                            break;
                        }
                    }
                    if(seatFree)
                    {
                        filteredSeatList.add(s);
                    }
                }
            }
        }
        model.addAttribute("filteredSeatList", filteredSeatList);
        return model;
    }

    @RequestMapping(value = "/admin/ticketCheck/{ticketId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String ticketCheck(@PathVariable int ticketId) throws Exception
    {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if(ticket != null)
        {
            List<Reservation> reservationList = reservationService.getReservationAll();
            for(Reservation r : reservationList)
            {
                if(r.getTicket().equals(ticket))
                {
                    return "Билет зарезервирован. Сначала удалите бронь.";
                }
            }
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
    }
}
