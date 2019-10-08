package com.web;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.FilmshowService;
import com.service.ReservationService;
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
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TicketController {

    private final TicketService ticketService;
    private final FilmshowService filmshowService;
    private final SeatService seatService;
    private final ReservationService reservationService;
    private final TicketEditor ticketEditor;
    private final FilmshowEditor filmshowEditor;
    private final SeatEditor seatEditor;

    public TicketController(TicketService ticketService, FilmshowService filmshowService, SeatService seatService,
            ReservationService reservationService, TicketEditor ticketEditor, FilmshowEditor filmshowEditor, SeatEditor seatEditor) {
        this.ticketService = ticketService;
        this.filmshowService = filmshowService;
        this.seatService = seatService;
        this.reservationService = reservationService;
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
    public Mono<Rendering> addTicket(@Valid Ticket ticket, BindingResult result) {
        return Mono.just(ticket)
                .filter(t -> !result.hasErrors())
                .flatMap(ticketService::save)
                .map(t -> new Ticket())
                .defaultIfEmpty(ticket)
                .map(t -> Rendering.view("addTicket")
                        .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                        .modelAttribute("ticket", t)
                        .build());
    }

    @RequestMapping("/admin/addTicketAllForm")
    public Rendering addTicketAllForm() {
        return Rendering.view("addTicketAll")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .modelAttribute("ticket", new Ticket())
                .build();
    }

    @RequestMapping("/admin/addTicketAll")
    public Mono<Rendering> addTicketAll(@ModelAttribute Ticket ticket) {
        return Mono.just(ticket)
                .filter(t -> t.getFilmshow() != null)
                .filter(t -> t.getPrice() != null)
                .map(Ticket::getFilmshow)
                .flatMapMany(seatService::getSeatFreeByFilmshow)
                .flatMap(seat -> ticketService.save(new Ticket(ticket.getPrice(), ticket.getFilmshow(), seat)))
                .then(Mono.just(Rendering.redirectTo("addTicketAllForm").build()));
    }

    @RequestMapping("/admin/deleteTicket")
    public Mono<Rendering> deleteTicket(@ModelAttribute Ticket ticket) {
        return Mono.justOrEmpty(ticket.getTicketId())
                .filter(ticketId -> !ticketId.equals(BigInteger.ZERO))
                .flatMap(ticketService::getTicketById)
                .flatMap(ticketService::delete)
                .thenMany(ticketService.getTicketAll())
                .collectList()
                .map(tickets -> Rendering.view("deleteTicket").modelAttribute("ticketList", tickets).build());
    }

    @RequestMapping("/admin/ticketList")
    public Rendering listTickets(@ModelAttribute Ticket ticket) {
        return Rendering.view("ticketList")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .modelAttribute("filteredTicketList", ticketService.getTicketAllByFilmshow(ticket.getFilmshow()))
                .build();
    }

    @RequestMapping("/admin/ticketsFilter/{filmshowId}")
    @ResponseBody
    public Mono<Map<BigInteger, String>> filterTickets(@PathVariable BigInteger filmshowId) {
        return Mono.justOrEmpty(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .flatMapMany(ticketService::getTicketFreeByFilmshow)
                .collect(Collectors.toMap(
                        Ticket::getTicketId,
                        Ticket::toString,
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new));
    }

    @RequestMapping(value = "/admin/checkTicket/{ticketId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public Mono<String> checkTicket(@PathVariable BigInteger ticketId) {
        return Mono.justOrEmpty(ticketId)
                .flatMap(ticketService::getTicketById)
                .flatMapMany(reservationService::getReservationAllByTicket)
                .collectList()
                .filter(reservations -> !reservations.isEmpty())
                .map(reservations -> "Билет зарезервирован. Сначала удалите бронь.");
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Ticket.class, ticketEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Seat.class, seatEditor);
    }
}
