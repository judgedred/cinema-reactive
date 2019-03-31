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

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Controller
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @RequestMapping("/admin/addFilmForm")
    public String addFilmForm(Model model) {
        model.addAttribute("film", new Film());
        return "addFilm";
    }

    @RequestMapping("/admin/addFilm")
    public String addFilm(@Valid Film film, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("film", film);
        } else {
            filmService.save(film);
            model.addAttribute("film", new Film());
        }
        return "addFilm";
    }

    @RequestMapping("/admin/deleteFilm")
    public String deleteFilm(@ModelAttribute Film film, Model model) {
        if (film.getFilmId() != null && !film.getFilmId().equals(BigInteger.ZERO)) {
            filmService.getFilmById(film.getFilmId()).ifPresent(filmService::delete);
        }
        List<Film> filmList = filmService.getFilmAll();
        model.addAttribute("filmList", filmList);
        return "deleteFilm";
    }

    @RequestMapping("/admin/filmList")
    public String listFilms(Model model) {
        List<Film> filmList = filmService.getFilmAll();
        model.addAttribute("filmList", filmList);
        return "filmList";
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
