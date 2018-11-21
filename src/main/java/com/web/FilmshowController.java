package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
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
import java.util.List;
import java.util.Optional;

@Controller
public class FilmshowController {

    private final FilmshowService filmshowService;
    private final HallService hallService;
    private final FilmService filmService;
    private final FilmEditor filmEditor;
    private final HallEditor hallEditor;

    public FilmshowController(FilmshowService filmshowService, HallService hallService, FilmService filmService,
            FilmEditor filmEditor, HallEditor hallEditor) {
        this.filmshowService = filmshowService;
        this.hallService = hallService;
        this.filmService = filmService;
        this.filmEditor = filmEditor;
        this.hallEditor = hallEditor;
    }

    @RequestMapping("/admin/addFilmshowForm")
    public ModelAndView addFilmshowForm() {
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);
        mav.addObject("filmshow", new Filmshow());
        return mav;
    }

    @RequestMapping("/admin/addFilmshow")
    public ModelAndView addFilmshow(@Valid Filmshow filmshow, BindingResult result) {
        if (result.hasErrors()) {
            List<Film> filmList = filmService.getFilmAll();
            List<Hall> hallList = hallService.getHallAll();
            ModelAndView mav = new ModelAndView("addFilmshow");
            mav.addObject("filmList", filmList);
            mav.addObject("hallList", hallList);
            mav.addObject("filmshow", filmshow);
            return mav;
        }
        filmshowService.save(filmshow);
        return new ModelAndView(new RedirectView("addFilmshowForm"));
    }

    @RequestMapping("/admin/deleteFilmshow")
    public ModelAndView deleteFilmshow(@ModelAttribute Filmshow filmshow) {
        if (filmshow.getFilmshowId() != null && !filmshow.getFilmshowId().equals(BigInteger.ZERO)) {
            filmshowService.getFilmshowById(filmshow.getFilmshowId()).ifPresent(filmshowService::delete);
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("deleteFilmshow", "filmshowList", filmshowList);
    }

    @RequestMapping("/admin/filmshowList")
    public ModelAndView listFilmshows() {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("filmshowList", "filmshowList", filmshowList);
    }

    @RequestMapping(value = "/admin/checkFilmshow/{filmshowId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkFilmshow(@PathVariable BigInteger filmshowId) {
        return Optional.ofNullable(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .filter(filmshowService::checkFilmshowInTicket)
                .map(filmshow -> "На сеанс имеются билеты. Сначала удалите билеты.")
                .orElse(null);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Film.class, filmEditor);
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
