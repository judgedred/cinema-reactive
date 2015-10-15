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
import java.util.*;

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

    @Autowired
    private SeatEditor seatEditor;

    private List<Seat> filteredSeatList = new ArrayList<>();

    @RequestMapping("/admin/addTicket")
    public ModelAndView addTicket(@ModelAttribute Ticket ticket) throws Exception
    {
        if(ticket != null && ticket.getFilmshow() != null
                && ticket.getSeat() != null && ticket.getPrice() != null)
        {
            ticketService.create(ticket);
            return new ModelAndView(new RedirectView("/cinema/admin/addTicket"));
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowList", filmshowList);
        return mav;
    }

    @RequestMapping("/admin/addTicketAll")
    public ModelAndView addTicketAll(@ModelAttribute Ticket ticket) throws Exception
    {
        if(ticket != null && ticket.getFilmshow() != null
             && ticket.getPrice() != null
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
        ModelAndView mav = new ModelAndView("addTicketAll");
        mav.addObject("filmshowList", filmshowList);
        return mav;
    }

//    @RequestMapping("/admin/updateTicket")

    @RequestMapping("/admin/deleteTicket")
    public ModelAndView deleteTicket(@ModelAttribute Ticket ticket, HttpServletResponse response) throws Exception
    {
        try
        {
            if(ticket.getTicketId() != null && ticket.getTicketId() != 0)
            {
                ticket = ticketService.getTicketById(ticket.getTicketId());
                if(ticket != null)
                {
                    ticketService.delete(ticket);
                    return new ModelAndView(new RedirectView("deleteTicket"));
                }
            }
            List<Ticket> ticketList = ticketService.getTicketAll();
            response.setStatus(HttpServletResponse.SC_OK);
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
    public ModelAndView listTickets(@ModelAttribute Ticket ticket) throws Exception
    {
        List<Ticket> ticketList = ticketService.getTicketAll();
        List<Ticket> filteredTicketList = new ArrayList<>();
        Filmshow filmshow = ticket.getFilmshow();
        if(ticketList != null && filmshow != null && filmshow.getFilm() != null
                && filmshow.getHall() != null && filmshow.getDateTime() != null)
        {
            for(Ticket t : ticketList)
            {
                if(t.getFilmshow().equals(filmshow))
                {
                    filteredTicketList.add(t);
                }
            }
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("ticketList");
        mav.addObject("ticketList", ticketList);
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("filteredTicketList", filteredTicketList);
        return mav;
    }

    @RequestMapping("/admin/seatsFilter/{filmshowId}")
    public @ResponseBody Map<Integer, String> filterSeats(@PathVariable int filmshowId) throws Exception
    {
        List<Seat> seatList = seatService.getSeatAll();
        List<Ticket> ticketList = ticketService.getTicketAll();
        boolean seatFree;
        Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
        filteredSeatList.clear();
        Map<Integer, String> filteredSeatMap = new HashMap<>();
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
                        filteredSeatMap.put(s.getSeatId(), s.toString());
                        filteredSeatList.add(s);
                    }
                }
            }
        }
        return filteredSeatMap;
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
        binder.registerCustomEditor(Seat.class, seatEditor);
    }
}
