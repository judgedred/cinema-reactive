package com.web;

import com.domain.User;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.beans.PropertyEditorSupport;

@Component
public class UserEditor extends PropertyEditorSupport
{
    @Autowired
    private UserService userService;

    @Override
    public void setAsText(String text)
    {
        try
        {
            User u = userService.getUserById(Integer.parseInt(text));
            this.setValue(u);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
