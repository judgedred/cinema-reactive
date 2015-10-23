package com.web;

import com.domain.Seat;
import com.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.beans.PropertyEditorSupport;

@Component
public class SeatEditor extends PropertyEditorSupport
{
    @Autowired
    private SeatService seatService;

    public void setAsText(String text)
    {
        try
        {
            Seat s = seatService.getSeatById(Integer.parseInt(text));
            this.setValue(s);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
