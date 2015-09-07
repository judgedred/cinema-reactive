package com.dao;

import com.domain.Hall;
import java.util.List;

public interface HallDao
{
	public Hall create(Hall hall) throws DaoException;
	public void update(Hall hall) throws DaoException;
	public void delete(Hall hall) throws DaoException;
	public List<Hall> getHallAll() throws DaoException;
	public Hall getHallById(int id) throws DaoException;
}