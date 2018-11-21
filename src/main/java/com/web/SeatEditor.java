package com.web;

import com.domain.Seat;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class SeatEditor extends PropertyEditorSupport {

    public void setAsText(String text) {
        try {
            Seat seat = new Seat();
            seat.setSeatId(new BigInteger(text));
            this.setValue(seat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
