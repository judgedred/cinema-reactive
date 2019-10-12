package com.web;

import com.dao.UserBlockingRepository;
import com.domain.User;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;
import java.math.BigInteger;

@Component
public class UserEditor extends PropertyEditorSupport {

    private final UserBlockingRepository userRepository;

    public UserEditor(UserBlockingRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void setAsText(String text) {
        try {
            BigInteger id = new BigInteger(text);
            User user = userRepository.findById(id).orElse(null);
            this.setValue(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
