package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.List;

@Controller
public class FilmshowController
{
    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private HallService hallService;

    @Autowired
    private FilmService filmService;

    @RequestMapping("/admin/addFilmshow")
    public ModelAndView addFilmshow(@ModelAttribute Filmshow filmshow, BindingResult result) throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);
        if(filmshow != null && filmshow.getFilm() != null
                && filmshow.getHall() != null && filmshow.getDateTime() != null)
        {
            List<Filmshow> filmshowLst = filmshowService.getFilmshowAll();
            boolean filmshowValid = true;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm");
            for(Filmshow f : filmshowLst)
            {
                if(f.getFilm().equals(filmshow.getFilm())
                        && f.getHall().equals(filmshow.getHall())
                        && dateFormat.format(f.getDateTime()).equals(dateFormat.format(filmshow.getDateTime())))
                {
                    filmshowValid = false;
                    break;
                }
            }
            if(filmshowValid)
            {
                filmshowService.create(filmshow);
                mav.addObject("filmshow", filmshow);
            }
        }
        return mav;
    }

//    @RequestMapping("/admin/updateFilmshow")

    @RequestMapping("/admin/deleteFilmshow")
    public ModelAndView deleteFilmshow(@ModelAttribute Filmshow filmshow) throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
//        Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
        if(filmshow != null)
        {
            filmshowService.delete(filmshow);
        }
        return new ModelAndView("deleteFilmshow", "filmshowList", filmshowList);
    }

    @RequestMapping("/admin/filmshowList")
    public ModelAndView listFilmshows() throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("filmshowList", "filmshowList", filmshowList);
    }
}
