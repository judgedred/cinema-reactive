package com.web;

import com.domain.Hall;
import com.domain.Seat;
import com.service.HallService;
import com.service.SeatService;
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
import java.util.List;
import java.util.Optional;

@Controller
public class SeatController {

    private final SeatService seatService;
    private final HallService hallService;
    private final HallEditor hallEditor;

    public SeatController(SeatService seatService, HallService hallService, HallEditor hallEditor) {
        this.seatService = seatService;
        this.hallService = hallService;
        this.hallEditor = hallEditor;
    }

    @RequestMapping("/admin/addSeatForm")
    public ModelAndView addSeatForm() {
        List<Hall> hallList = hallService.getHallAll();
        ModelAndView mav = new ModelAndView("addSeat");
        mav.addObject("hallList", hallList);
        mav.addObject("seat", new Seat());
        return mav;
    }

    @RequestMapping("/admin/addSeat")
    public ModelAndView addSeat(@Valid Seat seat, BindingResult result) {
        if (result.hasErrors()) {
            List<Hall> hallList = hallService.getHallAll();
            ModelAndView mav = new ModelAndView("addSeat");
            mav.addObject("hallList", hallList);
            mav.addObject("seat", seat);
            return mav;
        }
        seatService.create(seat);
        return new ModelAndView(new RedirectView("addSeatForm"));
    }

    @RequestMapping("/admin/deleteSeat")
    public ModelAndView deleteSeat(@ModelAttribute Seat seat) {
        if (seat.getSeatId() != null && !seat.getSeatId().equals(BigInteger.ZERO)) {
            seatService.getSeatById(seat.getSeatId()).ifPresent(seatService::delete);
        }
        List<Seat> seatList = seatService.getSeatAll();
        return new ModelAndView("deleteSeat", "seatList", seatList);
    }

    @RequestMapping("/admin/seatList")
    public ModelAndView listSeats() {
        List<Seat> seatList = seatService.getSeatAll();
        return new ModelAndView("seatList", "seatList", seatList);
    }

    @RequestMapping(value = "/admin/checkSeat/{seatId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkSeat(@PathVariable Integer seatId) {
        return Optional.ofNullable(seatId)
                .map(BigInteger::valueOf)
                .flatMap(seatService::getSeatById)
                .filter(seatService::checkSeatInTicket)
                .map(seat -> "На место выпущены билеты. Сначала удалите билеты.")
                .orElse(null);
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Hall.class, hallEditor);
    }
}
