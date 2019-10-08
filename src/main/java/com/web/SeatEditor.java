package com.web;

import com.dao.SeatBlockingRepository;
import com.domain.Seat;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class SeatEditor extends PropertyEditorSupport {

    private final SeatBlockingRepository seatRepository;

    public SeatEditor(SeatBlockingRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Seat seat = seatRepository.findById(id).orElse(null);
            this.setValue(seat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
