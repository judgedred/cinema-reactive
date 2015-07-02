package com.web;

import com.dao.*;
import com.domain.Hall;
import com.mysql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

@Controller
public class SessionListener implements HttpSessionListener
{
    @Autowired
    private UserDao userDao;

    @Autowired
    private FilmshowDao filmshowDao;

    @Autowired
    private FilmDao filmDao;

    @Autowired
    private HallDao hallDao;

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private SeatDao seatDao;

    @Autowired
    private TicketDao ticketDao;

	@Override
	public void sessionCreated(HttpSessionEvent event)
	{
			event.getSession().setAttribute("userDao", userDao);
			event.getSession().setAttribute("filmshowDao", filmshowDao);
			event.getSession().setAttribute("filmDao", filmDao);
			event.getSession().setAttribute("hallDao", hallDao);
			event.getSession().setAttribute("reservationDao", reservationDao);
			event.getSession().setAttribute("seatDao", seatDao);
			event.getSession().setAttribute("ticketDao", ticketDao);
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