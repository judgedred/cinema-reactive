package com.web;

import com.domain.User;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class UserEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        try {
            User user = new User();
            user.setUserId(BigInteger.valueOf(Integer.parseInt(text)));
            this.setValue(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
