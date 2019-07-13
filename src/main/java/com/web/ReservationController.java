package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
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
public class ReservationController {

    private final ReservationService reservationService;
    private final FilmshowService filmshowService;
    private final UserService userService;
    private final TicketService ticketService;
    private final FilmshowEditor filmshowEditor;
    private final UserEditor userEditor;
    private final TicketEditor ticketEditor;

    public ReservationController(ReservationService reservationService, FilmshowService filmshowService,
            UserService userService, TicketService ticketService, FilmshowEditor filmshowEditor, UserEditor userEditor,
            TicketEditor ticketEditor) {
        this.reservationService = reservationService;
        this.filmshowService = filmshowService;
        this.userService = userService;
        this.ticketService = ticketService;
        this.filmshowEditor = filmshowEditor;
        this.userEditor = userEditor;
        this.ticketEditor = ticketEditor;
    }

    @RequestMapping("/admin/addReservationForm")
    public Rendering addReservationForm() {
        return Rendering.view("addReservation")
                .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                .modelAttribute("userList", userService.getUserAll())
                .modelAttribute("reservation", new Reservation())
                .build();
    }

    @RequestMapping("/admin/addReservation")
    public Rendering addReservation(@Valid Reservation reservation, BindingResult result) {
        if (result.hasErrors()) {
            return Rendering.view("addReservation")
                    .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                    .modelAttribute("userList", userService.getUserAll())
                    .modelAttribute("reservation", reservation)
                    .build();
        }
        reservationService.save(reservation);
        return Rendering.redirectTo("addReservationForm").build();
    }

    @RequestMapping("/admin/deleteReservation")
    public Rendering deleteReservation(@ModelAttribute Reservation reservation) {
        if (reservation.getReservationId() != null && !reservation.getReservationId().equals(BigInteger.ZERO)) {
            reservationService.getReservationById(reservation.getReservationId()).ifPresent(reservationService::delete);
        }
        return Rendering.view("deleteReservation")
                .modelAttribute("reservationList", reservationService.getReservationAll())
                .build();
    }

    @RequestMapping("/admin/reservationList")
    public Rendering listReservations(@ModelAttribute Reservation reservation) {
        return Rendering.view("reservationList")
                .modelAttribute("userList", userService.getUserAll())
                .modelAttribute(
                        "filteredReservationList",
                        reservationService.getReservationAllByUser(reservation.getUser()))
                .build();
    }

    @RequestMapping("/admin/ticketsFilter/{filmshowId}")
    @ResponseBody
    public Map<BigInteger, String> filterTickets(@PathVariable BigInteger filmshowId) {
        return Optional.ofNullable(filmshowId)
                .flatMap(filmshowService::getFilmshowById)
                .map(ticketService::getTicketFreeByFilmshow)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(
                        Ticket::getTicketId,
                        Ticket::toString,
                        (oldValue, newValue) -> newValue,
                        LinkedHashMap::new));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
