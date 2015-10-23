package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.service.FilmService;
import com.service.FilmshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class FilmController
{
    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmshowService filmshowService;

    @RequestMapping("/admin/addFilm")
    public ModelAndView addFilm(@ModelAttribute Film film) throws Exception
    {
        if(film != null && film.getFilmName() != null && film.getDescription() != null)
        {
            filmService.create(film);
            return new ModelAndView(new RedirectView("/cinema/admin/addFilm"));
        }
        return new ModelAndView("addFilm", "film", new Film());
    }

    @RequestMapping("/admin/deleteFilm")
    public ModelAndView deleteFilm(@ModelAttribute Film film, HttpServletResponse response) throws Exception
    {
        try
        {
            if(film.getFilmId() != null && film.getFilmId() != 0)
            {
                film = filmService.getFilmById(film.getFilmId());
                if(film != null)
                {
                    filmService.delete(film);
                    return new ModelAndView(new RedirectView("deleteFilm"));
                }
            }
            List<Film> filmList = filmService.getFilmAll();
            response.setStatus(HttpServletResponse.SC_OK);
            return new ModelAndView("deleteFilm", "filmList", filmList);
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/admin/filmList")
    public ModelAndView listFilms() throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        return new ModelAndView("filmList", "filmList", filmList);
    }

    @RequestMapping(value = "/admin/filmCheck/{filmId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String filmCheck(@PathVariable Integer filmId) throws Exception
    {
        if(filmId != null)
        {
            Film film = filmService.getFilmById(filmId);
            if(film != null)
            {
                List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
                for(Filmshow f : filmshowList)
                {
                    if(f.getFilm().equals(film))
                    {
                        return "На фильм созданы сеансы. Сначала удалите сеансы.";
                    }
                }
            }
        }
        return null;
    }
}
