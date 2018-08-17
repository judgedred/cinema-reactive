package com.web;

import com.domain.Film;
import com.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class FilmEditor extends PropertyEditorSupport {

    @Qualifier("filmServiceImpl")
    @Autowired
    private FilmService filmService;

    @Override
    public void setAsText(String text) {
        try {
            Film f = filmService.getFilmById(BigInteger.valueOf(Integer.parseInt(text)));
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
