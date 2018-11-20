package com.service;

import com.dao.FilmshowRepository;
import com.dao.TicketRepository;
import com.domain.Filmshow;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFilmshowService implements FilmshowService {

    private final FilmshowRepository filmshowRepository;
    private final TicketRepository ticketRepository;

    public DefaultFilmshowService(FilmshowRepository filmshowRepository, TicketRepository ticketRepository) {
        this.filmshowRepository = filmshowRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Filmshow save(Filmshow filmshow) {
        return filmshowRepository.save(filmshow);
    }

    @Override
    public void delete(Filmshow filmshow) {
        filmshowRepository.delete(filmshow);
    }

    @Override
    public List<Filmshow> getFilmshowAll() {
        return filmshowRepository.findAll();
    }

    @Override
    public Optional<Filmshow> getFilmshowById(BigInteger id) {
        return filmshowRepository.findById(id);
    }

    @Override
    public boolean checkFilmshowInTicket(Filmshow filmshow) {
        return !ticketRepository.findAllByFilmshow(filmshow).isEmpty();
    }

    @Override
    public List<Filmshow> getFilmshowToday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        Date startDate = cal.getTime();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        Date endDate = cal.getTime();
        return filmshowRepository.findByDateTimeBetween(startDate, endDate);
    }

    @Override
    public List<Filmshow> getFilmshowWeek() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, 30);
        Date startDate = cal.getTime();
        cal.add(Calendar.DATE, 7);
        Date endDate = cal.getTime();
        return filmshowRepository.findByDateTimeBetween(startDate, endDate);
    }

}
