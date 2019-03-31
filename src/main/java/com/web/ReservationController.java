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
    public String addReservationForm(Model model) {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        List<User> userList = userService.getUserAll();
        model.addAttribute("filmshowList", filmshowList);
        model.addAttribute("userList", userList);
        model.addAttribute("reservation", new Reservation());
        return "addReservation";
    }

    @RequestMapping("/admin/addReservation")
    public String addReservation(@Valid Reservation reservation, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            List<User> userList = userService.getUserAll();
            model.addAttribute("filmshowList", filmshowList);
            model.addAttribute("userList", userList);
            model.addAttribute("reservation", reservation);
            return "addReservation";
        }
        reservationService.save(reservation);
        return "redirect:addReservationForm";
    }

    @RequestMapping("/admin/deleteReservation")
    public String deleteReservation(@ModelAttribute Reservation reservation, Model model) {
        if (reservation.getReservationId() != null && !reservation.getReservationId().equals(BigInteger.ZERO)) {
            reservationService.getReservationById(reservation.getReservationId()).ifPresent(reservationService::delete);
        }
        List<Reservation> reservationList = reservationService.getReservationAll();
        model.addAttribute("reservationList", reservationList);
        return "deleteReservation";
    }

    @RequestMapping("/admin/reservationList")
    public String listReservations(@ModelAttribute Reservation reservation, Model model) {
        List<Reservation> reservationList = reservationService.getReservationAllByUser(reservation.getUser());
        List<User> userList = userService.getUserAll();
        model.addAttribute("userList", userList);
        model.addAttribute("filteredReservationList", reservationList);
        return "reservationList";
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
