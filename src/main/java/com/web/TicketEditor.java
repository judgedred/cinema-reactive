package com.web;

import com.domain.Ticket;
import com.service.TicketService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class TicketEditor extends PropertyEditorSupport {

    private final TicketService ticketService;

    public TicketEditor(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Ticket t = ticketService.getTicketById(id).orElse(null);
            this.setValue(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
