package com.web;

import com.domain.User;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class UserEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        try {
            User user = new User();
            user.setUserId(Integer.parseInt(text));
            this.setValue(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
