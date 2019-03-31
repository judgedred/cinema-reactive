package com.web;

import com.domain.Filmshow;
import com.domain.Seat;
import com.domain.Ticket;
import com.service.FilmshowService;
import com.service.SeatService;
import com.service.TicketService;
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
import java.util.Collections;
import java.util.LinkedHashMap;
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
    public String addTicketForm(Model model) {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        model.addAttribute("filmshowList", filmshowList);
        model.addAttribute("ticket", new Ticket());
        return "addTicket";
    }

    @RequestMapping("/admin/addTicket")
    public String addTicket(@Valid Ticket ticket, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            model.addAttribute("filmshowList", filmshowList);
            model.addAttribute("ticket", ticket);
            return "addTicket";
        }
        ticketService.save(ticket);
        return "redirect:addTicketForm";
    }

    @RequestMapping("/admin/addTicketAllForm")
    public String addTicketAllForm(Model model) {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        model.addAttribute("filmshowList", filmshowList);
        model.addAttribute("ticket", new Ticket());
        return "addTicketAll";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping("/admin/addTicketAll")
    public String addTicketAll(@ModelAttribute Ticket ticket) {
        Optional.of(ticket)
                .filter(t -> t.getFilmshow() != null)
                .filter(t -> t.getPrice() != null)
                .map(Ticket::getFilmshow)
                .map(seatService::getSeatFreeByFilmshow)
                .orElse(Collections.emptyList())
                .forEach(seat -> ticketService.save(new Ticket(ticket.getPrice(), ticket.getFilmshow(), seat)));
        return "redirect:addTicketAllForm";
    }

    @RequestMapping("/admin/deleteTicket")
    public String deleteTicket(@ModelAttribute Ticket ticket, Model model) {
        if (ticket.getTicketId() != null && !ticket.getTicketId().equals(BigInteger.ZERO)) {
            ticketService.getTicketById(ticket.getTicketId()).ifPresent(ticketService::delete);
        }
        List<Ticket> ticketList = ticketService.getTicketAll();
        model.addAttribute("ticketList", ticketList);
        return "deleteTicket";
    }

    @RequestMapping("/admin/ticketList")
    public String listTickets(@ModelAttribute Ticket ticket, Model model) {
        List<Ticket> ticketList = ticketService.getTicketAllByFilmshow(ticket.getFilmshow());
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        model.addAttribute("filmshowList", filmshowList);
        model.addAttribute("filteredTicketList", ticketList);
        return "ticketList";
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
