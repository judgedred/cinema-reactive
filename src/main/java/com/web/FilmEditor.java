package com.web;

import com.domain.Film;
import com.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class FilmEditor extends PropertyEditorSupport {

    @Autowired
    private FilmService filmService;

    @Override
    public void setAsText(String text) {
        try {
            Film f = filmService.getFilmById(Integer.parseInt(text));
        /*Film f = new Film();
        f.setFilmId(Integer.parseInt(text));*/
            this.setValue(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*@Override
    public String getAsText()
    {
        Film f = (Film) this.getValue();
        return f.getFilmName();
    }*/
}
