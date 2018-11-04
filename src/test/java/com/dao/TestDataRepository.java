package com.dao;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Seat;

import java.math.BigInteger;
import java.util.Date;

public class TestDataRepository {

    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;
    private final FilmshowRepository filmshowRepository;
    private final SeatRepository seatRepository;

    public TestDataRepository(FilmRepository filmRepository, HallRepository hallRepository,
            FilmshowRepository filmshowRepository, SeatRepository seatRepository) {
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
        this.filmshowRepository = filmshowRepository;
        this.seatRepository = seatRepository;
    }

    Film createFilm(BigInteger filmId, String filmName, String description) {
        Film film = new Film();
        film.setFilmId(filmId);
        film.setFilmName(filmName);
        film.setDescription(description);
        return filmRepository.save(film);
    }

    Film createTestFilm() {
        return createFilm(BigInteger.valueOf(555), "testFilm", "testFilm");
    }

    Hall createHall(BigInteger id, String name, Integer number) {
        Hall hall = new Hall();
        hall.setHallId(id);
        hall.setHallName(name);
        hall.setHallNumber(number);
        return hallRepository.save(hall);
    }

    Hall createTestHall() {
        return createHall(BigInteger.valueOf(555), "testHall", 3);
    }

    Filmshow createTestFilmshow() {
        Filmshow filmshow = new Filmshow();
        Film film = createTestFilm();
        Hall hall = createTestHall();
        filmshow.setFilm(film);
        filmshow.setHall(hall);
        filmshow.setDateTime(new Date());
        return filmshowRepository.save(filmshow);
    }

    void cleanUpFilmshow(Filmshow filmshow) {
        filmRepository.delete(filmshow.getFilm());
        hallRepository.delete(filmshow.getHall());
        filmshowRepository.delete(filmshow);
    }

    Seat createSeat(BigInteger id, int rowNumber, int seatNumber, Hall hall) {
        Seat seat = new Seat();
        seat.setSeatId(id);
        seat.setRowNumber(rowNumber);
        seat.setSeatNumber(seatNumber);
        seat.setHall(hall);
        return seatRepository.save(seat);
    }

    Seat createTestSeat() {
        return createSeat(BigInteger.valueOf(555), 3, 12, createTestHall());
    }

    void cleanUpSeat(Seat seat) {
        hallRepository.delete(seat.getHall());
        seatRepository.delete(seat);
    }
}
