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
        Film film = new Film()
                .setFilmId(filmId)
                .setFilmName(filmName)
                .setDescription(description);
        return filmRepository.save(film).block();
    }

    Film createTestFilm() {
        return createFilm(BigInteger.valueOf(555), "testFilm", "testFilm");
    }

    Hall createHall(BigInteger id, String name, Integer number) {
        Hall hall = new Hall()
                .setHallId(id)
                .setHallName(name)
                .setHallNumber(number);
        return hallRepository.save(hall).block();
    }

    Hall createTestHall() {
        return createHall(BigInteger.valueOf(555), "testHall", 3);
    }

    Filmshow createTestFilmshow() {
        Film film = createTestFilm();
        Hall hall = createTestHall();
        Filmshow filmshow = new Filmshow()
                .setFilm(film)
                .setHall(hall)
                .setDateTime(LocalDateTime.now());
        return filmshowRepository.save(filmshow).block();
    }

    void cleanUpFilmshow(Filmshow filmshow) {
        filmRepository.delete(filmshow.getFilm()).block();
        hallRepository.delete(filmshow.getHall()).block();
        filmshowRepository.delete(filmshow).block();
    }

    Seat createSeat(BigInteger id, int rowNumber, int seatNumber, Hall hall) {
        Seat seat = new Seat()
                .setSeatId(id)
                .setRowNumber(rowNumber)
                .setSeatNumber(seatNumber)
                .setHall(hall);
        return seatRepository.save(seat);
    }

    Seat createTestSeat() {
        return createSeat(BigInteger.valueOf(555), 3, 12, createTestHall());
    }

    void cleanUpSeat(Seat seat) {
        hallRepository.delete(seat.getHall()).block();
        seatRepository.delete(seat);
    }

    Ticket createTicket(BigInteger id, Float price, Filmshow filmshow, Seat seat) {
        Ticket ticket = new Ticket()
                .setTicketId(id)
                .setPrice(price)
                .setFilmshow(filmshow)
                .setSeat(seat);
        return ticketRepository.save(ticket);
    }

    Ticket createTestTicket() {
        return createTicket(BigInteger.valueOf(555), 100F, createTestFilmshow(), createTestSeat());
    }

    void cleanUpTicket(Ticket ticket) {
        cleanUpFilmshow(ticket.getFilmshow());
        cleanUpSeat(ticket.getSeat());
        ticketRepository.delete(ticket);
    }

    User createUser(String login, String password, String email) {
        User user = new User()
                .setLogin(login)
                .setPassword(password)
                .setEmail(email);
        return userRepository.save(user);
    }

    User createTestUser() {
        return createUser("test", "test", "test@domain.com");
    }

    void cleanUpUser(User user) {
        userRepository.delete(user);
    }

    Reservation createReservation(Ticket ticket, User user) {
        Reservation reservation = new Reservation()
                .setTicket(ticket)
                .setUser(user);
        return reservationRepository.save(reservation);
    }

    Reservation createTestReservation() {
        return createReservation(createTestTicket(), createTestUser());
    }

    void cleanUpReservation(Reservation reservation) {
        cleanUpTicket(reservation.getTicket());
        userRepository.delete(reservation.getUser());
        reservationRepository.delete(reservation);
    }

}
