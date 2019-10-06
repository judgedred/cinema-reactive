package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.result.view.Rendering;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.math.BigInteger;

@Controller
public class ReservationController {

    private final ReservationService reservationService;
    private final FilmshowService filmshowService;
    private final UserService userService;
    private final FilmshowEditor filmshowEditor;
    private final UserEditor userEditor;
    private final TicketEditor ticketEditor;

    public ReservationController(ReservationService reservationService, FilmshowService filmshowService,
            UserService userService, FilmshowEditor filmshowEditor, UserEditor userEditor, TicketEditor ticketEditor) {
        this.reservationService = reservationService;
        this.filmshowService = filmshowService;
        this.userService = userService;
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
    public Mono<Rendering> addReservation(@Valid Reservation reservation, BindingResult result) {
        return Mono.just(reservation)
                .filter(r -> !result.hasErrors())
                .flatMap(reservationService::save)
                .thenReturn(Rendering.redirectTo("addReservationForm").build())
                .defaultIfEmpty(Rendering.view("addReservation")
                        .modelAttribute("filmshowList", filmshowService.getFilmshowAll())
                        .modelAttribute("userList", userService.getUserAll())
                        .modelAttribute("reservation", reservation)
                        .build());
    }

    @RequestMapping("/admin/deleteReservation")
    public Mono<Rendering> deleteReservation(@ModelAttribute Reservation reservation) {
        return Mono.justOrEmpty(reservation.getReservationId())
                .filter(reservationId -> !reservationId.equals(BigInteger.ZERO))
                .flatMap(reservationService::getReservationById)
                .flatMap(reservationService::delete)
                .thenMany(reservationService.getReservationAll())
                .collectList()
                .map(reservations -> Rendering.view("deleteReservation")
                        .modelAttribute("reservationList", reservations)
                        .build());
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



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
