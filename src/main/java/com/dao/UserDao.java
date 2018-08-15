package com.dao;

import com.domain.User;

import java.util.List;

public interface UserDao {

    User create(User user) throws DaoException;

    void update(User user) throws DaoException;

    void delete(User user) throws DaoException;

    List<User> getUserAll() throws DaoException;

    User getUserById(Integer id) throws DaoException;

    User getUserByUser(User user) throws DaoException;

    List<User> getUserByLogin(String login) throws DaoException;

    List<User> getUserByEmail(String email) throws DaoException;
}