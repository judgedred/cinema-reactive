package com.web;

import com.domain.Hall;
import com.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HallController
{
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "admin/addHall", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addHall(@RequestBody Hall hall) throws Exception
    {
        if(hall != null && !hall.getHallName().equals("") && hall.getHallNumber() != null)
        {
            List<Hall> hallList = hallService.getHallAll();
            boolean hallValid = true;
            for(Hall h : hallList)
            {
                if(h.getHallName().equals(hall.getHallName()) && h.getHallNumber().equals(hall.getHallNumber()))
                {
                    hallValid = false;
                    break;
                }
            }
            if(hallValid)
            {
                return new ModelAndView("addHall", "hallJson", hallService.create(hall));
            }
            return null;
        }
        else
        {
            return null;
        }
    }

//    @RequestMapping("admin/updateHall")

    @RequestMapping("/admin/deleteHall/{hallId}")
    public @ResponseBody ModelAndView deleteHall(@PathVariable int hallId) throws Exception
    {
        List<Hall> hallList = hallService.getHallAll();
        Hall hall = hallService.getHallById(hallId);
        if(hall != null)
        {
            hallService.delete(hall);
        }
        return new ModelAndView("deleteHall", "hallListJson", hallList);
    }

    @RequestMapping("/admin/hallList")
    public @ResponseBody ModelAndView hallList() throws Exception
    {
        List<Hall> hallList = hallService.getHallAll();
        return new ModelAndView("hallList", "hallListJson", hallList);
    }

}
