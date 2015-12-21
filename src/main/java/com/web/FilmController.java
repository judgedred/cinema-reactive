package com.web;

import com.domain.Film;
import com.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.validation.Valid;
import java.util.List;

@Controller
public class FilmController
{
    @Autowired
    private FilmService filmService;

    @RequestMapping("/admin/addFilmForm")
    public ModelAndView addFilmForm()
    {
        return new ModelAndView("addFilm", "film", new Film());
    }

    @RequestMapping("/admin/addFilm")
    public ModelAndView addFilm(@Valid Film film, BindingResult result) throws Exception
    {
        if(result.hasErrors())
        {
            return new ModelAndView("addFilm", "film", film);
        }
        filmService.create(film);
        return new ModelAndView("addFilm", "film", new Film());
    }

    @RequestMapping("/admin/deleteFilm")
    public ModelAndView deleteFilm(@ModelAttribute Film film) throws Exception
    {
        if(film.getFilmId() != null && film.getFilmId() != 0)
        {
            film = filmService.getFilmById(film.getFilmId());
            if(film != null)
            {
                filmService.delete(film);
            }
        }
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("deleteFilm", "filmList", filmList);
    }

    @RequestMapping("/admin/filmList")
    public ModelAndView listFilms() throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("filmList", "filmList", filmList);
    }

    @RequestMapping(value = "/admin/checkFilm/{filmId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String checkFilm(@PathVariable Integer filmId) throws Exception
    {
        if(filmId != null)
        {
            Film film = filmService.getFilmById(filmId);
            if(film != null && filmService.checkFilmInFilmshow(film))
            {
                return "На фильм созданы сеансы. Сначала удалите сеансы.";
            }
        }
        return null;
    }
}
