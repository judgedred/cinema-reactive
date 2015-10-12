package com.web;

import com.domain.Ticket;
import com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class TicketEditor extends PropertyEditorSupport
{
    @Autowired
    private TicketService ticketService;

    @Override
    public void setAsText(String text)
    {
        try
        {
            Ticket t = ticketService.getTicketById(Integer.parseInt(text));
            this.setValue(t);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
