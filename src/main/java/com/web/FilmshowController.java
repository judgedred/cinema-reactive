package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.text.SimpleDateFormat;
import java.util.Date;
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

    @Autowired
    private FilmEditor filmEditor;

    @Autowired
    private HallEditor hallEditor;

    @RequestMapping("/admin/addFilmshow")
    public ModelAndView addFilmshow(@ModelAttribute Filmshow filmshow) throws Exception
    {
        /*List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);*/
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
                return new ModelAndView(new RedirectView("/cinema/admin/addFilmshow"));
            }
        }
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);
        return mav;
    }

    /*@RequestMapping(value = "/admin/addFilmshow", method = RequestMethod.GET)
    public ModelAndView addFilmshowForm() throws Exception
    {
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);
        mav.addObject("filmshow", new Filmshow());
        return mav;
    }*/

//    @RequestMapping("/admin/updateFilmshow")

    @RequestMapping("/admin/deleteFilmshow")
    public ModelAndView deleteFilmshow(@ModelAttribute Filmshow filmshow) throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("deleteFilmshow");
        if(filmshow != null && filmshow.getFilmshowId() != null && filmshow.getFilmshowId() != 0)
        {
            filmshow = filmshowService.getFilmshowById(filmshow.getFilmshowId());
            filmshowService.delete(filmshow);
            return new ModelAndView(new RedirectView("/cinema/admin/deleteFilmshow"));
        }
        mav.addObject("filmshowList", filmshowList);
        return mav;
    }

   /* @RequestMapping("/admin/deleteFilmshow/{filmshowId}")
    public ModelAndView deleteFilmshow(@PathVariable int filmshowId) throws Exception
    {
           Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
            filmshowService.delete(filmshow);

        return new ModelAndView(new RedirectView("/cinema/admin/deleteFilmshow"));
    }*/

    @RequestMapping("/admin/filmshowList")
    public ModelAndView listFilmshows() throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("filmshowList", "filmshowList", filmshowList);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        binder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
        binder.registerCustomEditor(Film.class, filmEditor);
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
