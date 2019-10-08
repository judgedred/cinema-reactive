package com.web;

import com.domain.Hall;
import com.domain.Seat;
import com.service.FilmshowService;
import com.service.HallService;
import com.service.SeatService;
import com.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class SeatController {

    private final SeatService seatService;
    private final HallService hallService;
    private final TicketService ticketService;
    private final FilmshowService filmshowService;
    private final HallEditor hallEditor;

    public SeatController(SeatService seatService, HallService hallService, TicketService ticketService,
            FilmshowService filmshowService, HallEditor hallEditor) {
        this.seatService = seatService;
        this.hallService = hallService;
        this.ticketService = ticketService;
        this.filmshowService = filmshowService;
        this.hallEditor = hallEditor;
    }

    @RequestMapping("/admin/addSeatForm")
    public Rendering addSeatForm() {
        return Rendering.view("addSeat")
                .modelAttribute("hallList", hallService.getHallAll())
                .modelAttribute("seat", new Seat())
                .build();
    }

    @RequestMapping("/admin/addSeat")
    public Mono<Rendering> addSeat(@Valid Seat seat, BindingResult result) {
        return Mono.just(seat)
                .filter(s -> !result.hasErrors())
                .flatMap(seatService::save)
                .map(s -> new Seat())
                .defaultIfEmpty(seat)
                .map(s -> Rendering.view("addSeat")
                        .modelAttribute("hallList", hallService.getHallAll())
                        .modelAttribute("seat", s)
                        .build());
    }

    @RequestMapping("/admin/deleteSeat")
    public Mono<Rendering> deleteSeat(@ModelAttribute Seat seat) {
        return Mono.justOrEmpty(seat.getSeatId())
                .filter(seatId -> !seatId.equals(BigInteger.ZERO))
                .flatMap(seatService::getSeatById)
                .flatMap(seatService::delete)
                .thenMany(seatService.getSeatAll())
                .collectList()
                .map(seats -> Rendering.view("deleteSeat").modelAttribute("seatList", seats).build());

    }

    @RequestMapping("/admin/seatList")
    public Rendering listSeats(Model model) {
        return Rendering.view("seatList").modelAttribute("seatList", seatService.getSeatAll()).build();
    }

    @RequestMapping(value = "/admin/checkSeat/{seatId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> checkSeat(@PathVariable BigInteger seatId) {
        return Mono.justOrEmpty(seatId)
                .flatMap(seatService::getSeatById)
                .flatMapMany(ticketService::getTicketAllBySeat)
                .collectList()
                .filter(tickets -> !tickets.isEmpty())
                .map(tickets -> "На место выпущены билеты. Сначала удалите билеты.");
    }

    @RequestMapping("/admin/seatsFilter/{filmshowId}")
    @ResponseBody
    public Mono<Map<BigInteger, String>> filterSeats(@PathVariable BigInteger filmshowId) {
        return Mono.justOrEmpty(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .flatMapMany(seatService::getSeatFreeByFilmshow)
                .collect(Collectors.toMap(
                        Seat::getSeatId,
                        Seat::toString,
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
