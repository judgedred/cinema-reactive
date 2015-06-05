package com.dao;


public interface DaoFactory
{
	public UserDao getUserDao() throws DaoException;
	public FilmDao getFilmDao() throws DaoException;
	public HallDao getHallDao() throws DaoException;
	public SeatDao getSeatDao() throws DaoException;
	public TicketDao getTicketDao() throws DaoException;
	public FilmshowDao getFilmshowDao() throws DaoException;
	public ReservationDao getReservationDao() throws DaoException;	
}