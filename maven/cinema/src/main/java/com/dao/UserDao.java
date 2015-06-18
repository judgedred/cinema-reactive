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
	public void close() throws DaoException;
	public User register(User user) throws DaoException;		//?
	public void changePassword(User user) throws DaoException; 	//?
}