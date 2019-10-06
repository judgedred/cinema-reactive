package com.web;

import com.domain.Hall;
import com.service.FilmshowService;
import com.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Controller
public class HallController {

    private final HallService hallService;
    private final FilmshowService filmshowService;

    public HallController(HallService hallService, FilmshowService filmshowService) {
        this.hallService = hallService;
        this.filmshowService = filmshowService;
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
    public Mono<String> checkHall(@PathVariable BigInteger hallId) {
        return Mono.justOrEmpty(hallId)
                .flatMap(id -> Mono.justOrEmpty(hallService.getHallById(id)))
                .flatMap(this::checkHallConstraints);
    }

    private Mono<String> checkHallConstraints(Hall hall) {
        return filmshowService.getFilmshowByHall(hall)
                .collectList()
                .map(filmshows -> "В зале имеются сеансы. Сначала удалите сеансы.")
                .switchIfEmpty(Mono.fromCallable(() -> hallService.checkHallInSeat(hall))
                        .map(found -> "В зале имеются места. Сначала удалите места."));

    }
}
