package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Controller
public class FilmshowController {

    private final FilmshowService filmshowService;
    private final HallService hallService;
    private final FilmService filmService;
    private final TicketService ticketService;
    private final FilmEditor filmEditor;
    private final HallEditor hallEditor;

    public FilmshowController(FilmshowService filmshowService, HallService hallService, FilmService filmService,
            TicketService ticketService, FilmEditor filmEditor, HallEditor hallEditor) {
        this.filmshowService = filmshowService;
        this.hallService = hallService;
        this.filmService = filmService;
        this.ticketService = ticketService;
        this.filmEditor = filmEditor;
        this.hallEditor = hallEditor;
    }

    @RequestMapping("/admin/addFilmshowForm")
    public Rendering addFilmshowForm() {
        return Rendering.view("addFilmshow")
                .modelAttribute("filmList", filmService.getFilmAll())
                .modelAttribute("hallList", hallService.getHallAll())
                .modelAttribute("filmshow", new Filmshow())
                .build();
    }

    @RequestMapping("/admin/addFilmshow")
    public Mono<Rendering> addFilmshow(@Valid Filmshow filmshow, BindingResult result) {
        return Mono.just(filmshow)
                .filter(show -> !result.hasErrors())
                .flatMap(filmshowService::save)
                .thenReturn(Rendering.redirectTo("addFilmshowForm").build())
                .defaultIfEmpty(Rendering.view("addFilmshow")
                        .modelAttribute("filmList", filmService.getFilmAll())
                        .modelAttribute("hallList", hallService.getHallAll())
                        .modelAttribute("filmshow", filmshow)
                        .build());
    }

    @RequestMapping("/admin/deleteFilmshow")
    public Mono<Rendering> deleteFilmshow(@ModelAttribute Filmshow filmshow) {
        return Mono.justOrEmpty(filmshow.getFilmshowId())
                .filter(filmshowId -> !filmshowId.equals(BigInteger.ZERO))
                .flatMap(filmshowService::getFilmshowById)
                .flatMap(filmshowService::delete)
                .thenMany(filmshowService.getFilmshowAll())
                .collectList()
                .map(filmshowList -> Rendering.view("deleteFilmshow")
                        .modelAttribute("filmshowList", filmshowList)
                        .build());
    }

    @RequestMapping("/admin/filmshowList")
    public Rendering listFilmshows(Model model) {
        return Rendering.view("filmshowList").modelAttribute("filmshowList", filmshowService.getFilmshowAll()).build();
    }

    @RequestMapping(value = "/admin/checkFilmshow/{filmshowId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> checkFilmshow(@PathVariable BigInteger filmshowId) {
        return Mono.justOrEmpty(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .flatMapMany(filmshow -> Flux.fromIterable(ticketService.getTicketAllByFilmshow(filmshow)))
                .collectList()
                .map(filmshows -> "На сеанс имеются билеты. Сначала удалите билеты.");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Film.class, filmEditor);
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
