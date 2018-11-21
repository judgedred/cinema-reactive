package com.service;

import com.CinemaTestConfiguration;
import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import com.domain.User;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = CinemaTestConfiguration.class)
public class DataGenerator {

    @Autowired
    private UserService userService;

    @Autowired
    private HallService hallService;

    @Autowired
    private SeatService seatService;

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmshowService filmshowService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ReservationService reservationService;

    @Test
    public void generateData() {
        createUsers();
        createHalls();
        createSeats();
        createFilms();
        createFilmshows();
        createTickets();
        createReservations();
    }


    private void createUsers() {
        userService.save(new User("admin", "admin", "admin@gmail.com"));
        userService.save(new User("user", "user", "user@gmail.com"));
    }

    private void createHalls() {
        hallService.save(new Hall(1, "Большой зал"));
        hallService.save(new Hall(2, "Зал повышенной комфортности"));
    }

    private void createSeats() {
        createSeats(20, 3, 1);
        createSeats(6, 1, 2);
    }

    private void createSeats(int quantity, int rowNumber, Integer hallId) {
        IntStream.range(1, quantity + 1)
                .forEach(index -> seatService.save(new Seat(index, rowNumber, getHall(hallId))));
    }

    private Hall getHall(Integer hallId) {
        return hallService.getHallByNumber(hallId).orElseThrow(() -> new IllegalStateException("Hall must be created."));
    }

    private void createFilms() {
        filmService.save(new Film("Мстители", "Супергерои против злодея Артрона."));
        filmService.save(new Film("Восхождение Юпитер", "Фантастика"));
        filmService.save(new Film("Аватар", "Фантастика"));
        filmService.save(new Film("Матрица", "Фантастика"));
    }

    private void createFilmshows() {
        Film film = filmService.getFilmByName("Матрица")
                .orElseThrow(() -> new IllegalStateException("Film must be created."));
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LongStream.range(0, 8)
                .forEach(index -> filmshowService.save(new Filmshow(dateTime.plusDays(index), film, getHall(1))));
    }

    private void createTickets() {
        Filmshow filmshow = filmshowService.getFilmshowAll()
                .stream()
                .max(Comparator.comparing(Filmshow::getFilmshowId))
                .orElseThrow(() -> new IllegalStateException("Filmshows must be created"));
        seatService.getSeatFreeByFilmshow(filmshow)
                .stream()
                .limit(5)
                .forEach(seat -> ticketService.save(new Ticket(7F, filmshow, seat)));
    }

    private void createReservations() {
        User user = userService.getUserByLogin("user")
                .orElseThrow(() -> new IllegalStateException("User must be created."));
        Ticket ticket = ticketService.getTicketAll()
                .stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Ticket must be created."));
        reservationService.save(new Reservation(user, ticket));
    }
}
