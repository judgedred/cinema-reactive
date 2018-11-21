package com.web;

import com.domain.Hall;
import com.service.HallService;
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
import java.util.Optional;

@Controller
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @RequestMapping("/admin/addHallForm")
    public ModelAndView addHallForm() {
        return new ModelAndView("addHall", "hall", new Hall());
    }

    @RequestMapping("/admin/addHall")
    public ModelAndView addHall(@Valid Hall hall, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("addHall", "hall", hall);
        }
        hallService.save(hall);
        return new ModelAndView("addHall", "hall", new Hall());
    }

    @RequestMapping("/admin/deleteHall")
    public ModelAndView deleteHall(@ModelAttribute Hall hall) {
        if (hall.getHallId() != null && !hall.getHallId().equals(BigInteger.ZERO)) {
            hallService.getHallById(hall.getHallId()).ifPresent(hallService::delete);
        }
        List<Hall> hallList = hallService.getHallAll();
        return new ModelAndView("deleteHall", "hallList", hallList);
    }

    @RequestMapping("/admin/hallList")
    public ModelAndView listHalls() {
        List<Hall> hallList = hallService.getHallAll();
        return new ModelAndView("hallList", "hallList", hallList);
    }

    @RequestMapping(value = "/admin/checkHall/{hallId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkHall(@PathVariable BigInteger hallId) {
        return Optional.ofNullable(hallId)
                .flatMap(hallService::getHallById)
                .flatMap(this::checkHallConstraints)
                .orElse(null);
    }

    private Optional<String> checkHallConstraints(Hall hall) {
        if (hallService.checkHallInFilmshow(hall)) {
            return Optional.of("В зале имеются сеансы. Сначала удалите сеансы.");
        }
        if (hallService.checkHallInSeat(hall)) {
            return Optional.of("В зале имеются места. Сначала удалите места.");
        }
        return Optional.empty();
    }
}
