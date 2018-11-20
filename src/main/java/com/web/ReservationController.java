package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.Ticket;
import com.domain.User;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FilmshowEditor filmshowEditor;

    @Autowired
    private UserEditor userEditor;

    @Autowired
    private TicketEditor ticketEditor;

    @RequestMapping("/admin/addReservationForm")
    public ModelAndView addReservationForm() throws Exception {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("addReservation");
        mav.addObject("filmshowList", filmshowList);
        mav.addObject("userList", userList);
        mav.addObject("reservation", new Reservation());
        return mav;
    }

    @RequestMapping("/admin/addReservation")
    public ModelAndView addReservation(@Valid Reservation reservation, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
            List<User> userList = userService.getUserAll();
            ModelAndView mav = new ModelAndView("addReservation");
            mav.addObject("filmshowList", filmshowList);
            mav.addObject("userList", userList);
            mav.addObject("reservation", reservation);
            return mav;
        }
        reservationService.create(reservation);
        return new ModelAndView(new RedirectView("addReservationForm"));
    }

    @RequestMapping("/admin/deleteReservation")
    public ModelAndView deleteReservation(@ModelAttribute Reservation reservation) throws Exception {
        if (reservation.getReservationId() != null && !reservation.getReservationId().equals(BigInteger.ZERO)) {
            reservation = reservationService.getReservationById(reservation.getReservationId());
            if (reservation != null) {
                reservationService.delete(reservation);
            }
        }
        List<Reservation> reservationList = reservationService.getReservationAll();
        return new ModelAndView("deleteReservation", "reservationList", reservationList);
    }

    @RequestMapping("/admin/reservationList")
    public ModelAndView listReservations(@ModelAttribute Reservation reservation) throws Exception {
        List<Reservation> reservationList = reservationService.getReservationAllByUser(reservation.getUser());
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("reservationList");
        mav.addObject("userList", userList);
        mav.addObject("filteredReservationList", reservationList);
        return mav;
    }

    @RequestMapping("/admin/ticketsFilter/{filmshowId}")
    @ResponseBody
    public Map<BigInteger, String> filterTickets(@PathVariable Integer filmshowId) throws Exception {
        if (filmshowId != null) {
            Filmshow filmshow = filmshowService.getFilmshowById(BigInteger.valueOf(filmshowId));
            if (filmshow != null) {
                List<Ticket> ticketList = ticketService.getTicketFreeByFilmshow(filmshow);
                if (ticketList != null) {
                    Map<BigInteger, String> ticketMap = new HashMap<>();
                    for (Ticket t : ticketList) {
                        ticketMap.put(t.getTicketId(), t.toString());
                    }
                    return ticketMap;
                }
            }
        }
        return null;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(User.class, userEditor);
        binder.registerCustomEditor(Filmshow.class, filmshowEditor);
        binder.registerCustomEditor(Ticket.class, ticketEditor);
    }
}
