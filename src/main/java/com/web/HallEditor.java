package com.web;


import com.domain.Hall;
import com.service.HallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class HallEditor extends PropertyEditorSupport
{
    @Autowired
    private HallService hallService;

    @Override
    public void setAsText(String text)
    {
        try
        {
            Hall h = hallService.getHallById(Integer.parseInt(text));
            /*Hall h = new Hall();
            h.setHallId(Integer.parseInt(text));*/
            this.setValue(h);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /*@Override
    public String getAsText()
    {
        Hall h = (Hall) this.getValue();
        return h.getHallName();
    }*/
}
