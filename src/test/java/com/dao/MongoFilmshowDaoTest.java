package com.dao;

import com.CinemaTestConfiguration;
import com.domain.Film;
import com.domain.Filmshow;
import com.domain.Hall;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CinemaTestConfiguration.class)
public class MongoFilmshowDaoTest {

    @Autowired
    private FilmshowRepository filmshowRepository;

    @Autowired
    private TestDataRepository testDataRepository;

    @Test
    public void createTest() {
        Filmshow filmshow = new Filmshow();
        Film film = testDataRepository.createTestFilm();
        Hall hall = testDataRepository.createTestHall();
        filmshow.setFilm(film);
        filmshow.setHall(hall);
        filmshow.setDateTime(LocalDateTime.now());
        Filmshow filmshowCreated = filmshowRepository.save(filmshow).block();
        assertNotNull(filmshowCreated);
        assertThat(filmshowCreated, is(filmshow));
        testDataRepository.cleanUpFilmshow(filmshowCreated);
    }

    @Test
    public void getByIdTest() {
        Filmshow filmshow = testDataRepository.createTestFilmshow();
        Optional<Filmshow> filmshowTest = filmshowRepository.findById(filmshow.getFilmshowId()).blockOptional();
        assertTrue(filmshowTest.isPresent());
        testDataRepository.cleanUpFilmshow(filmshow);
    }

    @Test
    public void updateTest() {
        Filmshow filmshow = testDataRepository.createTestFilmshow();
        filmshow.setDateTime(LocalDateTime.now());
        Film film = filmshow.getFilm();
        film.setFilmName("testFilmUpdated");
        filmshow.setFilm(film);
        Hall hall = filmshow.getHall();
        hall.setHallName("testHallUpdated");
        filmshow.setHall(hall);
        Filmshow filmshowUpdated = filmshowRepository.save(filmshow).block();
        assertThat(filmshowUpdated, is(filmshow));
        testDataRepository.cleanUpFilmshow(filmshowUpdated);
    }

    @Test
    public void deleteTest() {
        Filmshow filmshow = testDataRepository.createTestFilmshow();
        testDataRepository.cleanUpFilmshow(filmshow);
        assertFalse(filmshowRepository.findById(filmshow.getFilmshowId()).blockOptional().isPresent());
    }

    @Test
    public void getAllFilmshowsTest() {
        Filmshow filmshow = testDataRepository.createTestFilmshow();
        List<Filmshow> filmshows = filmshowRepository.findAll().collectList().block();
        assertNotNull(filmshows);
        assertTrue(filmshows.size() > 0);
        testDataRepository.cleanUpFilmshow(filmshow);
    }

    @Test
    public void findAllByFilmTest() {
        Filmshow filmshow = testDataRepository.createTestFilmshow();
        List<Filmshow> filmshows = filmshowRepository.findAllByFilm(filmshow.getFilm()).collectList().block();
        assertThat(filmshows, is(notNullValue()));
        assertThat(filmshows.size(), greaterThan(0));
        filmshows.forEach(f -> assertThat(f.getFilm(), is(filmshow.getFilm())));
        testDataRepository.cleanUpFilmshow(filmshow);
    }

    @Test
    public void findAllByHallTest() {
        Filmshow filmshow = testDataRepository.createTestFilmshow();
        List<Filmshow> filmshows = filmshowRepository.findAllByHall(filmshow.getHall()).collectList().block();
        assertThat(filmshows, is(notNullValue()));
        assertThat(filmshows.size(), greaterThan(0));
        filmshows.forEach(f -> assertThat(f.getFilm(), is(filmshow.getFilm())));
        testDataRepository.cleanUpFilmshow(filmshow);
    }

    @Test
    public void findAllBetweenDatesTest() {
        testDataRepository.createTestFilmshow();
        LocalDateTime startDate = LocalDateTime.now().minusMinutes(1);
        LocalDateTime endDate = startDate.plusMinutes(1);
        List<Filmshow> filmshows = filmshowRepository.findByDateTimeBetween(startDate, endDate).collectList().block();
        assertThat(filmshows, is(notNullValue()));
        assertThat(filmshows.size(), greaterThan(0));
        filmshows.forEach(f -> assertThat(f.getDateTime(), allOf(greaterThanOrEqualTo(startDate), lessThanOrEqualTo(endDate))));
        filmshows.forEach(testDataRepository::cleanUpFilmshow);
    }
}
