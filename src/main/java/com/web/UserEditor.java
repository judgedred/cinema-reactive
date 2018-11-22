package com.web;

import com.domain.User;
import com.service.UserService;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class UserEditor extends PropertyEditorSupport {

    private final UserService userService;

    public UserEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            User user = userService.getUserById(id).orElse(null);
            this.setValue(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
