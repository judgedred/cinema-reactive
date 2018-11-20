package com;

import com.dao.FilmRepository;
import com.dao.FilmshowRepository;
import com.dao.HallRepository;
import com.dao.ReservationRepository;
import com.dao.SeatRepository;
import com.dao.TestDataRepository;
import com.dao.TicketRepository;
import com.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
public class CinemaTestConfiguration {

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private FilmshowRepository filmshowRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Bean
    public TestDataRepository testDataCreator() {
        return new TestDataRepository(filmRepository, hallRepository, filmshowRepository, seatRepository,
                ticketRepository, userRepository, reservationRepository);
    }

}
