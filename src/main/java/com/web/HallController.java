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
import org.springframework.web.reactive.result.view.Rendering;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Optional;

@Controller
public class HallController {

    private final HallService hallService;

    public HallController(HallService hallService) {
        this.hallService = hallService;
    }

    @RequestMapping("/admin/addHallForm")
    public Rendering addHallForm() {
        return Rendering.view("addHall").modelAttribute("hall", new Hall()).build();
    }

    @RequestMapping("/admin/addHall")
    public Rendering addHall(@Valid Hall hall, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("hall", hall);
        } else {
            hallService.save(hall);
            model.addAttribute("hall", new Hall());
        }
        return Rendering.view("addHall").build();
    }

    @RequestMapping("/admin/deleteHall")
    public Rendering deleteHall(@ModelAttribute Hall hall, Model model) {
        if (hall.getHallId() != null && !hall.getHallId().equals(BigInteger.ZERO)) {
            hallService.getHallById(hall.getHallId()).ifPresent(hallService::delete);
        }
        return Rendering.view("deleteHall").modelAttribute("hallList", hallService.getHallAll()).build();
    }

    @RequestMapping("/admin/hallList")
    public Rendering listHalls(Model model) {
        return Rendering.view("hallList").modelAttribute("hallList", hallService.getHallAll()).build();
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
