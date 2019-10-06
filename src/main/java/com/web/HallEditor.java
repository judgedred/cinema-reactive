package com.web;

import com.dao.HallBlockingRepository;
import com.domain.Hall;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class HallEditor extends PropertyEditorSupport {

    private final HallBlockingRepository hallRepository;

    public HallEditor(HallBlockingRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Hall hall = hallRepository.findById(id).orElse(null);
            this.setValue(hall);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
