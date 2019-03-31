package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public String addFilmshowForm(Model model) {
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        model.addAttribute("filmList", filmList);
        model.addAttribute("hallList", hallList);
        model.addAttribute("filmshow", new Filmshow());
        return "addFilmshow";
    }

    @RequestMapping("/admin/addFilmshow")
    public String addFilmshow(@Valid Filmshow filmshow, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Film> filmList = filmService.getFilmAll();
            List<Hall> hallList = hallService.getHallAll();
            model.addAttribute("filmList", filmList);
            model.addAttribute("hallList", hallList);
            model.addAttribute("filmshow", filmshow);
            return "addFilmshow";
        }
        filmshowService.save(filmshow);
        return "redirect:addFilmshowForm";
    }

    @RequestMapping("/admin/deleteFilmshow")
    public String deleteFilmshow(@ModelAttribute Filmshow filmshow, Model model) {
        if (filmshow.getFilmshowId() != null && !filmshow.getFilmshowId().equals(BigInteger.ZERO)) {
            filmshowService.getFilmshowById(filmshow.getFilmshowId()).ifPresent(filmshowService::delete);
        }
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        model.addAttribute("filmshowList", filmshowList);
        return "deleteFilmshow";
    }

    @RequestMapping("/admin/filmshowList")
    public String listFilmshows(Model model) {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        model.addAttribute("filmshowList", filmshowList);
        return "filmshowList";
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
