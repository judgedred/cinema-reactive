package com.web;

import com.domain.Hall;
import com.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Controller
public class HallController {

    @Autowired
    private HallService hallService;

    @RequestMapping("/admin/addHallForm")
    public ModelAndView addHallForm() throws Exception {
        return new ModelAndView("addHall", "hall", new Hall());
    }

    @RequestMapping("/admin/addHall")
    public ModelAndView addHall(@Valid Hall hall, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return new ModelAndView("addHall", "hall", hall);
        }
        hallService.create(hall);
        return new ModelAndView("addHall", "hall", new Hall());
    }

    @RequestMapping("/admin/deleteHall")
    public ModelAndView deleteHall(@ModelAttribute Hall hall) throws Exception {
        if (hall.getHallId() != null && !hall.getHallId().equals(BigInteger.ZERO)) {
            hall = hallService.getHallById(hall.getHallId());
            if (hall != null) {
                hallService.delete(hall);
            }
        }
        List<Hall> hallList = hallService.getHallAll();
        return new ModelAndView("deleteHall", "hallList", hallList);
    }

    @RequestMapping("/admin/hallList")
    public ModelAndView listHalls() throws Exception {
        List<Hall> hallList = hallService.getHallAll();
        return new ModelAndView("hallList", "hallList", hallList);
    }

    @RequestMapping(value = "/admin/checkHall/{hallId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody
    String checkHall(@PathVariable Integer hallId) throws Exception {
        if (hallId != null) {
            Hall hall = hallService.getHallById(BigInteger.valueOf(hallId));
            if (hall != null) {
                if (hallService.checkHallInFilmshow(hall)) {
                    return "В зале имеются сеансы. Сначала удалите сеансы.";
                }
                if (hallService.checkHallInSeat(hall)) {
                    return "В зале имеются места. Сначала удалите места.";
                }
            }
        }
        return null;
    }
}
