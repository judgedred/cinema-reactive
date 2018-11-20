package com.service;

import com.domain.User;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user);

    void update(User user);

    void delete(User user);

    List<User> getUserAll();

    Optional<User> getUserById(BigInteger id);

    boolean checkUserInReservation(User user);

    User authenticateAdmin(User user);

    User authenticateUser(User user);

    boolean checkLogin(String login);

    boolean checkEmail(String email);
}
