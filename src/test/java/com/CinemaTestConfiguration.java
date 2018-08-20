package com;

import com.dao.FilmRepository;
import com.dao.FilmshowRepository;
import com.dao.HallRepository;
import com.dao.TestDataCreator;
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

    @Bean
    public TestDataCreator testDataCreator() {
        return new TestDataCreator(filmRepository, hallRepository, filmshowRepository);
    }

}
