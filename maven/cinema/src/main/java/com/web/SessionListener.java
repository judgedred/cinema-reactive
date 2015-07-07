package com.web;

import com.dao.*;
import com.domain.Hall;
import com.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;


public class SessionListener implements HttpSessionListener
{
   	@Override
	public void sessionCreated(HttpSessionEvent event)
	{

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
			if(userDao != null)
            {
                userDao.close();
            }
			if(filmshowDao != null)
            {
                filmshowDao.close();
            }
            if(filmDao != null)
            {
                filmDao.close();
            }
            if(hallDao != null)
            {
                hallDao.close();
            }
            if(reservationDao != null)
            {
                reservationDao.close();
            }
            if(seatDao != null)
            {
                seatDao.close();
            }
            if(ticketDao != null)
            {
                ticketDao.close();
            }
		}
		catch(DaoException d)
		{
			d.printStackTrace();
		}
	}
}