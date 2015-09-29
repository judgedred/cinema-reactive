package com.service;


import com.dao.DaoException;
import com.dao.UserDao;
import com.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Override
    public User create(User user) throws DaoException
    {
        return userDao.create(user);
    }

    @Override
    public void update(User user) throws DaoException
    {
        userDao.update(user);
    }

    @Override
    public void delete(User user) throws DaoException
    {
        userDao.delete(user);
    }

    @Override
    public List<User> getUserAll() throws DaoException
    {
        return userDao.getUserAll();
    }

    @Override
    public User getUserById(Integer id) throws DaoException
    {
        return userDao.getUserById(id);
    }
}
