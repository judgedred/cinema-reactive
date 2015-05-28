package web;

import dao.*;
import mysql.*;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;


public class SessionListener implements HttpSessionListener
{
	@Override
	public void sessionCreated(HttpSessionEvent event)
	{
		try
		{
			DaoFactory daoFactory = new MySqlDaoFactory();
			UserDao userDao = daoFactory.getUserDao();
			FilmshowDao filmshowDao = daoFactory.getFilmshowDao();
			FilmDao filmDao = daoFactory.getFilmDao();
			HallDao hallDao = daoFactory.getHallDao();
			ReservationDao reservationDao = daoFactory.getReservationDao();
			SeatDao seatDao = daoFactory.getSeatDao();
			TicketDao ticketDao = daoFactory.getTicketDao();
			event.getSession().setAttribute("userDao", userDao);
			event.getSession().setAttribute("filmshowDao", filmshowDao);
			event.getSession().setAttribute("filmDao", filmDao);
			event.getSession().setAttribute("hallDao", hallDao);
			event.getSession().setAttribute("reservationDao", reservationDao);
			event.getSession().setAttribute("seatDao", seatDao);
			event.getSession().setAttribute("ticketDao", ticketDao);
		} 
		catch(DaoException d)
		{
			d.printStackTrace();
		}
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent event)
	{
		try
		{
			UserDao userDao = (UserDao)event.getSession().getAttribute("userDao");
			FilmshowDao filmshowDao = (FilmshowDao)event.getSession().getAttribute("filmshowDao");
			FilmDao filmDao = (FilmDao)event.getSession().getAttribute("filmsDao");
			HallDao hallDao = (HallDao)event.getSession().getAttribute("hallDao");
			ReservationDao reservationDao = (ReservationDao)event.getSession().getAttribute("reservationDao");
			SeatDao seatDao = (SeatDao)event.getSession().getAttribute("seatDao");
			TicketDao ticketDao = (TicketDao)event.getSession().getAttribute("ticketDao");
			userDao.close();
			filmshowDao.close();
			filmDao.close();
			hallDao.close();
			reservationDao.close();
			seatDao.close();
			ticketDao.close();
		}
		catch(DaoException d)
		{
			d.printStackTrace();
		}
	}
}