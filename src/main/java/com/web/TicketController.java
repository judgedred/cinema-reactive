package com.web;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.FilmshowService;
import com.service.SeatService;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private TicketEditor ticketEditor;

    @Autowired
    private FilmshowEditor filmshowEditor;

    @Autowired
    private SeatEditor seatEditor;

    private List<Seat> filteredSeatList = new ArrayList<>();

    @RequestMapping("/admin/addTicketForm")
    public ModelAndView addTicketForm() throws Exception {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("ticket", new Ticket());
        return mav;
    }

    @RequestMapping("/admin/addTicket")
    public ModelAndView addTicket(@Valid Ticket ticket, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            ModelAndView mav = new ModelAndView("addTicket");
            mav.addObject("filmshowList", filmshowList);
            mav.addObject("ticket", ticket);
            return mav;
        }
        ticketService.create(ticket);
        return new ModelAndView(new RedirectView("addTicketForm"));
    }

    @RequestMapping("/admin/addTicketAllForm")
    public ModelAndView addTicketAllForm() throws Exception {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicketAll");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("ticket", new Ticket());
        return mav;
    }

    @RequestMapping("/admin/addTicketAll")
    public ModelAndView addTicketAll(@ModelAttribute Ticket ticket) throws Exception {
        if (ticket.getFilmshow() != null && ticket.getPrice() != null && filteredSeatList != null) {
            for (Seat s : filteredSeatList) {
                ticket.setSeat(s);
                ticketService.create(ticket);
            }
        }
        return new ModelAndView(new RedirectView("addTicketAllForm"));
    }

    @RequestMapping("/admin/deleteTicket")
    public ModelAndView deleteTicket(@ModelAttribute Ticket ticket) throws Exception {
        if (ticket.getTicketId() != null && ticket.getTicketId() != 0) {
            ticket = ticketService.getTicketById(ticket.getTicketId());
            if (ticket != null) {
                ticketService.delete(ticket);
            }
        }
        List<Ticket> ticketList = ticketService.getTicketAll();
        return new ModelAndView("deleteTicket", "ticketList", ticketList);
    }

    @RequestMapping("/admin/ticketList")
    public ModelAndView listTickets(@ModelAttribute Ticket ticket) throws Exception {
        List<Ticket> ticketList = ticketService.getTicketAllByFilmshow(ticket.getFilmshow());
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("ticketList");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("filteredTicketList", ticketList);
        return mav;
    }

    @RequestMapping("/admin/seatsFilter/{filmshowId}")
    public @ResponseBody
    Map<Integer, String> filterSeats(@PathVariable int filmshowId) throws Exception {
        Filmshow filmshow = filmshowService.getFilmshowById(BigInteger.valueOf(filmshowId));
        Map<Integer, String> filteredSeatMap = new HashMap<>();
        if (filmshow != null) {
            filteredSeatList = seatService.getSeatFreeByFilmshow(filmshow);
            for (Seat s : filteredSeatList) {
                filteredSeatMap.put(s.getSeatId(), s.toString());
            }
        }
        return filteredSeatMap;
    }

    @RequestMapping(value = "/admin/checkTicket/{ticketId}", produces = "text/html; charset=UTF-8")
    public @ResponseBody
    String checkTicket(@PathVariable Integer ticketId) throws Exception {
        if (ticketId != null) {
            Ticket ticket = ticketService.getTicketById(ticketId);
            if (ticket != null && ticketService.checkTicketInReservation(ticket)) {
                return "Билет зарезервирован. Сначала удалите бронь.";
            }
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Seat.class, seatEditor);
    }
}
