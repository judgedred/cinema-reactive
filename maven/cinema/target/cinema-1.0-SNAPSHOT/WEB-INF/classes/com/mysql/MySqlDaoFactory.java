package com.mysql;

import com.dao.*;
import java.sql.DriverManager;
import java.sql.Connection;

public class MySqlDaoFactory implements DaoFactory
{
	private String driver = "com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost:3306/cinema";
	private static String user = "admin";
	private static String pass = "admin";

	public static Connection getConnection() throws DaoException
	{
		try 
		{
			return DriverManager.getConnection(url, user, pass);
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}
	
	@Override
	public UserDao getUserDao() throws DaoException
	{
		return new MySqlUserDao();
	}

	@Override
	public FilmDao getFilmDao() throws DaoException
	{
		return new MySqlFilmDao();
	}

	@Override
	public HallDao getHallDao() throws DaoException
	{
		return new MySqlHallDao();
	}

	@Override
	public SeatDao getSeatDao() throws DaoException
	{
		return new MySqlSeatDao();
	}

	@Override
	public TicketDao getTicketDao() throws DaoException
	{
		return new MySqlTicketDao();
	}

	@Override
	public FilmshowDao getFilmshowDao() throws DaoException
	{
		return new MySqlFilmshowDao();
	}

	@Override
	public ReservationDao getReservationDao() throws DaoException
	{
		return new MySqlReservationDao();
	}

	public MySqlDaoFactory() throws DaoException
	{
		try 
		{
			Class.forName(driver);
		}
		catch(Exception e)
		{
			throw new DaoException(e);
		}
	}
}