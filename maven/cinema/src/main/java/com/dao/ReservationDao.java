package com.dao;

import com.domain.Reservation;
import java.util.List;

public interface ReservationDao
{
	public Reservation create(Reservation reservation) throws DaoException;
	public void update(Reservation reservation) throws DaoException;
	public void delete(Reservation reservation) throws DaoException;
	public List<Reservation> getReservationAll() throws DaoException;
	public Reservation getReservationById(int id) throws DaoException;
}