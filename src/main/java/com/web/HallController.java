package com.web;

import com.domain.Hall;
import com.service.FilmshowService;
import com.service.HallService;
import com.service.SeatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Controller
public class HallController {

    private final HallService hallService;
    private final FilmshowService filmshowService;
    private final SeatService seatService;

    public HallController(HallService hallService, FilmshowService filmshowService, SeatService seatService) {
        this.hallService = hallService;
        this.filmshowService = filmshowService;
        this.seatService = seatService;
    }

    @RequestMapping("/admin/addHallForm")
    public Rendering addHallForm() {
        return Rendering.view("addHall").modelAttribute("hall", new Hall()).build();
    }

    @RequestMapping("/admin/addHall")
    public Mono<Rendering> addHall(@Valid Hall hall, BindingResult result) {
        return Mono.just(hall)
                .filter(h -> !result.hasErrors())
                .flatMap(hallService::save)
                .map(h -> new Hall())
                .defaultIfEmpty(hall)
                .map(h -> Rendering.view("addHall").modelAttribute("hall", h).build());
    }

    @RequestMapping("/admin/deleteHall")
    public Mono<Rendering> deleteHall(@ModelAttribute Hall hall) {
        return Mono.justOrEmpty(hall.getHallId())
                .filter(hallId -> !hallId.equals(BigInteger.ZERO))
                .flatMap(hallService::getHallById)
                .flatMap(hallService::delete)
                .thenMany(hallService.getHallAll())
                .collectList()
                .map(hallList -> Rendering.view("deleteHall").modelAttribute("hallList", hallList).build());
    }

    @RequestMapping("/admin/hallList")
    public Rendering listHalls(Model model) {
        return Rendering.view("hallList").modelAttribute("hallList", hallService.getHallAll()).build();
    }

    @RequestMapping(value = "/admin/checkHall/{hallId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> checkHall(@PathVariable BigInteger hallId) {
        return Mono.justOrEmpty(hallId)
                .flatMap(hallService::getHallById)
                .flatMap(this::checkHallConstraints);
    }

    private Mono<String> checkHallConstraints(Hall hall) {
        return checkFilmshows(hall)
                .switchIfEmpty(checkSeats(hall));
    }

    private Mono<String> checkFilmshows(Hall hall) {
        return filmshowService.getFilmshowByHall(hall)
                .collectList()
                .filter(filmshows -> !filmshows.isEmpty())
                .map(filmshows -> "В зале имеются сеансы. Сначала удалите сеансы.");
    }

    private Mono<String> checkSeats(Hall hall) {
        return Flux.fromIterable(seatService.getSeatAllByHall(hall))
                .collectList()
                .filter(seats -> !seats.isEmpty())
                .map(seats -> "В зале имеются места. Сначала удалите места.");
    }
}
