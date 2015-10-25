package com.web;

import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;
import com.service.FilmshowService;
import com.service.HallService;
import com.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class HallController
{
    @Autowired
    private HallService hallService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private SeatService seatService;

    @RequestMapping("/admin/addHall")
    public ModelAndView addHall(@ModelAttribute Hall hall) throws Exception
    {
        if(hall != null && hall.getHallName() != null && !hall.getHallName().isEmpty() && hall.getHallNumber() != null)
        {
            hallService.create(hall);
        }
        return new ModelAndView("addHall", "hall", new Hall());
    }

    @RequestMapping("/admin/deleteHall")
    public ModelAndView deleteHall(@ModelAttribute Hall hall, HttpServletResponse response) throws Exception
    {
        try
        {
            if(hall.getHallId() != null && hall.getHallId() != 0)
            {
                hall = hallService.getHallById(hall.getHallId());
                if(hall != null)
                {
                    hallService.delete(hall);
                }
            }
            List<Hall> hallList = hallService.getHallAll();
            response.setStatus(HttpServletResponse.SC_OK);
            return new ModelAndView("deleteHall", "hallList", hallList);
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/admin/hallList")
    public ModelAndView listHalls() throws Exception
    {
        List<Hall> hallList = hallService.getHallAll();
        return new ModelAndView("hallList", "hallList", hallList);
    }

    @RequestMapping(value = "/admin/hallCheck/{hallId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String hallCheck(@PathVariable Integer hallId) throws Exception
    {
        if(hallId != null)
        {
            Hall hall = hallService.getHallById(hallId);
            if(hall != null)
            {
                List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
                List<Seat> seatList = seatService.getSeatAll();
                boolean hallFreeFilmshow = true;
                boolean hallFreeSeat = true;
                for(Filmshow f : filmshowList)
                {
                    if(f.getHall().equals(hall))
                    {
                        hallFreeFilmshow = false;
                        break;
                    }
                    for(Seat s : seatList)
                    {
                        if(s.getHall().equals(hall))
                        {
                            hallFreeSeat = false;
                            break;
                        }
                    }
                }
                if(!hallFreeFilmshow)
                {
                    return "В зале имеются сеансы. Сначала удалите сеансы.";
                }
                if(!hallFreeSeat)
                {
                    return "В зале имеются места. Сначала удалите места.";
                }
            }
        }
        return null;
    }
}
