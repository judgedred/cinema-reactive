package com.web;

import com.domain.Hall;
import com.domain.Seat;
import com.service.HallService;
import com.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SeatController
{
    @Autowired
    private SeatService seatService;

    @Autowired
    private HallService hallService;

    @RequestMapping(value = "/admin/addSeat", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addSeat(@RequestBody Seat seat) throws Exception
    {
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addSeat");
        mav.addObject("hallListJson", hallList);
        if(seat != null && seat.getHall() != null && seat.getRowNumber() != null
                && seat.getSeatNumber() != null)
        {
            List<Seat> seatList = seatService.getSeatAll();
            boolean seatValid = true;
            for(Seat s : seatList)
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
                seatService.create(seat);
                mav.addObject("seatJson", seat);
            }
        }
        return mav;
    }

//    @RequestMapping("updateSeat")

    @RequestMapping("/admin/deleteSeat/{seatId}")
    public @ResponseBody ModelAndView deleteSeat(@PathVariable int seatId) throws Exception
    {
        List<Seat> seatList = seatService.getSeatAll();
        Seat seat = seatService.getSeatById(seatId);
        if(seat != null)
        {
            seatService.delete(seat);
        }
        return new ModelAndView("deleteSeat", "seatListJson", seatList);
    }

    @RequestMapping("/admin/seatList")
    public @ResponseBody ModelAndView seatList() throws Exception
    {
        List<Seat> seatList = seatService.getSeatAll();
        return new ModelAndView("seatList", "seatListJson", seatList);
    }
}
