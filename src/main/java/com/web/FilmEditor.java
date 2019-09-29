package com.web;

import com.dao.FilmBlockingRepository;
import com.domain.Film;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class FilmEditor extends PropertyEditorSupport {

    private final FilmBlockingRepository filmRepository;

    public FilmEditor(FilmBlockingRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Film film = filmRepository.findById(id).orElse(null);
            this.setValue(film);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
