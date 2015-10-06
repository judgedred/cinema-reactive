package com.web;


import com.domain.Film;

import java.beans.PropertyEditorSupport;

public class FilmEditor extends PropertyEditorSupport
{
    @Override
    public void setAsText(String text)
    {
        Film f = new Film();
        f.setFilmId(Integer.parseInt(text));
        this.setValue(f);
    }

    @Override
    public String getAsText()
    {
        Film f = (Film) this.getValue();
        return f.getFilmName();
    }
}
