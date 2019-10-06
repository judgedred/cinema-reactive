package com.web;

import com.dao.FilmshowBlockingRepository;
import com.domain.Filmshow;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class FilmshowEditor extends PropertyEditorSupport {

    private final FilmshowBlockingRepository filmshowRepository;

    public FilmshowEditor(FilmshowBlockingRepository filmshowRepository) {
        this.filmshowRepository = filmshowRepository;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            Filmshow filmshow = filmshowRepository.findById(id).orElse(null);
            this.setValue(filmshow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
