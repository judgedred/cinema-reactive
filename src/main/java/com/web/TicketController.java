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
import org.springframework.web.reactive.result.view.Rendering;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collections;
import java.util.LinkedHashMap;
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
    public Rendering addTicketForm() {
        return Rendering.view("addTicket")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .modelAttribute("ticket", new Ticket())
                .build();
    }

    @RequestMapping("/admin/addTicket")
    public Rendering addTicket(@Valid Ticket ticket, BindingResult result) {
        if (result.hasErrors()) {
            return Rendering.view("addTicket")
                    .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                    .modelAttribute("ticket", ticket)
                    .build();
        }
        ticketService.save(ticket);
        return Rendering.redirectTo("addTicketForm").build();
    }

    @RequestMapping("/admin/addTicketAllForm")
    public Rendering addTicketAllForm() {
        return Rendering.view("addTicketAll")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .modelAttribute("ticket", new Ticket())
                .build();
    }

    @RequestMapping("/admin/addTicketAll")
    public Rendering addTicketAll(@ModelAttribute Ticket ticket) {
        Optional.of(ticket)
                .filter(t -> t.getFilmshow() != null)
                .filter(t -> t.getPrice() != null)
                .map(Ticket::getFilmshow)
                .map(seatService::getSeatFreeByFilmshow)
                .orElse(Collections.emptyList())
                .forEach(seat -> ticketService.save(new Ticket(ticket.getPrice(), ticket.getFilmshow(), seat)));
        return Rendering.redirectTo("addTicketAllForm").build();
    }

    @RequestMapping("/admin/deleteTicket")
    public Rendering deleteTicket(@ModelAttribute Ticket ticket) {
        if (ticket.getTicketId() != null && !ticket.getTicketId().equals(BigInteger.ZERO)) {
            ticketService.getTicketById(ticket.getTicketId()).ifPresent(ticketService::delete);
        }
        return Rendering.view("deleteTicket").modelAttribute("ticketList", ticketService.getTicketAll()).build();
    }

    @RequestMapping("/admin/ticketList")
    public Rendering listTickets(@ModelAttribute Ticket ticket) {
        return Rendering.view("ticketList")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .modelAttribute("filteredTicketList", ticketService.getTicketAllByFilmshow(ticket.getFilmshow()))
                .build();
    }

    @RequestMapping("/admin/seatsFilter/{filmshowId}")
    @ResponseBody
    public Map<BigInteger, String> filterSeats(@PathVariable BigInteger filmshowId) {
        return Optional.of(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .map(seatService::getSeatFreeByFilmshow)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        Seat::getSeatId,
                        Seat::toString,
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new));
    }

    @RequestMapping(value = "/admin/checkTicket/{ticketId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkTicket(@PathVariable BigInteger ticketId) {
        return Optional.ofNullable(ticketId)
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
