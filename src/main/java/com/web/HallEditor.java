package com.web;

import com.domain.Hall;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class HallEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        try {
            Hall h = new Hall();
            h.setHallId(new BigInteger(text));
            this.setValue(h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
