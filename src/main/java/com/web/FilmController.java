package com.web;

import com.domain.Film;
import com.service.FilmService;
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
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @RequestMapping("/admin/addFilmForm")
    public ModelAndView addFilmForm() {
        return new ModelAndView("addFilm", "film", new Film());
    }

    @RequestMapping("/admin/addFilm")
    public ModelAndView addFilm(@Valid Film film, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("addFilm", "film", film);
        }
        filmService.save(film);
        return new ModelAndView("addFilm", "film", new Film());
    }

    @RequestMapping("/admin/deleteFilm")
    public ModelAndView deleteFilm(@ModelAttribute Film film) {
        if (film.getFilmId() != null && !film.getFilmId().equals(BigInteger.ZERO)) {
            filmService.getFilmById(film.getFilmId()).ifPresent(filmService::delete);
        }
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("deleteFilm", "filmList", filmList);
    }

    @RequestMapping("/admin/filmList")
    public ModelAndView listFilms() {
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("filmList", "filmList", filmList);
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
