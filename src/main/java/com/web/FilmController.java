package com.web;

import com.domain.Film;
import com.service.FilmService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.List;

@Controller
public class FilmController
{
    @Autowired
    private FilmService filmService;

    @RequestMapping(value = "/admin/addFilm", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addFilm(@RequestBody Film film) throws Exception
    {
        if(film != null && !film.getFilmName().equals(" ") && !film.getDescription().equals(" "))
        {
            List<Film> filmList = filmService.getFilmAll();
            boolean filmValid = true;
            for(Film f : filmList)
            {
                if(f.getFilmName().equals(film.getFilmName()) && f.getDescription().equals(film.getDescription()))
                {
                    filmValid = false;
                    break;
                }
            }
            if(filmValid)
            {
                return new ModelAndView("addFilm", "filmJson", filmService.create(film));
            }
            return null;
        }
        else
        {
            return null;
        }
    }

//    @RequestMapping("/admin/updateFilm")



    @RequestMapping("/admin/deleteFilm/{filmId}")
    public @ResponseBody ModelAndView deleteFilm(@PathVariable int filmId) throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        Film film = filmService.getFilmById(filmId);
        if(film != null)
        {
            filmService.delete(film);
        }
        return new ModelAndView("deleteFilm", "filmListJson", filmList);
    }

    @RequestMapping("/admin/filmList")
    public @ResponseBody ModelAndView filmList() throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("filmList", "filmListJson", filmList);
    }

}
