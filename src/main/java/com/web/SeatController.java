package com.web;

import com.domain.Hall;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.HallService;
import com.service.SeatService;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class SeatController
{
    @Autowired
    private SeatService seatService;

    @Autowired
    private HallService hallService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private HallEditor hallEditor;

    @RequestMapping("/admin/addSeat")
    public ModelAndView addSeat(@ModelAttribute Seat seat) throws Exception
    {
        if(seat != null && seat.getHall() != null && seat.getRowNumber() != null
                && seat.getSeatNumber() != null)
        {
            seatService.create(seat);
        }
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addSeat");
        mav.addObject("hallList", hallList);
        mav.addObject("seat", new Seat());
        return mav;
    }

    @RequestMapping("/admin/deleteSeat")
    public ModelAndView deleteSeat(@ModelAttribute Seat seat, HttpServletResponse response) throws Exception
    {
        try
        {
            if(seat.getSeatId() != null && seat.getSeatId() != 0)
            {
                seat = seatService.getSeatById(seat.getSeatId());
                if(seat != null)
                {
                    seatService.delete(seat);
                }
            }
            List<Seat> seatList = seatService.getSeatAll();
            response.setStatus(HttpServletResponse.SC_OK);
            return new ModelAndView("deleteSeat", "seatList", seatList);
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/admin/seatList")
    public ModelAndView listSeats() throws Exception
    {
        List<Seat> seatList = seatService.getSeatAll();
        return new ModelAndView("seatList", "seatList", seatList);
    }

    @RequestMapping(value = "/admin/seatCheck/{seatId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String seatCheck(@PathVariable Integer seatId) throws Exception
    {
        if(seatId != null)
        {
            Seat seat = seatService.getSeatById(seatId);
            if(seat != null)
            {
                List<Ticket> ticketList = ticketService.getTicketAll();
                for(Ticket t : ticketList)
                {
                    if(t.getSeat().equals(seat))
                    {
                        return "На место выпущены билеты. Сначала удалите билеты.";
                    }
                }
            }
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
