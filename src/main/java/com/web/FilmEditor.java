package com.web;

import com.domain.Film;
import com.service.FilmService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class FilmEditor extends PropertyEditorSupport {

    private final FilmService filmService;

    public FilmEditor(FilmService filmService) {
        this.filmService = filmService;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Film film = filmService.getFilmById(id).orElse(null);
            this.setValue(film);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
