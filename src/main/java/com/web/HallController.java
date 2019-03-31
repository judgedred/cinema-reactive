package com.web;

import com.domain.Hall;
import com.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String addHallForm(Model model) {
        model.addAttribute("hall", new Hall());
        return "addHall";
    }

    @RequestMapping("/admin/addHall")
    public String addHall(@Valid Hall hall, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("hall", hall);
        } else {
            hallService.save(hall);
            model.addAttribute("hall", new Hall());
        }
        return "addHall";
    }

    @RequestMapping("/admin/deleteHall")
    public String deleteHall(@ModelAttribute Hall hall, Model model) {
        if (hall.getHallId() != null && !hall.getHallId().equals(BigInteger.ZERO)) {
            hallService.getHallById(hall.getHallId()).ifPresent(hallService::delete);
        }
        List<Hall> hallList = hallService.getHallAll();
        model.addAttribute("hallList", hallList);
        return "deleteHall";
    }

    @RequestMapping("/admin/hallList")
    public String listHalls(Model model) {
        List<Hall> hallList = hallService.getHallAll();
        model.addAttribute("hallList", hallList);
        return "hallList";
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
