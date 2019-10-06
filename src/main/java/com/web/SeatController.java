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
import org.springframework.web.reactive.result.view.Rendering;

import javax.validation.Valid;
import java.math.BigInteger;
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
    public Rendering addSeatForm() {
        return Rendering.view("addSeat")
                .modelAttribute("hallList", hallService.getHallAll())
                .modelAttribute("seat", new Seat())
                .build();
    }

    @RequestMapping("/admin/addSeat")
    public Rendering addSeat(@Valid Seat seat, BindingResult result) {
        if (result.hasErrors()) {
            return Rendering.view("addSeat")
                    .modelAttribute("hallList", hallService.getHallAll())
                    .modelAttribute("seat", seat)
                    .build();
        }
        seatService.save(seat);
        return Rendering.redirectTo("addSeatForm").build();
    }

    @RequestMapping("/admin/deleteSeat")
    public Rendering deleteSeat(@ModelAttribute Seat seat) {
        if (seat.getSeatId() != null && !seat.getSeatId().equals(BigInteger.ZERO)) {
            seatService.getSeatById(seat.getSeatId()).ifPresent(seatService::delete);
        }
        return Rendering.view("deleteSeat").modelAttribute("seatList", seatService.getSeatAll()).build();
    }

    @RequestMapping("/admin/seatList")
    public Rendering listSeats(Model model) {
        return Rendering.view("seatList").modelAttribute("seatList", seatService.getSeatAll()).build();
    }

    @RequestMapping(value = "/admin/checkSeat/{seatId}", produces = "text/html; charset=UTF-8")
    @ResponseBody
    public String checkSeat(@PathVariable BigInteger seatId) {
        return Optional.ofNullable(seatId)
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
