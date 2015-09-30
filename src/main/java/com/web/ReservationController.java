package com.web;

import com.domain.Filmshow;
import com.domain.Reservation;
import com.domain.User;
import com.service.FilmshowService;
import com.service.ReservationService;
import com.service.TicketService;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ReservationController
{
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/admin/addReservation", method = RequestMethod.POST)
    public @ResponseBody ModelAndView addReservation(@RequestBody Reservation reservation) throws Exception
    {
        List<Filmshow> filmshowList = filmshowService.getFilmshowAll();
        List<User> userList = userService.getUserAll();
        List<Reservation> reservationList = reservationService.getReservationAll();
        ModelAndView mav = new ModelAndView("addReservation");
        mav.addObject("filmshowListJson", filmshowList);
        mav.addObject("userListJson", userList);
        if(reservation != null && reservation.getTicket() != null && reservation.getUser() != null)
        {
            boolean reservationValid = true;
            for(Reservation r : reservationList)
            {
                if(r.getTicket().equals(reservation.getTicket()))
                {
                    reservationValid = false;
                    break;
                }
            }
            if(reservationValid)
            {
                reservationService.create(reservation);
                mav.addObject("reservationJson", reservation);
            }
        }
        return mav;
    }

//    @RequestMapping("updateReservation")

    @RequestMapping("/admin/deleteReservation/{reservationId}")
    public @ResponseBody ModelAndView deleteReservation(@PathVariable int reservationId) throws Exception
    {
        List<Reservation> reservationList = reservationService.getReservationAll();
        Reservation reservation = reservationService.getReservationById(reservationId);
        if(reservation != null)
        {
            reservationService.delete(reservation);
        }
        return new ModelAndView("deleteReservation", "reservationListJson", reservationList);
    }

    @RequestMapping("/admin/reservationList")
    public @ResponseBody ModelAndView listReservations(@RequestBody User user) throws Exception
    {
        List<Reservation> reservationList = reservationService.getReservationAll();
        List<User> userList = userService.getUserAll();
        ModelAndView mav = new ModelAndView("reservationList");
        mav.addObject("userListJson", userList);
        List<Reservation> filteredList = new LinkedList<>();
        if(reservationList != null && user != null)
        {
            filteredList.addAll(reservationList.stream().filter(r -> r.getUser().equals(user)).collect(Collectors.toList()));
            mav.addObject("filteredListJson", filteredList);
        }
        return mav;
    }
}
