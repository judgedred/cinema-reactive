package com.web;

import com.dao.TicketBlockingRepository;
import com.domain.Ticket;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class TicketEditor extends PropertyEditorSupport {

    private final TicketBlockingRepository ticketRepository;

    public TicketEditor(TicketBlockingRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Ticket t = ticketRepository.findById(id).orElse(null);
            this.setValue(t);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
