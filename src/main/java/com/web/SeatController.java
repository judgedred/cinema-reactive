package com.web;

import com.domain.Hall;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.HallService;
import com.service.SeatService;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @RequestMapping("/admin/addSeatForm")
    public ModelAndView addSeatForm() throws Exception
    {
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addSeat");
        mav.addObject("hallList", hallList);
        mav.addObject("seat", new Seat());
        return mav;
    }

    @RequestMapping("/admin/addSeat")
    public ModelAndView addSeat(@Valid Seat seat, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            List<Hall> hallList = hallService.getHallAll();
            ModelAndView mav = new ModelAndView("addSeat");
            mav.addObject("hallList", hallList);
            mav.addObject("seat", seat);
            return mav;
        }
        seatService.create(seat);
        return new ModelAndView(new RedirectView("addSeatForm"));
    }

    @RequestMapping("/admin/deleteSeat")
    public ModelAndView deleteSeat(@ModelAttribute Seat seat) throws Exception
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
        return new ModelAndView("deleteSeat", "seatList", seatList);
    }

    @RequestMapping("/admin/seatList")
    public ModelAndView listSeats() throws Exception
    {
        List<Seat> seatList = seatService.getSeatAll();
        return new ModelAndView("seatList", "seatList", seatList);
    }

    @RequestMapping(value = "/admin/checkSeat/{seatId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String checkSeat(@PathVariable Integer seatId) throws Exception
    {
        if(seatId != null)
        {
            Seat seat = seatService.getSeatById(seatId);
            if(seat != null && seatService.checkSeatInTicket(seat))
            {
                return "На место выпущены билеты. Сначала удалите билеты.";
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
