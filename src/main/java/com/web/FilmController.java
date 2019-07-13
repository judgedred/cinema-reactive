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

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Optional;

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
    public Rendering addFilm(@Valid Film film, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("film", film);
        } else {
            filmService.save(film);
            model.addAttribute("film", new Film());
        }
        return Rendering.view("addFilm").build();
    }

    @RequestMapping("/admin/deleteFilm")
    public Rendering deleteFilm(@ModelAttribute Film film) {
        if (film.getFilmId() != null && !film.getFilmId().equals(BigInteger.ZERO)) {
            filmService.getFilmById(film.getFilmId()).ifPresent(filmService::delete);
        }
        return Rendering.view("deleteFilm").modelAttribute("filmList", filmService.getFilmAll()).build();
    }

    @RequestMapping("/admin/filmList")
    public Rendering listFilms() {
        return Rendering.view("filmList").modelAttribute("filmList", filmService.getFilmAll()).build();
    }

    @RequestMapping(value = "/admin/checkFilm/{filmId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkFilm(@PathVariable BigInteger filmId) {
        return Optional.ofNullable(filmId)
                .flatMap(filmService::getFilmById)
                .filter(filmService::checkFilmInFilmshow)
                .map(film -> "На фильм созданы сеансы. Сначала удалите сеансы.")
                .orElse(null);
    }
}
