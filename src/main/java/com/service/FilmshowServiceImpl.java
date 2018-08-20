package com.service;

import com.dao.DaoException;
import com.dao.FilmshowDao;
import com.dao.TicketDao;
import com.domain.Filmshow;
import com.domain.Ticket;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class FilmshowServiceImpl implements FilmshowService {

    @Autowired
    private FilmshowDao filmshowDao;

    @Autowired
    private TicketDao ticketDao;

    @Override
    public Filmshow create(Filmshow filmshow) throws DaoException {
        return filmshowDao.create(filmshow);
    }

    @Override
    public void update(Filmshow filmshow) throws DaoException {
        filmshowDao.update(filmshow);
    }

    @Override
    public void delete(Filmshow filmshow) throws DaoException {
        filmshowDao.delete(filmshow);
    }

    @Override
    public List<Filmshow> getFilmshowAll() throws DaoException {
        return filmshowDao.getFilmshowAll();
    }

    @Override
    public Filmshow getFilmshowById(BigInteger id) throws DaoException {
        return filmshowDao.getFilmshowById(id);
    }

    @Override
    public boolean checkFilmshowInTicket(Filmshow filmshow) throws DaoException {
        List<Ticket> ticketList = ticketDao.getTicketAllByFilmshow(filmshow);
        if (ticketList != null) {
            return true;
        }
        return false;
    }

    @Override
    public List<Filmshow> getFilmshowToday() throws DaoException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        Date startDate = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        Date endDate = cal.getTime();
        return filmshowDao.getFilmshowAllByDate(startDate, endDate);
    }

    @Override
    public List<Filmshow> getFilmshowWeek() throws DaoException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        Date startDate = cal.getTime();
        cal.add(Calendar.DATE, 7);
        Date endDate = cal.getTime();
        return filmshowDao.getFilmshowAllByDate(startDate, endDate);
    }

    @Override
    public Map<LocalDate, List<Filmshow>> groupFilmshowByDate(List<Filmshow> filmshowList) {
        Map<LocalDate, List<Filmshow>> filmshowMap = new TreeMap<>();
        for (Filmshow f : filmshowList) {
            Date javaDate = f.getDateTime();
            LocalDate date = new LocalDate(javaDate);
            List<Filmshow> dateGroupedFilmshow = filmshowMap.get(date);
            if (dateGroupedFilmshow == null) {
                dateGroupedFilmshow = new ArrayList<>();
                filmshowMap.put(date, dateGroupedFilmshow);
            }
            dateGroupedFilmshow.add(f);
        }
        return filmshowMap;
    }
}
