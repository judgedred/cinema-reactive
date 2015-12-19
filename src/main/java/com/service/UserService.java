package com.service;

import com.dao.DaoException;
import com.domain.User;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService
{
    public User create(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException;
    public void update(User user) throws DaoException;
    public void delete(User user) throws DaoException;
    public List<User> getUserAll() throws DaoException;
    public User getUserById(Integer id) throws DaoException;
    public boolean checkUserInReservation(User user) throws DaoException;
    public User authenticateAdmin(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException;
    public User authenticateUser(User user) throws DaoException, NoSuchAlgorithmException, UnsupportedEncodingException;
    public boolean checkLogin(String login) throws DaoException;
    public boolean checkEmail(String email) throws DaoException;
}
