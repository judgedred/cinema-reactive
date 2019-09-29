package com.web;

import com.domain.Film;
import com.service.FilmService;
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
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @RequestMapping("/admin/addFilmForm")
    public Rendering addFilmForm() {
        return Rendering.view("addFilm").modelAttribute("film", new Film()).build();
    }

    @RequestMapping("/admin/addFilm")
    public Mono<Rendering> addFilm(@Valid Film film, BindingResult result, Model model) {
        return Mono.just(film)
                .filter(f -> !result.hasErrors())
                .flatMap(filmService::save)
                .map(f -> new Film())
                .switchIfEmpty(Mono.just(film))
                .map(f -> Rendering.view("addFilm").modelAttribute("film", f).build());
    }

    @RequestMapping("/admin/deleteFilm")
    public Mono<Rendering> deleteFilm(@ModelAttribute Film film) {
        return Mono.justOrEmpty(film.getFilmId())
                .filter(filmId -> !film.getFilmId().equals(BigInteger.ZERO))
                .flatMap(filmService::getFilmById)
                .flatMap(filmService::delete)
                .thenMany(filmService.getFilmAll())
                .collectList()
                .map(filmList -> Rendering.view("deleteFilm").modelAttribute("filmList", filmList).build());
    }

    @RequestMapping("/admin/filmList")
    public Rendering listFilms() {
        return Rendering.view("filmList").modelAttribute("filmList", filmService.getFilmAll()).build();
    }

    @RequestMapping(value = "/admin/checkFilm/{filmId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> checkFilm(@PathVariable BigInteger filmId) {
        return Mono.justOrEmpty(filmId)
                .flatMap(filmService::getFilmById)
                .filter(filmService::checkFilmInFilmshow)
                .map(film -> "На фильм созданы сеансы. Сначала удалите сеансы.");
    }
}
