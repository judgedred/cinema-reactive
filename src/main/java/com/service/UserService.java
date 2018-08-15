package com.service;

import com.dao.DaoException;
import com.domain.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    User create(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException;

    void update(User user) throws DaoException;

    void delete(User user) throws DaoException;

    List<User> getUserAll() throws DaoException;

    User getUserById(Integer id) throws DaoException;

    boolean checkUserInReservation(User user) throws DaoException;

    User authenticateAdmin(User user)
            throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException;

    User authenticateUser(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException;

    boolean checkLogin(String login) throws DaoException;

    boolean checkEmail(String email) throws DaoException;
}
