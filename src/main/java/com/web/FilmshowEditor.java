package com.web;

import com.domain.Filmshow;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class FilmshowEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        try {
            Filmshow filmshow = new Filmshow();
            filmshow.setFilmshowId(Integer.parseInt(text));
            this.setValue(filmshow);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
