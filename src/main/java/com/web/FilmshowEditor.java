package com.web;

import com.domain.Filmshow;
import com.service.FilmshowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.beans.PropertyEditorSupport;

@Component
public class FilmshowEditor extends PropertyEditorSupport
{
    @Autowired
    private FilmshowService filmshowService;

    @Override
    public void setAsText(String text)
    {
        try
        {
            Filmshow f = filmshowService.getFilmshowById(Integer.parseInt(text));
            this.setValue(f);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
