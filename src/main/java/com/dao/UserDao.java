package com.dao;

import com.domain.User;
import java.util.List;

public interface UserDao
{
	public User create(User user) throws DaoException;
	public void update(User user) throws DaoException;
	public void delete(User user) throws DaoException;
	public List<User> getUserAll() throws DaoException;
	public User getUserById(Integer id) throws DaoException;
    public User getUserByUser(User user) throws DaoException;
    public List<User> getUserByLogin(String login) throws DaoException;
    public List<User> getUserByEmail(String email) throws DaoException;
}