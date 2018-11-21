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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collections;
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
    public ModelAndView addReservationForm() {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("addReservation");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("userList", userList);
        mav.addObject("reservation", new Reservation());
        return mav;
    }

    @RequestMapping("/admin/addReservation")
    public ModelAndView addReservation(@Valid Reservation reservation, BindingResult result) {
        if (result.hasErrors()) {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            List<User> userList = userService.getUserAll();
            ModelAndView mav = new ModelAndView("addReservation");
            mav.addObject("filmshowList", filmshowList);
            mav.addObject("userList", userList);
            mav.addObject("reservation", reservation);
            return mav;
        }
        reservationService.save(reservation);
        return new ModelAndView(new RedirectView("addReservationForm"));
    }

    @RequestMapping("/admin/deleteReservation")
    public ModelAndView deleteReservation(@ModelAttribute Reservation reservation) {
        if (reservation.getReservationId() != null && !reservation.getReservationId().equals(BigInteger.ZERO)) {
            reservationService.getReservationById(reservation.getReservationId()).ifPresent(reservationService::delete);
        }
        List<Reservation> reservationList = reservationService.getReservationAll();
        return new ModelAndView("deleteReservation", "reservationList", reservationList);
    }

    @RequestMapping("/admin/reservationList")
    public ModelAndView listReservations(@ModelAttribute Reservation reservation) {
        List<Reservation> reservationList = reservationService.getReservationAllByUser(reservation.getUser());
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("reservationList");
        mav.addObject("userList", userList);
        mav.addObject("filteredReservationList", reservationList);
        return mav;
    }

    @RequestMapping("/admin/ticketsFilter/{filmshowId}")
    @ResponseBody
    public Map<BigInteger, String> filterTickets(@PathVariable Integer filmshowId) {
        return Optional.ofNullable(filmshowId)
                .map(BigInteger::valueOf)
                .flatMap(filmshowService::getFilmshowById)
                .map(ticketService::getTicketFreeByFilmshow)
                .orElse(Collections.emptyList())
                .stream()
                .collect(Collectors.toMap(Ticket::getTicketId, Ticket::toString));
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
