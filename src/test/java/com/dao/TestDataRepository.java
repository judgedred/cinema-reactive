package com.dao;

import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import com.domain.User;

import java.math.BigInteger;
import java.time.LocalDateTime;

public class TestDataRepository {

    private final FilmRepository filmRepository;
    private final HallRepository hallRepository;
    private final FilmshowRepository filmshowRepository;
    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public TestDataRepository(FilmRepository filmRepository, HallRepository hallRepository,
            FilmshowRepository filmshowRepository, SeatRepository seatRepository, TicketRepository ticketRepository,
            UserRepository userRepository, ReservationRepository reservationRepository) {
        this.filmRepository = filmRepository;
        this.hallRepository = hallRepository;
        this.filmshowRepository = filmshowRepository;
        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
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
        filmshow.setDateTime(LocalDateTime.now());
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

    Ticket createTicket(BigInteger id, Float price, Filmshow filmshow, Seat seat) {
        Ticket ticket = new Ticket();
        ticket.setTicketId(id);
        ticket.setPrice(price);
        ticket.setFilmshow(filmshow);
        ticket.setSeat(seat);
        return ticketRepository.save(ticket);
    }

    Ticket createTestTicket() {
        return createTicket(BigInteger.valueOf(555), 100F, createTestFilmshow(), createTestSeat());
    }

    void cleanUpTicket(Ticket ticket) {
        cleanUpFilmshow(ticket.getFilmshow());
        seatRepository.delete(ticket.getSeat());
        ticketRepository.delete(ticket);
    }

    User createUser(String login, String password, String email) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        return userRepository.save(user);
    }

    User createTestUser() {
        return createUser("test", "test", "test@domain.com");
    }

    Reservation createReservation(Ticket ticket, User user) {
        Reservation reservation = new Reservation();
        reservation.setTicket(ticket);
        reservation.setUser(user);
        return reservationRepository.save(reservation);
    }

    Reservation createTestReservation() {
        return createReservation(createTestTicket(), createTestUser());
    }

    void cleanUpReservation(Reservation reservation) {
        ticketRepository.delete(reservation.getTicket());
        userRepository.delete(reservation.getUser());
        reservationRepository.delete(reservation);
    }

}
