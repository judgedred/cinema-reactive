package com.service;

import com.CinemaTestConfiguration;
import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import com.domain.Reservation;
import com.domain.Seat;
import com.domain.Ticket;
import com.domain.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@Disabled
@ExtendWith(SpringExtension.class)
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
        hallService.save(new Hall(1, "Большой зал")).block();
        hallService.save(new Hall(2, "Зал повышенной комфортности")).block();
    }

    private void createSeats() {
        createSeats(20, 3, 1);
        createSeats(6, 1, 2);
    }

    private void createSeats(int quantity, int rowNumber, Integer hallId) {
        IntStream.range(1, quantity + 1)
                .forEach(index -> seatService.save(new Seat(index, rowNumber, getHall(hallId))).block());
    }

    private Hall getHall(Integer hallId) {
        return hallService.getHallByNumber(hallId)
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Hall must be created."));
    }

    private void createFilms() {
        filmService.save(new Film("Мстители", "Супергерои против злодея Артрона.")).block();
        filmService.save(new Film("Восхождение Юпитер", "Фантастика")).block();
        filmService.save(new Film("Аватар", "Фантастика")).block();
        filmService.save(new Film("Матрица", "Фантастика")).block();
    }

    private void createFilmshows() {
        Film film = filmService.getFilmByName("Матрица")
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Film must be created."));
        LocalDateTime dateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0));
        LongStream.range(0, 8)
                .forEach(index -> filmshowService.save(new Filmshow(dateTime.plusDays(index), film, getHall(1))).block());
    }

    private void createTickets() {
        Filmshow filmshow = filmshowService.getFilmshowAll()
                .collectList()
                .filter(filmshows -> !filmshows.isEmpty())
                .flatMap(filmshows -> Mono.justOrEmpty(filmshows.stream()
                        .max(Comparator.comparing(Filmshow::getFilmshowId))))
                .switchIfEmpty(Mono.error(new IllegalStateException("Filmshows must be created")))
                .block();
        seatService.getSeatFreeByFilmshow(filmshow)
                .take(5L)
                .flatMap(seat -> ticketService.save(new Ticket(7F, filmshow, seat)))
                .collectList()
                .block();
    }

    private void createReservations() {
        User user = userService.getUserByLogin("user")
                .orElseThrow(() -> new IllegalStateException("User must be created."));
        Ticket ticket = ticketService.getTicketAll()
                .next()
                .blockOptional()
                .orElseThrow(() -> new IllegalStateException("Ticket must be created."));
        reservationService.save(new Reservation(user, ticket)).block();
    }
}
