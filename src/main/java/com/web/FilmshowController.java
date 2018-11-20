package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class FilmshowController {

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private HallService hallService;

    @Qualifier("defaultFilmService")
    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmEditor filmEditor;

    @Autowired
    private HallEditor hallEditor;

    @RequestMapping("/admin/addFilmshowForm")
    public ModelAndView addFilmshowForm() throws Exception {
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);
        mav.addObject("filmshow", new Filmshow());
        return mav;
    }

    @RequestMapping("/admin/addFilmshow")
    public ModelAndView addFilmshow(@Valid Filmshow filmshow, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<Film> filmList = filmService.getFilmAll();
            List<Hall> hallList = hallService.getHallAll();
            ModelAndView mav = new ModelAndView("addFilmshow");
            mav.addObject("filmList", filmList);
            mav.addObject("hallList", hallList);
            mav.addObject("filmshow", filmshow);
            return mav;
        }
        filmshowService.create(filmshow);
        return new ModelAndView(new RedirectView("addFilmshowForm"));
    }

    @RequestMapping("/admin/deleteFilmshow")
    public ModelAndView deleteFilmshow(@ModelAttribute Filmshow filmshow) throws Exception {
        if (filmshow.getFilmshowId() != null && !filmshow.getFilmshowId().equals(BigInteger.ZERO)) {
            filmshow = filmshowService.getFilmshowById(filmshow.getFilmshowId());
            if (filmshow != null) {
                filmshowService.delete(filmshow);
            }
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("deleteFilmshow", "filmshowList", filmshowList);
    }

    @RequestMapping("/admin/filmshowList")
    public ModelAndView listFilmshows() throws Exception {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("filmshowList", "filmshowList", filmshowList);
    }

    @RequestMapping(value = "/admin/checkFilmshow/{filmshowId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody
    String checkFilmshow(@PathVariable Integer filmshowId) throws Exception {
        if (filmshowId != null) {
            Filmshow filmshow = filmshowService.getFilmshowById(BigInteger.valueOf(filmshowId));
            if (filmshow != null && filmshowService.checkFilmshowInTicket(filmshow)) {
                return "На сеанс имеются билеты. Сначала удалите билеты.";
            }
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true));
        binder.registerCustomEditor(Film.class, filmEditor);
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
