package com.web;

import com.domain.Film;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class FilmEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        try {
            Film film = new Film();
            film.setFilmId(BigInteger.valueOf(Integer.parseInt(text)));
            this.setValue(film);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
