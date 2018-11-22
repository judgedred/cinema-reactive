package com.web;

import com.domain.Filmshow;
import com.service.FilmshowService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class FilmshowEditor extends PropertyEditorSupport {

    private final FilmshowService filmshowService;

    public FilmshowEditor(FilmshowService filmshowService) {
        this.filmshowService = filmshowService;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Filmshow filmshow = filmshowService.getFilmshowById(id).orElse(null);
            this.setValue(filmshow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
