package com.web;

import com.domain.Seat;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class SeatEditor extends PropertyEditorSupport {

    public void setAsText(String text) {
        try {
            Seat seat = new Seat();
            seat.setSeatId(Integer.parseInt(text));
            this.setValue(seat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
