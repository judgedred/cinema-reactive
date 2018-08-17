package com.web;

import com.domain.Ticket;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class TicketEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        try {
            Ticket t = new Ticket();
            t.setTicketId(Integer.parseInt(text));
            this.setValue(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
