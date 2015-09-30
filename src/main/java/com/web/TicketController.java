package com.web;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.FilmshowService;
import com.service.SeatService;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping(value = "/admin/addTicket", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addTicket(@RequestBody Ticket ticket) throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowListJson", filmshowList);
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
                mav.addObject("ticketJson", ticket);
            }
        }
        return mav;
    }

    @RequestMapping(value = "/admin/addTicketAll", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addTicketAll(@RequestBody Ticket ticket, List<Seat> filteredSeatList) throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowListJson", filmshowList);
        if(ticket != null && ticket.getFilmshow() != null
            && ticket.getSeat() != null && ticket.getPrice() != null
                && filteredSeatList != null)
        {
            for(Seat s : filteredSeatList)
            {
                ticket.setSeat(s);
                ticketService.create(ticket);
            }
        }
        return mav;
    }

//    @RequestMapping("/admin/updateTicket")

    @RequestMapping("/admin/deleteTicket/{ticketId}")
    public @ResponseBody ModelAndView deleteTicket(@PathVariable int ticketId) throws Exception
    {
        List<Ticket> ticketList = ticketService.getTicketAll();
        Ticket ticket = ticketService.getTicketById(ticketId);
        if(ticket != null)
        {
            ticketService.delete(ticket);
        }
        return new ModelAndView("deleteTicket", "ticketListJson", ticketList);
    }

    @RequestMapping("/admin/ticketList")
    public @ResponseBody ModelAndView ticketList(@RequestBody Filmshow filmshow) throws Exception
    {
        List<Ticket> ticketList = ticketService.getTicketAll();
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("ticketList");
        mav.addObject("ticketListJson", ticketList);
        mav.addObject("filmshowListJson", filmshowList);
        List<Ticket> filteredList = new LinkedList<>();
        if(ticketList != null && filmshow != null)
        {
            for(Ticket t : ticketList)
            {
                if(t.getFilmshow().equals(filmshow))
                {
                    filteredList.add(t);
                }
            }
            mav.addObject("filteredListJson", filteredList);
        }
        return mav;
    }


}
