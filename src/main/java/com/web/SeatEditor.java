package com.web;

import com.domain.Seat;
import com.service.SeatService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class SeatEditor extends PropertyEditorSupport {

    private final SeatService seatService;

    public SeatEditor(SeatService seatService) {
        this.seatService = seatService;
    }

    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Seat seat = seatService.getSeatById(id).orElse(null);
            this.setValue(seat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
