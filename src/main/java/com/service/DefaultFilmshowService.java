package com.service;

import com.dao.FilmshowRepository;
import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class DefaultFilmshowService implements FilmshowService {

    private final FilmshowRepository filmshowRepository;
    private final TicketService ticketService;

    public DefaultFilmshowService(FilmshowRepository filmshowRepository, TicketService ticketService) {
        this.filmshowRepository = filmshowRepository;
        this.ticketService = ticketService;
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
        return !ticketService.getTicketAllByFilmshow(filmshow).isEmpty();
    }

    @Override
    public List<Filmshow> getFilmshowToday() {
        LocalDateTime startDate = LocalDateTime.now().plusMinutes(30);
        LocalDateTime endDate = LocalDateTime.of(startDate.toLocalDate(), LocalTime.of(23, 59));
        return filmshowRepository.findByDateTimeBetween(startDate, endDate);
    }

    @Override
    public List<Filmshow> getFilmshowWeek() {
        LocalDateTime startDate = LocalDateTime.now().plusMinutes(30);
        LocalDateTime endDate = startDate.plusDays(7);
        return filmshowRepository.findByDateTimeBetween(startDate, endDate);
    }

    @Override
    public List<Filmshow> getFilmshowByFilm(Film film) {
        return filmshowRepository.findAllByFilm(film);
    }

    @Override
    public List<Filmshow> getFilmshowByHall(Hall hall) {
        return filmshowRepository.findAllByHall(hall);
    }

}
