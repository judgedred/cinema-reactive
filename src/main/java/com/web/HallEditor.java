package com.web;

import com.domain.Hall;
import com.service.HallService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class HallEditor extends PropertyEditorSupport {

    private final HallService hallService;

    public HallEditor(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Hall hall = hallService.getHallById(id).orElse(null);
            this.setValue(hall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
