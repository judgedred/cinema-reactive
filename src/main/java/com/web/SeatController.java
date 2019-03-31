package com.web;

import com.domain.Hall;
import com.domain.Seat;
import com.service.HallService;
import com.service.SeatService;
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
    public String addSeatForm(Model model) {
        List<Hall> hallList = hallService.getHallAll();
        model.addAttribute("hallList", hallList);
        model.addAttribute("seat", new Seat());
        return "addSeat";
    }

    @RequestMapping("/admin/addSeat")
    public String addSeat(@Valid Seat seat, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Hall> hallList = hallService.getHallAll();
            model.addAttribute("hallList", hallList);
            model.addAttribute("seat", seat);
            return "addSeat";
        }
        seatService.save(seat);
        return "redirect:addSeatForm";
    }

    @RequestMapping("/admin/deleteSeat")
    public String deleteSeat(@ModelAttribute Seat seat, Model model) {
        if (seat.getSeatId() != null && !seat.getSeatId().equals(BigInteger.ZERO)) {
            seatService.getSeatById(seat.getSeatId()).ifPresent(seatService::delete);
        }
        List<Seat> seatList = seatService.getSeatAll();
        model.addAttribute("seatList", seatList);
        return "deleteSeat";
    }

    @RequestMapping("/admin/seatList")
    public String listSeats(Model model) {
        List<Seat> seatList = seatService.getSeatAll();
        model.addAttribute("seatList", seatList);
        return "seatList";
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
