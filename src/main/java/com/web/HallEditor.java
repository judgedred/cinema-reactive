package com.web;


import com.domain.Hall;

import java.beans.PropertyEditorSupport;

public class HallEditor extends PropertyEditorSupport
{
    @Override
    public void setAsText(String text)
    {
        Hall h = new Hall();
        h.setHallId(Integer.parseInt(text));
        this.setValue(h);
    }

    @Override
    public String getAsText()
    {
        Hall h = (Hall) this.getValue();
        return h.getHallName();
    }
}
