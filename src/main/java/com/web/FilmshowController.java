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
import org.springframework.web.reactive.result.view.Rendering;

import javax.validation.Valid;
import java.math.BigInteger;
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
    public Rendering addFilmshowForm() {
        return Rendering.view("addFilmshow")
                .modelAttribute("filmList", filmService.getFilmAll())
                .modelAttribute("hallList", hallService.getHallAll())
                .modelAttribute("filmshow", new Filmshow())
                .build();
    }

    @RequestMapping("/admin/addFilmshow")
    public Rendering addFilmshow(@Valid Filmshow filmshow, BindingResult result) {
        if (result.hasErrors()) {
            return Rendering.view("addFilmshow")
                    .modelAttribute("filmList", filmService.getFilmAll())
                    .modelAttribute("hallList", hallService.getHallAll())
                    .modelAttribute("filmshow", filmshow)
                    .build();
        }
        filmshowService.save(filmshow);
        return Rendering.redirectTo("addFilmshowForm").build();
    }

    @RequestMapping("/admin/deleteFilmshow")
    public Rendering deleteFilmshow(@ModelAttribute Filmshow filmshow) {
        if (filmshow.getFilmshowId() != null && !filmshow.getFilmshowId().equals(BigInteger.ZERO)) {
            filmshowService.getFilmshowById(filmshow.getFilmshowId()).ifPresent(filmshowService::delete);
        }
        return Rendering.view("deleteFilmshow")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .build();
    }

    @RequestMapping("/admin/filmshowList")
    public Rendering listFilmshows(Model model) {
        return Rendering.view("filmshowList").modelAttribute("filmshowList", filmshowService.getFilmshowAll()).build();
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
