package com.web;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Ticket;
import com.service.FilmService;
import com.service.FilmshowService;
import com.service.HallService;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletResponse;
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
    private TicketService ticketService;

    @Autowired
    private FilmEditor filmEditor;

    @Autowired
    private HallEditor hallEditor;

    @RequestMapping("/admin/addFilmshow")
    public ModelAndView addFilmshow(@ModelAttribute Filmshow filmshow) throws Exception
    {
        if(filmshow != null && filmshow.getFilm() != null
                && filmshow.getHall() != null && filmshow.getDateTime() != null)
        {
            filmshowService.create(filmshow);
            return new ModelAndView(new RedirectView("/cinema/admin/addFilmshow"));
        }
        List<Film> filmList = filmService.getFilmAll();
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addFilmshow");
        mav.addObject("filmList", filmList);
        mav.addObject("hallList", hallList);
        return mav;
    }

    @RequestMapping("/admin/deleteFilmshow")
    public ModelAndView deleteFilmshow(@ModelAttribute Filmshow filmshow, HttpServletResponse response) throws Exception
    {
        try
        {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            ModelAndView mav = new ModelAndView("deleteFilmshow");
            if(filmshow.getFilmshowId() != null && filmshow.getFilmshowId() != 0)
            {
                filmshow = filmshowService.getFilmshowById(filmshow.getFilmshowId());
                if(filmshow != null)
                {
                    filmshowService.delete(filmshow);
                    return new ModelAndView(new RedirectView("/cinema/admin/deleteFilmshow"));
                }
            }
            mav.addObject("filmshowList", filmshowList);
            response.setStatus(HttpServletResponse.SC_OK);
            return mav;
        }
        catch(Exception e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/admin/filmshowList")
    public ModelAndView listFilmshows() throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        return new ModelAndView("filmshowList", "filmshowList", filmshowList);
    }

    @RequestMapping(value = "/admin/filmshowCheck/{filmshowId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody String filmshowCheck(@PathVariable int filmshowId) throws Exception
    {
        Filmshow filmshow = filmshowService.getFilmshowById(filmshowId);
        if(filmshow != null)
        {
            List<Ticket> ticketList = ticketService.getTicketAll();
            for(Ticket t : ticketList)
            {
                if(t.getFilmshow().equals(filmshow))
                {
                    return "На сеанс имеются билеты. Сначала удалите билеты.";
                }
            }
        }
        return null;
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
