package com.web;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.FilmshowService;
import com.service.SeatService;
import com.service.TicketService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final FilmshowService filmshowService;
    private final SeatService seatService;
    private final TicketEditor ticketEditor;
    private final FilmshowEditor filmshowEditor;
    private final SeatEditor seatEditor;

    public TicketController(TicketService ticketService, FilmshowService filmshowService, SeatService seatService,
            TicketEditor ticketEditor, FilmshowEditor filmshowEditor, SeatEditor seatEditor) {
        this.ticketService = ticketService;
        this.filmshowService = filmshowService;
        this.seatService = seatService;
        this.ticketEditor = ticketEditor;
        this.filmshowEditor = filmshowEditor;
        this.seatEditor = seatEditor;
    }

    @RequestMapping("/admin/addTicketForm")
    public ModelAndView addTicketForm() {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicket");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("ticket", new Ticket());
        return mav;
    }

    @RequestMapping("/admin/addTicket")
    public ModelAndView addTicket(@Valid Ticket ticket, BindingResult result) {
        if (result.hasErrors()) {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            ModelAndView mav = new ModelAndView("addTicket");
            mav.addObject("filmshowList", filmshowList);
            mav.addObject("ticket", ticket);
            return mav;
        }
        ticketService.save(ticket);
        return new ModelAndView(new RedirectView("addTicketForm"));
    }

    @RequestMapping("/admin/addTicketAllForm")
    public ModelAndView addTicketAllForm() {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("addTicketAll");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("ticket", new Ticket());
        return mav;
    }

    @RequestMapping("/admin/addTicketAll")
    public ModelAndView addTicketAll(@ModelAttribute Ticket ticket, HttpServletRequest request) {
        List<Seat> filteredSeats = (List<Seat>) request.getSession().getAttribute("filteredSeats");
        if (ticket.getFilmshow() != null && ticket.getPrice() != null && filteredSeats != null) {
            for (Seat s : filteredSeats) {
                ticket.setSeat(s);
                ticketService.save(ticket);
            }
        }
        return new ModelAndView(new RedirectView("addTicketAllForm"));
    }

    @RequestMapping("/admin/deleteTicket")
    public ModelAndView deleteTicket(@ModelAttribute Ticket ticket) {
        if (ticket.getTicketId() != null && !ticket.getTicketId().equals(BigInteger.ZERO)) {
            ticketService.getTicketById(ticket.getTicketId()).ifPresent(ticketService::delete);
        }
        List<Ticket> ticketList = ticketService.getTicketAll();
        return new ModelAndView("deleteTicket", "ticketList", ticketList);
    }

    @RequestMapping("/admin/ticketList")
    public ModelAndView listTickets(@ModelAttribute Ticket ticket) {
        List<Ticket> ticketList = ticketService.getTicketAllByFilmshow(ticket.getFilmshow());
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        ModelAndView mav = new ModelAndView("ticketList");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("filteredTicketList", ticketList);
        return mav;
    }

    @RequestMapping("/admin/seatsFilter/{filmshowId}")
    @ResponseBody
    public Map<BigInteger, String> filterSeats(@PathVariable int filmshowId, HttpServletRequest request) {
        return Optional.of(filmshowId)
                .map(BigInteger::valueOf)
                .flatMap(filmshowService::getFilmshowById)
                .map(seatService::getSeatFreeByFilmshow)
                .map(seats -> {
                    request.getSession().setAttribute("filteredSeats", seats);
                    return seats;
                })
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(Seat::getSeatId, Seat::toString));
    }

    @RequestMapping(value = "/admin/checkTicket/{ticketId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkTicket(@PathVariable Integer ticketId) {
        return Optional.ofNullable(ticketId)
                .map(BigInteger::valueOf)
                .flatMap(ticketService::getTicketById)
                .filter(ticketService::checkTicketInReservation)
                .map(ticket -> "Билет зарезервирован. Сначала удалите бронь.")
                .orElse(null);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Seat.class, seatEditor);
    }
}
